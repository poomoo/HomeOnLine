package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.listeners.OnBuyCheckChangedListener;
import com.poomoo.homeonline.listeners.OnEditCheckChangedListener;
import com.poomoo.homeonline.ui.activity.MainNewActivity;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.homeonline.ui.fragment.CartFragment;
import com.poomoo.model.response.RCartBO;
import com.poomoo.model.response.RCommodityBO;

import java.util.List;

/**
 * expandableListView适配器
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private final String TAG = "MyAdapter";

    private Context context;
    private CartFragment cartFragment = null;
    private List<RCartBO> group;
    private RCartBO rCartBO;
    private RCommodityBO rCommodityBO;
    private OnBuyCheckChangedListener onBuyCheckChangedListener;
    private OnEditCheckChangedListener onEditCheckChangedListener;

    public boolean isEdit = false;//true -购买模式 false-编辑模式
    private double totalPrice = 0.00;
    public int removeCount = 0;//选择的删除项
    private int commodityKind = 0;

    public CartAdapter(Context context, List<RCartBO> group, OnBuyCheckChangedListener onBuyCheckChangedListener, OnEditCheckChangedListener onEditCheckChangedListener) {
        this.context = context;
        this.group = group;
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
        return group.get(groupPosition).rCommodityBOs.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).rCommodityBOs.get(childPosition);
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

    public List<RCartBO> getGroups() {
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
        holder.shopChk = (CheckBox) convertView.findViewById(R.id.chk_shop);
        holder.shopTxt = (TextView) convertView.findViewById(R.id.txt_shop);

        rCartBO = group.get(groupPosition);
        if (isEdit)
            holder.shopChk.setChecked(rCartBO.isEditChecked);
        else
            holder.shopChk.setChecked(rCartBO.isBuyChecked);
        holder.shopTxt.setText(rCartBO.shop);

        // 點擊 CheckBox 時，將狀態存起來
        holder.shopChk.setOnClickListener(new Group_CheckBox_Click(groupPosition));

        setTotalPrice();
        getTotalCommodity();

        MainNewActivity.INSTANCE.setInfoNum(3, commodityKind, commodityKind > 0 ? true : false);
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
        holder.commodityChk = (CheckBox) convertView.findViewById(R.id.chk_commodity);
        holder.commodityImg = (ImageView) convertView.findViewById(R.id.img_commodity);
        holder.commodityTxt = (TextView) convertView.findViewById(R.id.txt_commodity);
        holder.priceTxt = (TextView) convertView.findViewById(R.id.txt_price);
        holder.andMinusView = (AddAndMinusView) convertView.findViewById(R.id.addAndMinusView);
        holder.andMinusView.setOnCountChangeListener(new CountChange(groupPosition, childPosition));

        rCommodityBO = group.get(groupPosition).rCommodityBOs.get(childPosition);
        if (isEdit)
            holder.commodityChk.setChecked(rCommodityBO.isEditChecked);
        else
            holder.commodityChk.setChecked(rCommodityBO.isBuyChecked);
//        Glide.with(mContext).load(rCommodityBO.img).into(holder.commodityImg);
        holder.commodityTxt.setText(rCommodityBO.name);
        holder.priceTxt.setText(rCommodityBO.price);
        holder.andMinusView.setCount(rCommodityBO.count);

        // 點擊 CheckBox 時，將狀態存起來
        holder.commodityChk.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));

        setTotalPrice();
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

        public CountChange(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void count(int count) {
            group.get(groupPosition).rCommodityBOs.get(childPosition).count = count;
            setTotalPrice();
        }
    }

    class GroupViewHolder {
        CheckBox shopChk;
        TextView shopTxt;
    }

    class ChildViewHolder {
        CheckBox commodityChk;
        ImageView commodityImg;
        TextView commodityTxt;
        TextView priceTxt;
        AddAndMinusView andMinusView;
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

            for (int j = 0; j < group.get(i).rCommodityBOs.size(); j++)
                if (isEdit)
                    group.get(i).rCommodityBOs.get(j).isEditChecked = true;
                else
                    group.get(i).rCommodityBOs.get(j).isBuyChecked = true;
        }
        notifyDataSetChanged();
    }

    public void cancelAll() {
        for (int i = 0; i < group.size(); i++) {
            if (isEdit)
                group.get(i).isEditChecked = false;
            else
                group.get(i).isBuyChecked = false;
            for (int j = 0; j < group.get(i).rCommodityBOs.size(); j++)
                if (isEdit)
                    group.get(i).rCommodityBOs.get(j).isEditChecked = false;
                else
                    group.get(i).rCommodityBOs.get(j).isBuyChecked = false;
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
                    if (group.get(i).rCommodityBOs.get(j).isEditChecked) {
                        group.get(i).rCommodityBOs.remove(j);
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
        removeCount = 0;
        for (int i = 0; i < group.size(); i++)
            for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                rCommodityBO = group.get(i).rCommodityBOs.get(j);
                if (rCommodityBO.isBuyChecked)
                    totalPrice += Double.parseDouble(rCommodityBO.price) * rCommodityBO.count;
                if (rCommodityBO.isEditChecked)
                    removeCount++;
            }
//        LogUtils.d(TAG, "setTotalPrice:" + totalPrice);
        cartFragment.setTotalPrice(totalPrice + "");
    }

    public boolean isAllSelected() {
        if (isEdit)
            for (int i = 0; i < group.size(); i++)
                for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                    rCommodityBO = group.get(i).rCommodityBOs.get(j);
                    if (!rCommodityBO.isEditChecked)
                        return false;
                }
        else
            for (int i = 0; i < group.size(); i++)
                for (int j = 0; j < group.get(i).getChildrenCount(); j++) {
                    rCommodityBO = group.get(i).rCommodityBOs.get(j);
                    if (!rCommodityBO.isBuyChecked)
                        return false;
                }
        return true;
    }

    /**
     * 商品种类
     *
     * @return
     */
    public int getTotalCommodity() {
        commodityKind = 0;
        int len = group.size();
        for (int i = 0; i < len; i++)
            commodityKind += getChildrenCount(i);

        return commodityKind;
    }

}

