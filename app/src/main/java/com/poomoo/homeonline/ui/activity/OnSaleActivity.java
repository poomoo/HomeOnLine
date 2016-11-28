/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.OnSaleGridAdapter;
import com.poomoo.homeonline.presenters.OnSalePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.CustomDialog;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.popup.RulePopupWindow;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.ROnSaleBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 OnSaleActivity
 * 描述 优惠券专区
 * 作者 李苜菲
 * 日期 2016/10/31 15:26
 */
public class OnSaleActivity extends BaseDaggerActivity<OnSalePresenter> implements ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scroll_on_sale)
    ScrollView scrollView;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_1)
    ImageView img1;
    @Bind(R.id.img_2)
    ImageView img2;
    @Bind(R.id.img_3)
    ImageView img3;
    @Bind(R.id.img_4)
    ImageView img4;
    @Bind(R.id.llayout_on_sale_zone)
    LinearLayout zoneLayout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private OnSaleGridAdapter onSaleGridAdapter;
    private RListCommodityBO rListCommodityBO;
    private List<List<RListCommodityBO>> lists = new ArrayList<>();
    private Bundle bundle;
    private String[] ad;
    private String[] name;
    private RAdBO rAdBO;
    private int len = 0;
    private RulePopupWindow rulePopupWindow;
    private int flag = 0;//0-获取专区信息 1-领券
    private String money = "";//优惠券面额
    private int POSITION = 0;
    private String rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setBack();
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_get_ticket;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_ticket_zone;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) / 2));//设置广告栏的宽高比为2:1
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getOnSaleInfo();
        errorLayout.setOnActiveClickListener(this);
    }

    public void successful(ROnSaleBO rOnSaleBO) {
        loadAd(rOnSaleBO.advlist);
        addView(rOnSaleBO);
        setImage(rOnSaleBO.yhqs);
        LogUtils.d(TAG, "优惠券图片:" + rOnSaleBO.yhqs);
        errorLayout.setState(ErrorLayout.HIDE, "");
        scrollView.setVisibility(View.VISIBLE);
    }

    private void setImage(List<ROnSaleBO.Pic> pics) {
        int width = MyUtils.getScreenWidth(this) - 3 * (int) getResources().getDimension(R.dimen.dp_30);
        width /= 2;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width / 2);
        img1.setLayoutParams(lp);
        img2.setLayoutParams(lp);
        img3.setLayoutParams(lp);
        img4.setLayoutParams(lp);
        Glide.with(this).load(NetConfig.ImageUrl + pics.get(0).voucherPic).placeholder(R.drawable.replace2).into(img1);
        Glide.with(this).load(NetConfig.ImageUrl + pics.get(1).voucherPic).placeholder(R.drawable.replace2).into(img2);
        Glide.with(this).load(NetConfig.ImageUrl + pics.get(2).voucherPic).placeholder(R.drawable.replace2).into(img3);
        Glide.with(this).load(NetConfig.ImageUrl + pics.get(3).voucherPic).placeholder(R.drawable.replace2).into(img4);
    }

    public void failed(String msg) {
        flag = 0;
            errorLayout.setState(ErrorLayout.LOAD_FAILED, "");
    }

    public void getTicketSuccessful() {
        errorLayout.setState(ErrorLayout.HIDE, "");
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("恭喜您已成功领券");
        builder.setPositiveButton("确定", (dialog, which) ->
                dialog.dismiss()
        );
        builder.create().show();
    }

    public void getTicketFailed(String msg) {
        flag = 1;
        if (isNetWorkInvalid(msg))
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
        else {
            errorLayout.setState(ErrorLayout.HIDE, "");
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setMessage(msg);
            builder.setPositiveButton("确定", (dialog, which) ->
                    dialog.dismiss()
            );
            builder.create().show();
        }

    }

    public void loadAd(List<RAdBO> rAdBOs) {
        len = rAdBOs.size();
        ad = new String[len];
        for (int i = 0; i < len; i++) {
            rAdBO = new RAdBO();
            rAdBO = rAdBOs.get(i);
            ad[i] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        }
        slideShowView.setPics(ad, position -> {
            rAdBO = rAdBOs.get(position);
            if (rAdBO.isCommodity) {
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rAdBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), rAdBO.commodityType);
                openActivity(CommodityInfoActivity.class, bundle);
            } else {
                bundle = new Bundle();
                bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                openActivity(WebViewActivity.class, bundle);
            }
        });
    }

    private void addView(ROnSaleBO rOnSaleBO) {
        name = getResources().getStringArray(R.array.on_sale_type);
        len = rOnSaleBO.commoditys.size();
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_on_sale, null);
            TextView titleTxt = (TextView) view.findViewById(R.id.txt_on_sale_name);
            NoScrollGridView gridView = (NoScrollGridView) view.findViewById(R.id.grid_on_sale);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins((int) getResources().getDimension(R.dimen.dp_10), 0, 0, 0);
            titleTxt.setLayoutParams(lp);
            titleTxt.setText(name[i]);

            onSaleGridAdapter = new OnSaleGridAdapter(this);
            gridView.setAdapter(onSaleGridAdapter);
            onSaleGridAdapter.setItems(rOnSaleBO.commoditys.get(i));
            gridView.setTag(i);
            gridView.setOnItemClickListener((parent, view1, position, id) -> {
                POSITION = (int) ((View) view1.getParent()).getTag();
                rListCommodityBO = rOnSaleBO.commoditys.get(POSITION).get(position);
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), rListCommodityBO.commodityType);
                openActivity(CommodityInfoActivity.class, bundle);
            });
            zoneLayout.addView(view);
        }

        rule = rOnSaleBO.activityExplain;
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        switch (flag) {
            case 0:
                mPresenter.getOnSaleInfo();
                break;
            case 1:
                mPresenter.getTicket(application.getUserId(), money);
                break;
        }
    }

    /**
     * 查看规则
     *
     * @param view
     */
    public void catRule(View view) {
        if (rulePopupWindow == null) {
            rulePopupWindow = new RulePopupWindow(this, itemsOnClick);
            rulePopupWindow.setRule(rule);
        }
        // 显示窗口
        rulePopupWindow.showAtLocation(this.findViewById(R.id.rlayout_on_sale), Gravity.CENTER, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    // 为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            rulePopupWindow.dismiss();
        }
    };

    /**
     * 领取5元优惠券
     *
     * @param view
     */
    public void get5(View view) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        money = "5";
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTicket(application.getUserId(), money);
    }

    /**
     * 领取10元优惠券
     *
     * @param view
     */
    public void get10(View view) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        money = "10";
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTicket(application.getUserId(), money);
    }

    /**
     * 领取15元优惠券
     *
     * @param view
     */
    public void get15(View view) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        money = "15";
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTicket(application.getUserId(), money);
    }

    /**
     * 领取20元优惠券
     *
     * @param view
     */
    public void get20(View view) {
        if (!MyUtils.isLogin(this)) {
            openActivity(LogInActivity.class);
            MyUtils.showToast(context, "请先登录");
            return;
        }
        money = "20";
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getTicket(application.getUserId(), money);
    }
}
