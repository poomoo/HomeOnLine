package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.listeners.OnBuyCheckChangedListener;
import com.poomoo.homeonline.listeners.OnEditCheckChangedListener;
import com.poomoo.homeonline.ui.activity.MainNewActivity;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.homeonline.ui.fragment.CartFragment;
import com.poomoo.model.response.RCartShopBO;
import com.poomoo.model.response.RCartCommodityBO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名 CartAdapter
 * 描述 购物车适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:30
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private final String TAG = "CartAdapter";

    private Context context;
    private CartFragment cartFragment = null;
    private List<RCartShopBO> group = new ArrayList<>();
    private RCartShopBO rCartShopBO;
    private RCartCommodityBO rCartCommodityBO;
    private OnBuyCheckChangedListener onBuyCheckChangedListener;
    private OnEditCheckChangedListener onEditCheckChangedListener;

    public boolean isEdit = false;//true -购买模式 false-编辑模式
    private double totalPrice = 0.00;
    //    public int removeCount = 0;//选择的删除项
    public List<Integer> deleteIndex;//选择的删除项下标
    private int commodityKind = 0;
    private int commodityCount = 0;

    private DialogPlus dialogPlus;
    private EditText dialogCountEdt;
    private TextView dialogMinusTxt;
    private TextView dialogPlusTxt;
    private AddAndMinusView addAndMinusView;
    private int count = 1;
    private DecimalFormat df = new DecimalFormat("#.00");


    public CartAdapter(Context context, OnBuyCheckChangedListener onBuyCheckChangedListener, OnEditCheckChangedListener onEditCheckChangedListener) {
        this.context = context;
        this.onBuyCheckChangedListener = onBuyCheckChangedListener;
        this.onEditCheckChangedListener = onEditCheckChangedListener;
        cartFragment = CartFragment.inStance;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.get(groupPosition).carts.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    public void setGroup(List<RCartShopBO> group) {
        this.group = group;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).carts.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public List<RCartShopBO> getGroups() {
        return group;
    }

    /**
     * 显示：group
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        LogUtils.d(TAG, "getGroupView:" + groupPosition);

        GroupViewHolder holder;
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_group, null);
        holder = new GroupViewHolder();
        holder.shopLayout = (LinearLayout) convertView.findViewById(R.id.llayout_shop);
        holder.shopChk = (CheckBox) convertView.findViewById(R.id.chk_shop);
        holder.shopTxt = (TextView) convertView.findViewById(R.id.txt_shop);

        rCartShopBO = group.get(groupPosition);
        if (isEdit)
            holder.shopChk.setChecked(rCartShopBO.isEditChecked);
        else
            holder.shopChk.setChecked(rCartShopBO.isBuyChecked);
        holder.shopTxt.setText(rCartShopBO.shopName);

        // 點擊 CheckBox 時，將狀態存起來
        holder.shopLayout.setOnClickListener(new Group_CheckBox_Click(groupPosition));

        setTotalPrice();
//        getTotalCommodity();

        return convertView;
    }

    class Group_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            if (isEdit)
                group.get(groupPosition).toggleEdit();
            else
                group.get(groupPosition).toggleBuy();

            // 將 Children 的 isChecked 全面設成跟 Group 一樣
            int childrenCount = group.get(groupPosition).getChildrenCount();
            boolean groupIsChecked;
            if (isEdit) {
                groupIsChecked = group.get(groupPosition).isEditChecked;
            } else
                groupIsChecked = group.get(groupPosition).isBuyChecked;

            for (int i = 0; i < childrenCount; i++)
                if (isEdit)
                    group.get(groupPosition).getChildItem(i).isEditChecked = groupIsChecked;
                else
                    group.get(groupPosition).getChildItem(i).isBuyChecked = groupIsChecked;

            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
            notifyDataSetChanged();
            if (isEdit)
                onEditCheckChangedListener.onEditClick(isAllSelected());
            else
                onBuyCheckChangedListener.onBuyClick(isAllSelected());
        }
    }

    /**
     * 显示：child
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        LogUtils.d(TAG, "getChildView:" + childPosition);
        ChildViewHolder holder;
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_commodity, null);
        holder = new ChildViewHolder();
        holder.commodityLayout = (LinearLayout) convertView.findViewById(R.id.llayout_commodity);
        holder.commodityChk = (CheckBox) convertView.findViewById(R.id.chk_commodity);
        holder.commodityImg = (ImageView) convertView.findViewById(R.id.img_commodity);
        holder.commodityTxt = (TextView) convertView.findViewById(R.id.txt_commodity);
        holder.priceTxt = (TextView) convertView.findViewById(R.id.txt_price);
        holder.addAndMinusView = (AddAndMinusView) convertView.findViewById(R.id.addAndMinusView);

        rCartCommodityBO = group.get(groupPosition).carts.get(childPosition);
        if (isEdit)
            holder.commodityChk.setChecked(rCartCommodityBO.isEditChecked);
        else
            holder.commodityChk.setChecked(rCartCommodityBO.isBuyChecked);
        Glide.with(context).load(NetConfig.ImageUrl + rCartCommodityBO.listPic).placeholder(R.drawable.replace).into(holder.commodityImg);
        holder.commodityTxt.setText(rCartCommodityBO.commodityName);
        holder.priceTxt.setText(rCartCommodityBO.commodityPrice + "");

        holder.addAndMinusView.setCount(rCartCommodityBO.commodityNum);
        holder.addAndMinusView.setOnCountChangeListener(new CountChange(rCartCommodityBO.id, groupPosition, childPosition, holder.addAndMinusView, rCartCommodityBO.commodityType));

        // 點擊 CheckBox 時，將狀態存起來
        holder.commodityLayout.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));

        setTotalPrice();
        getTotalCommodityCount();
        MainNewActivity.INSTANCE.setInfoNum(3, commodityCount, commodityCount > 0 ? true : false);
        return convertView;
    }

    /**
     * 勾選 Child CheckBox 時，存 Child CheckBox 的狀態
     */
    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            handleClick(childPosition, groupPosition);
            if (isEdit)
                onEditCheckChangedListener.onEditClick(isAllSelected());
            else
                onBuyCheckChangedListener.onBuyClick(isAllSelected());
        }
    }

    class CountChange implements AddAndMinusView.OnCountChangeListener {
        private int groupPosition;
        private int childPosition;
        private AddAndMinusView addAndMinusView;
        private int cartId;
        private int commodityType;

        public CountChange(int cartId, int groupPosition, int childPosition, AddAndMinusView andMinusView, int commodityType) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            this.addAndMinusView = andMinusView;
            this.cartId = cartId;
            this.commodityType = commodityType;
        }

        @Override
        public void count(int count, boolean isEdit) {
            LogUtils.d(TAG, "isEdit:" + isEdit);
            if (isEdit)
                cartFragment.showEditPopupWindow(cartId, count, groupPosition, childPosition, addAndMinusView, commodityType);
            else {
                cartFragment.changeCount(cartId, count, groupPosition, childPosition, addAndMinusView, commodityType);
//                setCount(groupPosition, childPosition, count);
            }
        }
    }

    public void setCount(int groupPosition, int childPosition, int count) {
        group.get(groupPosition).carts.get(childPosition).commodityNum = count;
//        setTotalPrice();
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        LinearLayout shopLayout;
        CheckBox shopChk;
        TextView shopTxt;
    }

    class ChildViewHolder {
        LinearLayout commodityLayout;
        CheckBox commodityChk;
        ImageView commodityImg;
        TextView commodityTxt;
        TextView priceTxt;
        AddAndMinusView addAndMinusView;
    }

    public void handleClick(int childPosition, int groupPosition) {
        if (isEdit)
            group.get(groupPosition).getChildItem(childPosition).toggleEdit();
        else
            group.get(groupPosition).getChildItem(childPosition).toggleBuy();

        // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox
        int childrenCount = group.get(groupPosition).getChildrenCount();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (isEdit) {
                if (!group.get(groupPosition).getChildItem(i).isEditChecked)
                    childrenAllIsChecked = false;
            } else {
                if (!group.get(groupPosition).getChildItem(i).isBuyChecked)
                    childrenAllIsChecked = false;
            }
        }
        if (isEdit)
            group.get(groupPosition).isEditChecked = childrenAllIsChecked;
        else
            group.get(groupPosition).isBuyChecked = childrenAllIsChecked;

        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    public void selectAll() {
        for (int i = 0; i < group.size(); i++) {
            if (isEdit)
                group.get(i).isEditChecked = true;
            else
                group.get(i).isBuyChecked = true;

            for (int j = 0; j < group.get(i).carts.size(); j++)
                if (isEdit)
                    group.get(i).carts.get(j).isEditChecked = true;
                else
                    group.get(i).carts.get(j).isBuyChecked = true;
        }
        notifyDataSetChanged();
    }

    public void cancelAll() {
        for (int i = 0; i < group.size(); i++) {
            if (isEdit)
                group.get(i).isEditChecked = false;
            else
                group.get(i).isBuyChecked = false;
            for (int j = 0; j < group.get(i).carts.size(); j++)
                if (isEdit)
                    group.get(i).carts.get(j).isEditChecked = false;
                else
                    group.get(i).carts.get(j).isBuyChecked = false;
        }
        notifyDataSetChanged();
    }

    public void remove() {
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).isEditChecked) {
                group.remove(i);
                i--;
            } else
                for (int j = 0; j < group.get(i).getChildrenCount(); j++)
                    if (group.get(i).carts.get(j).isEditChecked) {
                        group.get(i).carts.remove(j);
                        j--;
                    }
        }
        if (group.size() == 0)
            MainNewActivity.INSTANCE.setInfoNum(3, 0, false);
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setTotalPrice() {
        totalPrice = 0.00;
//        removeCount = 0;
        deleteIndex = new ArrayList<>();
        for (int i = 0; i < group.size(); i++)
            for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                rCartCommodityBO = group.get(i).carts.get(j);
                if (rCartCommodityBO.isBuyChecked)
                    totalPrice += rCartCommodityBO.commodityPrice * rCartCommodityBO.commodityNum;
                if (rCartCommodityBO.isEditChecked) {
//                    removeCount++;
                    deleteIndex.add(rCartCommodityBO.id);
                }

            }
