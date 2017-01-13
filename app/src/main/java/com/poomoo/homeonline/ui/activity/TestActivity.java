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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RListCommodityBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 TestActivity
 * 描述 ${TODO}
 * 作者 李苜菲
 * 日期 2016/8/8 14:58
 */
public class TestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
//    @Bind(R.id.img_special_content)
//    ImageView imageView;
//    @Bind(R.id.webview)
//    WebView webView;
    @Bind(R.id.error_frame)
    ErrorLayout mErrorLayout;
//    @Bind(R.id.scrollView_classify_info)
//    ScrollView scrollView;
    @Bind(R.id.recycler_guess)
    RecyclerView commodityRecycler;

    private ListCommodityAdapter listCommodityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    private void init() {
//        webView.setWebViewClient(new webViewClient());
//        webView.setWebChromeClient(new MyWebChromeClient());
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
//        webView.getSettings().setUseWideViewPort(true);
//        webView.loadUrl(NetConfig.grabUrl);

//        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(this) * 5 / 12));//设置广告栏的宽高比为2:1
//        Glide.with(this).load(R.drawable.ic_add_image).into(imageView);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);

        commodityRecycler.setLayoutManager(new ScrollGridLayoutManager(this, 2));
        commodityRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        commodityRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        listCommodityAdapter = new ListCommodityAdapter(this, BaseListAdapter.NEITHER, ListCommodityAdapter.COMMON);
        commodityRecycler.setAdapter(listCommodityAdapter);

        listCommodityAdapter.setItems(getList());
    }

    private List<RListCommodityBO> getList() {
        List<RListCommodityBO> rListCommodityBOs = new ArrayList<>();
        RListCommodityBO rListCommodityBO;
        for (int i = 0; i < 20; i++) {
            rListCommodityBO = new RListCommodityBO();
            rListCommodityBO.commodityName = "测试商品" + (i + 1);
            rListCommodityBO.platformPrice = (i + 1) * 1.5;
            rListCommodityBOs.add(rListCommodityBO);
        }
        return rListCommodityBOs;
    }

    @Override
    public void onRefresh() {

    }

    class MyWebChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            LogUtils.d(TAG, "onProgressChanged" + progress);
            if (progress == 100) {
//                webView.setVisibility(View.VISIBLE);
                mErrorLayout.setState(ErrorLayout.HIDE, "");
            }
        }
    }

    class webViewClient extends WebViewClient {
        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }


        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        }
    }

}
