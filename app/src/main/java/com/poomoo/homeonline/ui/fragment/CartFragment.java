/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CartAdapter;
import com.poomoo.homeonline.listeners.OnBuyCheckChangedListener;
import com.poomoo.homeonline.listeners.OnEditCheckChangedListener;
import com.poomoo.homeonline.presenters.CartFragmentPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.homeonline.ui.activity.ConfirmOrderActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.RCartShopBO;
import com.poomoo.model.response.RCommodityCount;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CartFragment
 * 描述 购物车
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class CartFragment extends BaseDaggerFragment<CartFragmentPresenter> implements OnBuyCheckChangedListener, OnEditCheckChangedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.txt_edit)
    TextView editTxt;
    @Bind(R.id.expandableListView)
    ExpandableListView listView;
    @Bind(R.id.chk_all_commodity)
    CheckBox allBuyChk;
    @Bind(R.id.chk_all_commodity_edit)
    CheckBox allEditChk;
    @Bind(R.id.txt_totalPrice)
    TextView totalPriceTxt;
    @Bind(R.id.llayout_cart_buy)
    LinearLayout cartBuyLayout;
    @Bind(R.id.llayout_buy)
    LinearLayout buyLayout;
    @Bind(R.id.llayout_cart_edit)
    LinearLayout editLayout;
    @Bind(R.id.txt_delete)
    TextView deleteTxt;
    @Bind(R.id.llayout_empty)
    LinearLayout emptyLayout;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;

    private CartAdapter adapter;
    private List<RCartShopBO> rCartShopBOs = new ArrayList<>();
    private List<RCartCommodityBO> rCartCommodityBOs = new ArrayList<>();
    private RCartShopBO rCartShopBO;
    private RCartCommodityBO rCartCommodityBO;

    private boolean isClick = true;//是否是点击全选框 true-点击 false-适配器变化
    public static CartFragment inStance = null;
    private EditText dialogCountEdt;
    //    private TextView dialogMinusTxt;
//    private TextView dialogPlusTxt;
    private ImageView dialogMinusImg;
    private ImageView dialogPlusImg;
    private Button dialogCancelBtn;
    private Button dialogConfirmBtn;


    private AddAndMinusView addAndMinusView;
    private EditPopUpWindow popUpWindow;
    private View view;
    private int cartId;
    private int count;
    private int groupPosition;
    private int childPosition;
    private int commodityType;
    //    private int minNum = 1;//商品数量下限
//    private int maxNum = 200;//商品数量上限
    private boolean isRefresh = false;//true--刷新
    private int[] deleteIndex;//删除的商品的id集合


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    protected void setupFragmentComponent(FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }

    private void init() {
        inStance = this;
        adapter = new CartAdapter(getActivity(), this, this);
        listView.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.cart_header, null));
        listView.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.cart_footer, null));
        listView.setAdapter(adapter);

        //设置 属性 GroupIndicator 去掉默认向下的箭头
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener((parent, v, groupPosition1, id) -> true);
        listView.setOnItemClickListener((parent, view1, position, id) -> LogUtils.d(TAG,"onItemClick"));
        listView.setOnChildClickListener((parent, v, groupPosition1, childPosition1, id) -> {
            LogUtils.d(TAG,"setOnChildClickListener");
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.intent_commodityId), ((RCartCommodityBO) adapter.getChild(groupPosition1, childPosition1)).commodityId);
            bundle.putInt(getString(R.string.intent_commodityDetailId), ((RCartCommodityBO) adapter.getChild(groupPosition1, childPosition1)).commodityDetailId);
            bundle.putInt(getString(R.string.intent_commodityType), ((RCartCommodityBO) adapter.getChild(groupPosition1, childPosition1)).commodityType);
            openActivity(CommodityInfoActivity.class, bundle);
            return true;
        });