//        LogUtils.d(TAG, "setTotalPrice:" + totalPrice);
        cartFragment.setTotalPrice(df.format(totalPrice));
    }

    public boolean isAllSelected() {
        if (isEdit)
            for (int i = 0; i < group.size(); i++)
                for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                    rCartCommodityBO = group.get(i).carts.get(j);
                    if (!rCartCommodityBO.isEditChecked)
                        return false;
                }
        else
            for (int i = 0; i < group.size(); i++)
                for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                    rCartCommodityBO = group.get(i).carts.get(j);
                    if (!rCartCommodityBO.isBuyChecked)
                        return false;
                }
        return true;
    }

    /**
     * 商品种类
     *
     * @return
     */
//    public int getTotalCommodity() {
//        commodityKind = 0;
//        int len = group.size();
//        for (int i = 0; i < len; i++)
//            commodityKind += getChildrenCount(i);
//
//        return commodityKind;
//    }
    public int getTotalCommodityCount() {
        commodityCount = 0;
        int groupSize = group.size();
        for (int i = 0; i < groupSize; i++) {
            int childSize = getChildrenCount(i);
            for (int j = 0; j < childSize; j++) {
                commodityCount += ((RCartCommodityBO) getChild(i, j)).commodityNum;
            }
        }
        MainNewActivity.INSTANCE.application.setCartNum(commodityCount);
        return commodityCount;
    }

