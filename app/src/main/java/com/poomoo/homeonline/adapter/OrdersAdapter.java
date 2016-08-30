package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.CancelClickListener;
import com.poomoo.homeonline.listeners.CommodityClickListener;
import com.poomoo.homeonline.listeners.DeleteClickListener;
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.homeonline.listeners.PayClickListener;
import com.poomoo.homeonline.listeners.ReFundClickListener;
import com.poomoo.homeonline.listeners.ReceiptClickListener;
import com.poomoo.homeonline.listeners.TraceClickListener;
import com.poomoo.homeonline.listeners.UrgeClickListener;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.ROrderListBO;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.DELETE;

/**
 * 类名 OrdersAdapter
 * 描述 订单适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:32
 */
public class OrdersAdapter extends BaseListAdapter<ROrderListBO> {
    private PayClickListener payClickListener;
    private UrgeClickListener urgeClickListener;
    private ReceiptClickListener receiptClickListener;
    private EvaluateClickListener evaluateClickListener;
    private CancelClickListener cancelClickListener;
    private TraceClickListener traceClickListener;
    private DeleteClickListener deleteClickListener;
    private ReFundClickListener reFundClickListener;
    private OrderCommoditiesAdapter orderCommoditiesAdapter;
    private CommodityClickListener commodityClickListener;

    private DecimalFormat df = new DecimalFormat("0.00");

    public OrdersAdapter(Context context, int mode, PayClickListener payClickListener, UrgeClickListener urgeClickListener, ReceiptClickListener receiptClickListener, EvaluateClickListener evaluateClickListener, CancelClickListener cancelClickListener, TraceClickListener traceClickListener, DeleteClickListener deleteClickListener, ReFundClickListener reFundClickListener, CommodityClickListener commodityClickListener) {
        super(context, mode);
        this.payClickListener = payClickListener;
        this.urgeClickListener = urgeClickListener;
        this.receiptClickListener = receiptClickListener;
        this.evaluateClickListener = evaluateClickListener;
        this.cancelClickListener = cancelClickListener;
        this.traceClickListener = traceClickListener;
        this.reFundClickListener = reFundClickListener;
        this.deleteClickListener = deleteClickListener;
        this.commodityClickListener = commodityClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_order, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        ROrderListBO item = items.get(position);

        holder.orderIdTxt.setText(item.order.orderId + "");

        orderCommoditiesAdapter = new OrderCommoditiesAdapter(mContext, BaseListAdapter.NEITHER, position, reFundClickListener, item.order.state);
        holder.recyclerView.setLayoutManager(new ScrollLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.recyclerView.setAdapter(orderCommoditiesAdapter);
        orderCommoditiesAdapter.setItems(item.orderDetails);
        orderCommoditiesAdapter.setOnItemClickListener(new click(position));

        holder.allPriceTxt.setText("￥" + df.format(item.order.sumMoney));
        holder.freightPriceTxt.setText("￥" + df.format(item.order.deliveryFee));

        switch (item.order.state) {
            case ROrderBO.ORDER_PAY:
                holder.btn1.setText("付款");
                holder.btn1.setOnClickListener(new payOnclick(position));
                holder.btn2.setText("取消订单");
                holder.btn2.setOnClickListener(new cancelOnclick(position));
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setVisibility(View.GONE);
                holder.orderStatusTxt.setText("待付款");
                break;
            case ROrderBO.ORDER_DELIVER:
                holder.btn1.setText("催单");
                holder.btn1.setOnClickListener(new urgeOnclick(position));
                holder.btn2.setVisibility(View.GONE);
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setText(item.order.payWay == 1 ? "在线支付" : "货到付款");
                holder.orderStatusTxt.setText("待发货");
                break;
            case ROrderBO.ORDER_RECEIPT:
                holder.btn1.setText("确认收货");
                holder.btn1.setOnClickListener(new confirmOnclick(position));
                holder.btn2.setVisibility(View.GONE);
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setText(item.order.payWay == 1 ? "在线支付" : "货到付款");
                holder.orderStatusTxt.setText("待收货");
                break;
            case ROrderBO.ORDER_EVALUATE:
                holder.btn1.setText("评价");
                holder.btn1.setOnClickListener(new evaluateOnclick(position));
                holder.btn2.setVisibility(View.GONE);
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setText(item.order.payWay == 1 ? "在线支付" : "货到付款");
                holder.orderStatusTxt.setText("待评价");
                break;

            case ROrderBO.ORDER_FINISH:
                holder.btn1.setVisibility(View.GONE);
                holder.btn2.setText("删除订单");
                holder.btn2.setOnClickListener(new deleteOnclick(position));
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setText(item.order.payWay == 1 ? "在线支付" : "货到付款");
                holder.orderStatusTxt.setText("已完成");
                break;
            case ROrderBO.ORDER_USERCANCEL:
                holder.btn1.setVisibility(View.GONE);
                holder.btn2.setText("删除订单");
                holder.btn2.setOnClickListener(new deleteOnclick(position));
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setVisibility(View.GONE);
                holder.orderStatusTxt.setText("用户取消");
                break;
            case ROrderBO.ORDER_SYSYCANCEL:
                holder.btn1.setVisibility(View.GONE);
                holder.btn2.setText("删除订单");
                holder.btn2.setOnClickListener(new deleteOnclick(position));
                holder.btn3.setVisibility(View.GONE);

                holder.orderPayWayTxt.setVisibility(View.GONE);
                holder.orderStatusTxt.setText("系统取消");
                break;
        }
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_orderId)
        TextView orderIdTxt;
        @Bind(R.id.txt_orderStatus)
        TextView orderStatusTxt;
        @Bind(R.id.txt_orderPayWay)
        TextView orderPayWayTxt;
        @Bind(R.id.recycler_order_commodities)
        RecyclerView recyclerView;
        @Bind(R.id.txt_all_commodity_price)
        TextView allPriceTxt;
        @Bind(R.id.txt_freight_price)
        TextView freightPriceTxt;
        @Bind(R.id.btn_order_1)
        Button btn1;
        @Bind(R.id.btn_order_2)
        Button btn2;
        @Bind(R.id.btn_order_3)
        Button btn3;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 付款
     */
    class payOnclick implements View.OnClickListener {
        int position;

        public payOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            payClickListener.pay(position);
        }
    }

    /**
     * 催单
     */
    class urgeOnclick implements View.OnClickListener {
        int position;

        public urgeOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            urgeClickListener.urge(position);
        }
    }

    /**
     * 确认收货
     */
    class confirmOnclick implements View.OnClickListener {
        int position;

        public confirmOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            receiptClickListener.receipt(position);
        }
    }

    /**
     * 评价
     */
    class evaluateOnclick implements View.OnClickListener {
        int position;

        public evaluateOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            evaluateClickListener.evaluate(position);
        }
    }

    /**
     * 查看物流
     */
    class traceOnclick implements View.OnClickListener {
        int position;

        public traceOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            traceClickListener.trace(position);
        }
    }

    /**
     * 取消订单
     */
    class cancelOnclick implements View.OnClickListener {
        int position;

        public cancelOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            cancelClickListener.cancel(position);
        }
    }

    /**
     * 删除订单
     */
    class deleteOnclick implements View.OnClickListener {
        int position;

        public deleteOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            deleteClickListener.delete(position);
        }
    }

    class click implements OnItemClickListener {
        public int groupPos;

        public click(int groupPos) {
            this.groupPos = groupPos;
        }

        @Override
        public void onItemClick(int position, long id, View view) {
            commodityClickListener.click(groupPos, position);
        }
    }

}