//        adapter.setGroup(getList());
//        expandListView();

        allBuyChk.setOnCheckedChangeListener(this);
        allEditChk.setOnCheckedChangeListener(this);
        editTxt.setOnClickListener(this);
        deleteTxt.setOnClickListener(this);
        buyLayout.setOnClickListener(this);
        mErrorLayout.setOnActiveClickListener(this);

        getProgressBar();

        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCartInfo(application.getUserId());
    }

    public void getInfoSucceed(List<RCartShopBO> rCartShopBOs) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        hideProgressBar();

        if (!isRefresh)
            adapter.setGroup(rCartShopBOs);
        else
            add(rCartShopBOs);
        if (adapter.getGroupCount() == 0) {
            listView.setVisibility(View.GONE);
            cartBuyLayout.setVisibility(View.GONE);
            editTxt.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            cartBuyLayout.setVisibility(View.VISIBLE);
            editTxt.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }

        expandListView();
    }

    public void getInfoFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        if (isNetWorkInvalid(msg))
            mErrorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else
            mErrorLayout.setState(ErrorLayout.LOAD_FAILED, "");

        listView.setVisibility(View.GONE);
        cartBuyLayout.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        editTxt.setVisibility(View.GONE);
    }

    public void changeCount(int cartId, int cartNum, int groupPosition, int childPosition, AddAndMinusView addAndMinusView, int commodityType) {
        this.cartId = cartId;
        this.count = cartNum;
        this.addAndMinusView = addAndMinusView;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.commodityType = commodityType;
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.changeCount(cartId, cartNum, commodityType);
    }

    public void changeCountSucceed(RCommodityCount rCommodityCount) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        count = rCommodityCount.cartNum;
        addAndMinusView.setCount(count);
        adapter.setCount(groupPosition, childPosition, count);
    }

    public void changeCountFailed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
    }

    public void deleteCommodity() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.deleteCommodity(deleteIndex);
    }

    public void deleteSucceed() {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        adapter.remove();
        adapter.deleteIndex = new ArrayList<>();
        if (adapter.getGroupCount() == 0) {
            listView.setVisibility(View.GONE);
            cartBuyLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.GONE);
            editTxt.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);

            allBuyChk.setChecked(false);
            adapter.isEdit = false;
        }
    }

    public void deleteFailed(String msg) {
        mErrorLayout.setState(ErrorLayout.HIDE, "");
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

//    private List<RCartShopBO> getList() {
//        rCartShopBOs = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            rCartShopBO = new RCartShopBO();
////            rCartShopBO.shopId = i + 1;
//            rCartShopBO.shopName = "测试店铺" + (i + 1);
//            rCartShopBO.carts = getCommodities();
//            rCartShopBOs.add(rCartShopBO);
//        }
//        return rCartShopBOs;
//    }
//
//    private List<RCartShopBO> getList2() {
//        rCartShopBOs = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            rCartShopBO = new RCartShopBO();
////            rCartShopBO.shopId = i + 1;
//            rCartShopBO.shopName = "测试店铺" + (i + 1);
//            rCartShopBO.carts = getCommodities2();
//            rCartShopBOs.add(rCartShopBO);
//        }
//        rCartShopBO = new RCartShopBO();
////        rCartShopBO.shopId = 10;
//        rCartShopBO.shopName = "测试店铺10";
//        rCartShopBO.carts = getCommodities2();
//        rCartShopBOs.add(rCartShopBO);
//        return rCartShopBOs;
//    }

//    private List<RCartCommodityBO> getCommodities() {
//        rCartCommodityBOs = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            rCartCommodityBO = new RCartCommodityBO();
//            rCartCommodityBO.commodityNum = i + 1;
//            rCartCommodityBO.commodityId = i + 1;
//            rCartCommodityBO.commodityName = "测试商品" + (i + 1);
//            rCartCommodityBO.commodityPrice = 378 + i + "";
//            rCartCommodityBOs.add(rCartCommodityBO);
//        }
//        return rCartCommodityBOs;
//    }
//
//    private List<RCartCommodityBO> getCommodities2() {
//        rCartCommodityBOs = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            rCartCommodityBO = new RCartCommodityBO();
//            rCartCommodityBO.commodityNum = i + 1;
//            rCartCommodityBO.commodityId = i + 10;
//            rCartCommodityBO.commodityName = "添加商品" + (i + 1);
//            rCartCommodityBO.commodityPrice = 478 + i + "";
//            rCartCommodityBOs.add(rCartCommodityBO);
//        }
//        rCartCommodityBO = new RCartCommodityBO();
//        rCartCommodityBO.commodityNum = 4;
//        rCartCommodityBO.commodityId = 1;
//        rCartCommodityBO.commodityName = "测试商品10";
//        rCartCommodityBO.commodityPrice = "480";
//        rCartCommodityBOs.add(rCartCommodityBO);
//        return rCartCommodityBOs;
//    }

    @Override
    public void onLoadActiveClick() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getCartInfo(application.getUserId());
    }

    /**
     * 购买状态
     *
     * @param isAllSelected
     */
    @Override
    public void onBuyClick(boolean isAllSelected) {
        isClick = false;
        allBuyChk.setChecked(isAllSelected);
        isClick = true;
    }

    /**
     * 编辑状态
     *
     * @param isAllSelected
     */
    @Override
    public void onEditClick(boolean isAllSelected) {
        isClick = false;
        allEditChk.setChecked(isAllSelected);
        isClick = true;
    }

    /**
     * 更新总价格
     *
     * @param totalPrice
     */
    public void setTotalPrice(String totalPrice) {
        LogUtils.d(TAG, "totalPrice:" + totalPrice);
        totalPriceTxt.setText(totalPrice);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isClick)
            if (isChecked)
                adapter.selectAll();
            else
                adapter.cancelAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_edit:
                if (editTxt.getText().toString().equals("编辑")) {//编辑
                    editTxt.setText("完成");
                    cartBuyLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.VISIBLE);
                    //初始化选中状态
                    isClick = false;
                    allEditChk.setChecked(false);
                    isClick = true;
                    adapter.isEdit = true;