//    private void createDialog() {
//        if (dialogPlus == null) {
//            final View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_count, null);
//            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            dialogCountEdt = (EditText) view.findViewById(R.id.edt_dialog_count);
//            dialogMinusTxt = (TextView) view.findViewById(R.id.txt_dialog_minus);
//            dialogPlusTxt = (TextView) view.findViewById(R.id.txt_dialog_plus);
//
//            dialogCountEdt.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    String temp = s.toString();
//                    if (temp.length() == 0)
//                        return;
//                    if (temp.length() == 1 && temp.equals("0")) {
//                        s.replace(0, 1, "1");
//                        count = 1;
//                    }
//                    if (Integer.parseInt(temp) == 1) {
//                        dialogMinusTxt.setClickable(false);
//                    } else {
//                        dialogMinusTxt.setClickable(true);
//                    }
//                }
//            });
//
//            Holder holder = new ViewHolder(view);
//            OnClickListener clickListener = (dialog, view1) -> {
//                switch (view1.getId()) {
//                    case R.id.txt_dialog_minus:
//                        dialogCountEdt.setText(--count + "");
//                        LogUtils.d(TAG, "减" + count);
//                        break;
//                    case R.id.txt_dialog_plus:
//                        dialogCountEdt.setText(++count + "");
//                        break;
//                    case R.id.btn_dialog_cancel:
//                        dialog.dismiss();
//                        MyUtils.hiddenKeyBoard(context, view1);
//                        break;
//                    case R.id.btn_dialog_confirm:
//                        addAndMinusView.setCount(count);
//                        dialog.dismiss();
//                        break;
//                }
//            };
//            dialogPlus = DialogPlus.newDialog(context)
//                    .setContentHolder(holder)
//                    .setGravity(Gravity.CENTER)
//                    .setCancelable(true)
//                    .setOnDismissListener(dialog -> MyUtils.hiddenKeyBoard(context, view))
//                    .setOnBackPressListener(dialogPlus1 -> dialogPlus1.dismiss())
//                    .setOnClickListener(clickListener)
//                    .create();
//        }
//        dialogCountEdt.setText(count + "");
//        dialogCountEdt.setFocusableInTouchMode(true);
//        dialogCountEdt.setFocusable(true);
//        dialogCountEdt.requestFocus();
//
//        dialogPlus.show();
//    }

}

