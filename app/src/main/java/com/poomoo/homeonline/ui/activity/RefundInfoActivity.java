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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.presenters.RefundInfoPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 RefundInfoActivity
 * 描述 退款详情
 * 作者 李苜菲
 * 日期 2016/8/24 17:28
 */
public class RefundInfoActivity extends BaseDaggerActivity<RefundInfoPresenter> {
    @Bind(R.id.llayout_refund_wait)
    LinearLayout waitLayout;
    @Bind(R.id.llayout_refund_successful)
    LinearLayout successfulLayout;
    @Bind(R.id.txt_refund_top_amount)
    TextView topAmountTxt;
    @Bind(R.id.txt_refund_successful_date)
    TextView successfulDateTxt;
    @Bind(R.id.llayout_refund_failed)
    LinearLayout failedLayout;
    @Bind(R.id.txt_refund_failed_reason)
    TextView failedReasonTxt;
    @Bind(R.id.txt_refund_failed_date)
    TextView failedDateTxt;
    @Bind(R.id.img_arrow)
    ImageView arrowImg;
    @Bind(R.id.scroll_refund)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_refund_info;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_refundInfo;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        setBack();
    }

    /**
     * @param view
     */
    public void alterRefund(View view) {

    }

    /**
     * 展示详情
     *
     * @param view
     */
    public void toShow(View view) {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
            arrowImg.setImageResource(R.drawable.arrow_up);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            arrowImg.setImageResource(R.drawable.arrow_down);

        }
    }

}