//                    adapter.removeCount = 0;
                    adapter.deleteIndex = new ArrayList<>();
                    adapter.cancelAll();
                    adapter.notifyDataSetChanged();
                } else {//完成
                    editTxt.setText("编辑");
                    cartBuyLayout.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                    adapter.isEdit = false;
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.txt_delete:
                if (adapter.deleteIndex.size() == 0) {
                    MyUtils.showToast(getActivity().getApplicationContext(), "您还没有选择商品哦!");
                    return;
                }
                showDialog();
                break;
            case R.id.img_dialog_minus:
                dialogCountEdt.setText(--count + "");
                LogUtils.d(TAG, "减" + count);
                break;
            case R.id.img_dialog_plus:
                dialogCountEdt.setText(++count + "");
                break;
            case R.id.btn_dialog_cancel:
                popUpWindow.dismiss();
                break;
            case R.id.btn_dialog_confirm:
                if (TextUtils.isEmpty(dialogCountEdt.getText().toString()))
                    return;
//                addAndMinusView.setCount(count);
//                adapter.setCount(groupPosition, childPosition, count);
                showProgressBar();
                mPresenter.changeCount(cartId, count, commodityType);
                popUpWindow.dismiss();
                break;
            case R.id.llayout_buy:
                if (adapter.getrCartCommodityBOs().size() == 0) {
                    MyUtils.showToast(getActivity().getApplicationContext(), "您还没有选择商品哦!");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.intent_commodityList), adapter.getrCartCommodityBOs());
                bundle.putDouble(getString(R.string.intent_totalPrice), adapter.getTotalPrice());
                bundle.putBoolean(getString(R.string.intent_isFreePostage), adapter.isFreePostage());
                openActivity(ConfirmOrderActivity.class, bundle);
                break;
        }
    }

    public void showEditPopupWindow(int cartId, int count, int groupPosition, int childPosition, AddAndMinusView addAndMinusView, int commodityType) {
        if (popUpWindow == null)
            popUpWindow = new EditPopUpWindow(getActivity());

        this.cartId = cartId;
        this.count = count;
        this.addAndMinusView = addAndMinusView;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.commodityType = commodityType;

        dialogCountEdt.setText(count + "");
        dialogCountEdt.setSelection(String.valueOf(count).length());
        dialogCountEdt.setFocusableInTouchMode(true);
        dialogCountEdt.setFocusable(true);
        dialogCountEdt.requestFocus();

        popUpWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        popUpWindow.showAtLocation(getActivity().findViewById(R.id.llayout_cart), Gravity.CENTER, 0, 0);
    }

    private void showDialog() {
        Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage("确认要删除这" + adapter.deleteIndex.size() + "种商品吗?").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteIndex = new int[adapter.deleteIndex.size()];
                int index = 0;
                for (Integer integer : adapter.deleteIndex)
                    deleteIndex[index++] = integer;

                deleteCommodity();
            }
        }).create();
        dialog.show();
    }

    /**
     * 展开group
     */
    private void expandListView() {
        for (int i = 0; i < adapter.getGroups().size(); i++) {
            listView.expandGroup(i);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeState();
            isRefresh = true;
            showProgressBar();
            mPresenter.getCartInfo(application.getUserId());
        }
    }

    /**
     * 切换编辑状态到购买状态
     */
    private void changeState() {
        if (adapter.getGroups().size() > 0) {
            adapter.isEdit = false;
            adapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            cartBuyLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
            editTxt.setText("编辑");
            editTxt.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    public void add(List<RCartShopBO> rCartShopBOs) {
        Iterator<RCartShopBO> iterator = rCartShopBOs.iterator();
        final List<RCartShopBO> data = adapter.getGroups();
        while (iterator.hasNext()) {
            LogUtils.d(TAG, "While");
            RCartShopBO obj = iterator.next();
            if (data.contains(obj)) {
                int i = data.indexOf(obj);
                //遍历新增的商品和老的商品并比较
                int newLen = obj.getChildrenCount();
                for (int j = 0; j < newLen; j++) {
                    if (!data.get(i).carts.contains(obj.carts.get(j))) {//不存在则添加新商品
                        obj.carts.get(j).isBuyChecked = data.get(i).isBuyChecked;
                        data.get(i).carts.add(0, obj.carts.get(j));
                        LogUtils.d(TAG, "店铺" + obj.shopName + "添加了商品" + obj.carts.get(j).commodityName);
                    } else {//存在则更新
                        int position = data.get(i).carts.indexOf(obj.carts.get(j));
                        obj.carts.get(j).isBuyChecked = data.get(i).carts.get(data.get(i).carts.indexOf(obj.carts.get(j))).isBuyChecked;
                        data.get(i).carts.set(position, obj.carts.get(j));
                        LogUtils.d(TAG, "店铺" + obj.shopName + "更新了商品" + obj.carts.get(j).commodityName);
                    }
                }
            } else {
                LogUtils.d(TAG, "添加了一个店铺:" + obj.shopName);
                obj.isBuyChecked = allBuyChk.isChecked();
                obj.setChildChecked(allBuyChk.isChecked());
                data.add(0, obj);
            }
        }
        adapter.notifyDataSetChanged();
        expandListView();
        changeState();
    }

    class EditPopUpWindow extends PopupWindow {

        public EditPopUpWindow(Context context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_edit_count, null);
            dialogCountEdt = (EditText) view.findViewById(R.id.edt_dialog_count);
//            dialogMinusTxt = (TextView) view.findViewById(R.id.txt_dialog_minus);
//            dialogPlusTxt = (TextView) view.findViewById(R.id.txt_dialog_plus);
            dialogPlusImg = (ImageView) view.findViewById(R.id.img_dialog_plus);
            dialogMinusImg = (ImageView) view.findViewById(R.id.img_dialog_minus);
            dialogCancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
            dialogConfirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);

            dialogMinusImg.setOnClickListener(CartFragment.this);
            dialogPlusImg.setOnClickListener(CartFragment.this);
            dialogCancelBtn.setOnClickListener(CartFragment.this);
            dialogConfirmBtn.setOnClickListener(CartFragment.this);

//            dialogCountEdt.setFilters(new InputFilter[]{"1", "200"});

            dialogCountEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String temp = s.toString();
                    LogUtils.d(TAG, "temp:" + temp);
                    if (temp.length() == 0)
                        return;
                    if (temp.length() == MyConfig.MINCOUNT && temp.equals("0")) {
                        s.replace(0, 1, "1");
                        count = MyConfig.MINCOUNT;
                    }
                    count = Integer.parseInt(temp);
                    if (count > MyConfig.MAXCOUNT) {
                        count = MyConfig.MAXCOUNT;
                        LogUtils.d(TAG, "s:" + s.toString() + " len" + s.length());
                        s.replace(0, s.length(), count + "");
                    }
                    setEnabled(count);
                }
            });

            view.setOnTouchListener((v, event) -> {
                int height_top = view.findViewById(R.id.llayout_edit_count).getTop();
                int height_bottom = view.findViewById(R.id.llayout_edit_count).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height_top || y > height_bottom) {
                        dismiss();
                    }
                }
                return true;
            });

            this.setContentView(view);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            this.setOnDismissListener(() -> MyUtils.hiddenKeyBoard(getActivity(), view));

            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);
        }
    }

    /**
     * 设置是否可以增减商品数量
     *
     * @param count
     */
    private void setEnabled(int count) {
        if (count == MyConfig.MINCOUNT) {
            dialogMinusImg.setEnabled(false);
            dialogPlusImg.setEnabled(true);
        } else if (count == MyConfig.MAXCOUNT) {
            dialogMinusImg.setEnabled(true);
            dialogPlusImg.setEnabled(false);
        } else {
            dialogPlusImg.setEnabled(true);
            dialogMinusImg.setEnabled(true);
        }
    }
}
