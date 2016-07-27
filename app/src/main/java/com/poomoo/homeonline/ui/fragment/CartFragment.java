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
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CartAdapter;
import com.poomoo.homeonline.listeners.OnBuyCheckChangedListener;
import com.poomoo.homeonline.listeners.OnEditCheckChangedListener;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.model.response.RCartBO;
import com.poomoo.model.response.RCommodityBO;

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
public class CartFragment extends BaseFragment implements OnBuyCheckChangedListener, OnEditCheckChangedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
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
    LinearLayout buyLayout;
    @Bind(R.id.llayout_cart_edit)
    LinearLayout editLayout;
    @Bind(R.id.txt_delete)
    TextView deleteTxt;
    @Bind(R.id.llayout_empty)
    LinearLayout emptyLayout;

    private CartAdapter adapter;
    private List<RCartBO> rCartBOs = new ArrayList<>();
    private List<RCommodityBO> rCommodityBOs = new ArrayList<>();
    private RCartBO rCartBO;
    private RCommodityBO rCommodityBO;

    private boolean isClick = true;//是否是点击全选框 true-点击 false-适配器变化
    public static CartFragment inStance = null;
    private EditText dialogCountEdt;
    private TextView dialogMinusTxt;
    private TextView dialogPlusTxt;
    private Button dialogCancelBtn;
    private Button dialogConfirmBtn;

    private int count = 1;
    private AddAndMinusView addAndMinusView;
    private EditPopUpWindow popUpWindow;
    private View view;
    private int groupPosition;
    private int childPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        inStance = this;

        adapter = new CartAdapter(getActivity(), getList(), this, this);
        listView.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.cart_header, null));
        listView.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.cart_footer, null));
        listView.setAdapter(adapter);

        //设置 属性 GroupIndicator 去掉默认向下的箭头
        listView.setGroupIndicator(null);
        expandListView();

        allBuyChk.setOnCheckedChangeListener(this);
        allEditChk.setOnCheckedChangeListener(this);
        editTxt.setOnClickListener(this);
        deleteTxt.setOnClickListener(this);
    }

    private List<RCartBO> getList() {
        rCartBOs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            rCartBO = new RCartBO();
            rCartBO.shopId = i + 1;
            rCartBO.shop = "测试店铺" + (i + 1);
            rCartBO.rCommodityBOs = getCommodities();
            rCartBOs.add(rCartBO);
        }
        return rCartBOs;
    }

    private List<RCartBO> getList2() {
        rCartBOs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            rCartBO = new RCartBO();
            rCartBO.shopId = i + 1;
            rCartBO.shop = "测试店铺" + (i + 1);
            rCartBO.rCommodityBOs = getCommodities2();
            rCartBOs.add(rCartBO);
        }
        rCartBO = new RCartBO();
        rCartBO.shopId = 10;
        rCartBO.shop = "测试店铺10";
        rCartBO.rCommodityBOs = getCommodities2();
        rCartBOs.add(rCartBO);
        return rCartBOs;
    }

    private List<RCommodityBO> getCommodities() {
        rCommodityBOs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rCommodityBO = new RCommodityBO();
            rCommodityBO.count = i + 1;
            rCommodityBO.commodityId = i + 1;
            rCommodityBO.name = "测试商品" + (i + 1);
            rCommodityBO.price = 378 + i + "";
            rCommodityBOs.add(rCommodityBO);
        }
        return rCommodityBOs;
    }

    private List<RCommodityBO> getCommodities2() {
        rCommodityBOs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            rCommodityBO = new RCommodityBO();
            rCommodityBO.count = i + 1;
            rCommodityBO.commodityId = i + 10;
            rCommodityBO.name = "添加商品" + (i + 1);
            rCommodityBO.price = 478 + i + "";
            rCommodityBOs.add(rCommodityBO);
        }
        rCommodityBO = new RCommodityBO();
        rCommodityBO.count = 4;
        rCommodityBO.commodityId = 1;
        rCommodityBO.name = "测试商品10";
        rCommodityBO.price = "480";
        rCommodityBOs.add(rCommodityBO);
        return rCommodityBOs;
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
                    buyLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.VISIBLE);
                    //初始化选中状态
                    isClick = false;
                    allEditChk.setChecked(false);
                    isClick = true;
                    adapter.isEdit = true;
                    adapter.removeCount = 0;
                    adapter.cancelAll();
                    adapter.notifyDataSetChanged();
                } else {//完成
                    editTxt.setText("编辑");
                    buyLayout.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                    adapter.isEdit = false;
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.txt_delete:
                if (adapter.removeCount == 0) {
                    MyUtils.showToast(getActivity().getApplicationContext(), "您还没有选择商品哦!");
                    return;
                }
                showDialog();
                break;
            case R.id.txt_dialog_minus:
                dialogCountEdt.setText(--count + "");
                LogUtils.d(TAG, "减" + count);
                break;
            case R.id.txt_dialog_plus:
                dialogCountEdt.setText(++count + "");
                break;
            case R.id.btn_dialog_cancel:
                popUpWindow.dismiss();
                break;
            case R.id.btn_dialog_confirm:
                addAndMinusView.setCount(count);
                adapter.setCount(groupPosition, childPosition, count);
                popUpWindow.dismiss();
                break;
        }
    }

    public void showEditPopupWindow(int groupPosition, int childPosition, int count, AddAndMinusView addAndMinusView) {
        if (popUpWindow == null)
            popUpWindow = new EditPopUpWindow(getActivity());

        this.count = count;
        this.addAndMinusView = addAndMinusView;
        this.groupPosition=groupPosition;
        this.childPosition=childPosition;

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
        Dialog dialog = new AlertDialog.Builder(getActivity()).setMessage("确认要删除这" + adapter.removeCount + "种商品吗?").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.remove();
                adapter.removeCount = 0;
                if (adapter.getGroupCount() == 0) {
                    listView.setVisibility(View.GONE);
                    buyLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.GONE);
                    editTxt.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);

                    allBuyChk.setChecked(false);
                    adapter.isEdit = false;
                }
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
            add(getList2());
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
            buyLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
            editTxt.setText("编辑");
            editTxt.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    public void add(List<RCartBO> rCartBOs) {
        Iterator<RCartBO> iterator = rCartBOs.iterator();
        final List<RCartBO> data = adapter.getGroups();
        while (iterator.hasNext()) {
            LogUtils.d(TAG, "While");
            RCartBO obj = iterator.next();
            if (data.contains(obj)) {
                int i = data.indexOf(obj);
                //遍历新增的商品和老的商品并比较
                int newLen = obj.getChildrenCount();
                for (int j = 0; j < newLen; j++) {
                    if (!data.get(i).rCommodityBOs.contains(obj.rCommodityBOs.get(j))) {//不存在则添加新商品
                        data.get(i).rCommodityBOs.add(0, obj.rCommodityBOs.get(j));
                        LogUtils.d(TAG, "店铺" + obj.shop + "添加了商品" + obj.rCommodityBOs.get(j).name);
                    } else {//存在则更新
                        int position = data.get(i).rCommodityBOs.indexOf(obj.rCommodityBOs.get(j));
                        data.get(i).rCommodityBOs.set(position, obj.rCommodityBOs.get(j));
                        LogUtils.d(TAG, "店铺" + obj.shop + "更新了商品" + obj.rCommodityBOs.get(j).name);
                    }
                    obj.rCommodityBOs.get(j).isBuyChecked = data.get(i).isBuyChecked;
                }
            } else {
                LogUtils.d(TAG, "添加了一个店铺:" + obj.shop);
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
            dialogMinusTxt = (TextView) view.findViewById(R.id.txt_dialog_minus);
            dialogPlusTxt = (TextView) view.findViewById(R.id.txt_dialog_plus);
            dialogCancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
            dialogConfirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);

            dialogMinusTxt.setOnClickListener(CartFragment.this);
            dialogPlusTxt.setOnClickListener(CartFragment.this);
            dialogCancelBtn.setOnClickListener(CartFragment.this);
            dialogConfirmBtn.setOnClickListener(CartFragment.this);

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
                    if (temp.length() == 0)
                        return;
                    if (temp.length() == 1 && temp.equals("0")) {
                        s.replace(0, 1, "1");
                        count = 1;
                    }
                    if (Integer.parseInt(temp) == 1) {
                        dialogMinusTxt.setClickable(false);
                    } else {
                        dialogMinusTxt.setClickable(true);
                    }
                    count = Integer.parseInt(temp);
                }
            });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int height_top = view.findViewById(R.id.llayout_edit_count).getTop();
                    int height_bottom = view.findViewById(R.id.llayout_edit_count).getBottom();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height_top || y > height_bottom) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });

            this.setContentView(view);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            this.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    MyUtils.hiddenKeyBoard(getActivity(), view);
                }
            });

            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);
        }
    }
}
