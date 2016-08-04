/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.TimeCountDownUtilBy3View;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ScrollGridLayoutManager;
import com.poomoo.homeonline.ScrollLinearLayoutManager;
import com.poomoo.homeonline.adapter.GuessAdapter;
import com.poomoo.homeonline.adapter.HotAdapter;
import com.poomoo.homeonline.adapter.MainGridAdapter;
import com.poomoo.homeonline.adapter.MainListAdapter;
import com.poomoo.homeonline.adapter.PicturesGridAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ScrollViewListener;
import com.poomoo.homeonline.presenters.MainFragmentPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.homeonline.ui.custom.MyScrollView;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RGuessBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RTypeBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 MainFragment
 * 描述 首页
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class MainFragment extends BaseDaggerFragment<MainFragmentPresenter> implements AdapterView.OnItemClickListener, BaseListAdapter.OnItemClickListener, ScrollViewListener {
    @Bind(R.id.scrollView_main)
    MyScrollView scrollView;
    @Bind(R.id.grid_menu)
    NoScrollGridView menuGrid;
    @Bind(R.id.txt_hour)
    TextView hourTxt;
    @Bind(R.id.txt_minute)
    TextView minuteTxt;
    @Bind(R.id.txt_second)
    TextView secondTxt;
    @Bind(R.id.recycler_main)
    RecyclerView grabRecycler;
    @Bind(R.id.img_qhcs_title)
    ImageView qhcsTitleImg;
    @Bind(R.id.img_qhcs_content)
    ImageView qhcsContentImg;
    @Bind(R.id.grid_qhcs)
    NoScrollGridView qhcsGrid;
    @Bind(R.id.img_lsyz_title)
    ImageView lsyzTitleImg;
    @Bind(R.id.img_lsyz_content)
    ImageView lsyzContentImg;
    @Bind(R.id.grid_lsyz)
    NoScrollGridView lsyzGrid;
    @Bind(R.id.flipper_ad)
    SlideShowView slideShowView;
    @Bind(R.id.img_top)
    ImageView topImg;
    @Bind(R.id.llayout_specialAd)
    LinearLayout specialAdLayout;
    @Bind(R.id.recycler_hot)
    RecyclerView hotRecycler;
    @Bind(R.id.recycler_guess)
    RecyclerView guessRecycler;

    private MainGridAdapter gridAdapter;
    private MainListAdapter adapter;
    private HotAdapter hotAdapter;
    private GuessAdapter guessAdapter;
    private PicturesGridAdapter qhcsGridAdapter;
    private PicturesGridAdapter lsyzGridAdapter;
    private TimeCountDownUtilBy3View timeCountDownUtilBy3View;

    private String[] ad = {"http://img.jiayou9.com/jyzx/upload/company/20160621/20160621235614_798.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160617/20160617152510_21.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160617/20160617153952_548.jpg"};
    private String[] qhcs = {"http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133639_805.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133706_479.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133619_901.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133559_450.jpg"};
    private String[] lsyz = {"http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133432_159.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160622/20160622135718_235.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133453_903.jpg", "http://img.jiayou9.com/jyzx/upload/company/20160618/20160618133539_91.jpg"};
    private List<RTypeBO> rTypeBOs = new ArrayList<>();
    private List<String> qhcsList = new ArrayList<>();
    private List<String> lsyzList = new ArrayList<>();
    private RTypeBO rTypeBO;
    private int len = 0;
    private RAdBO rAdBO;
    private boolean isLoadAd = false;
    private final int GRAB = 1;
    private final int HOT = 2;
    private final int GUESS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    protected void setupFragmentComponent(FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }


    private void initView() {
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(getActivity()) / 2));//设置广告栏的宽高比为2:1
        gridAdapter = new MainGridAdapter(getActivity());
        menuGrid.setAdapter(gridAdapter);
        menuGrid.setOnItemClickListener(this);

        mPresenter.getSlide();
        mPresenter.getType();
        mPresenter.getGrabList();
        mPresenter.getSpecialAd();
        mPresenter.getHot();
        mPresenter.getGuess();


        initCountDownTime();

        initGrab();

        initHot();

        initGuess();

//        initType();
//
//        initQhcs();
//
//        initLsyz();

        scrollView.setScrollViewListener(this);

        topImg.setOnClickListener(v -> scrollView.smoothScrollTo(0, 0));
    }


    /**
     * 动态增加专题广告
     */
    private void addView(RSpecialAdBO rSpecialAdBO) {
        int len = rSpecialAdBO.advs.size();
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.special_layout, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LogUtils.d(TAG, "lp:" + lp);
            lp.setMargins(0, (int) getResources().getDimension(R.dimen.dp_8), 0, 0);
            view.setLayoutParams(lp);
            ImageView titleImg = (ImageView) view.findViewById(R.id.img_special_title);
            ImageView contentImg = (ImageView) view.findViewById(R.id.img_special_content);
            NoScrollGridView gridView = (NoScrollGridView) view.findViewById(R.id.grid_special);

            PicturesGridAdapter picturesGridAdapter = new PicturesGridAdapter(getActivity());
            gridView.setAdapter(picturesGridAdapter);
            picturesGridAdapter.setItems(rSpecialAdBO.advs.get(i).subList(1, rSpecialAdBO.advs.get(i).size()));
            gridView.setTag(i);
            gridView.setOnItemClickListener((parent, view1, position, id) ->
                    MyUtils.showToast(getActivity().getApplicationContext(), "点击了第" + parent.getTag() + "个专题的" + "第" + position + "个广告" + "  是否是商品广告:" + rSpecialAdBO.advs.get((int) parent.getTag()).get(position).isCommodity)
            );

            Glide.with(getActivity()).load(getString(R.string.base_url) + "/weixin/images/index-market-" + (i + 1) + ".png").into(titleImg);
            contentImg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(getActivity()) * 5 / 12));//设置广告栏的宽高比为2:1
            Glide.with(getActivity()).load(NetConfig.ImageUrl + rSpecialAdBO.advs.get(i).get(0).advertisementPic).placeholder(R.drawable.replace12b5).into(contentImg);
//            Glide.with(getActivity()).load("http://img.jiayou9.com/jyzx/upload/company/20160622/20160622173551_317.jpg").placeholder(R.drawable.replace12b5).into(contentImg);
            specialAdLayout.addView(view, i + 5);

        }
    }

    /**
     * 限时抢购
     */
    private void initGrab() {
        grabRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        grabRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
//                .color(getResources().getColor(R.color.transparent))
//                .size((int) getResources().getDimension(R.dimen.divider_height2))
//                .build());

        adapter = new MainListAdapter(getActivity(), BaseListAdapter.NEITHER);
        grabRecycler.setAdapter(adapter);
        grabRecycler.setTag(GRAB);
        adapter.setOnItemClickListener(this);
//        initCommodities();
    }

    /**
     * 热门推荐
     */
    private void initHot() {
//        hotRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        hotRecycler.setLayoutManager(new ScrollLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        hotRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        hotAdapter = new HotAdapter(getActivity(), BaseListAdapter.NEITHER);
        hotRecycler.setAdapter(hotAdapter);
        hotRecycler.setTag(HOT);
        hotAdapter.setOnItemClickListener(this);
    }

    /**
     * 猜你喜欢
     */
    private void initGuess() {
//        guessRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        guessRecycler.setLayoutManager(new ScrollGridLayoutManager(getActivity(), 2));
        guessRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        guessRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        guessAdapter = new GuessAdapter(getActivity(), BaseListAdapter.NEITHER);
        guessRecycler.setAdapter(guessAdapter);
        guessRecycler.setTag(GUESS);
        guessAdapter.setOnItemClickListener(this);
    }

//    private void initCommodities() {
//        RRecommendBO rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100836_78.jpg";
//        rRecommendBO.newPrice = "10.80";
//        rRecommendBO.oldPrice = "10.80";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101204_324.jpg";
//        rRecommendBO.newPrice = "68";
//        rRecommendBO.oldPrice = "68";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101635_324.jpg";
//        rRecommendBO.newPrice = "8.18";
//        rRecommendBO.oldPrice = "8.18";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100543_124.jpg";
//        rRecommendBO.newPrice = "15.20";
//        rRecommendBO.oldPrice = "15.20";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101204_324.jpg";
//        rRecommendBO.newPrice = "68";
//        rRecommendBO.oldPrice = "68";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628101635_324.jpg";
//        rRecommendBO.newPrice = "8.18";
//        rRecommendBO.oldPrice = "8.18";
//        rRecommendBOs.add(rRecommendBO);
//
//        rRecommendBO = new RRecommendBO();
//        rRecommendBO.img = "http://img.jiayou9.com/jyzx//upload/company/20160628/20160628100543_124.jpg";
//        rRecommendBO.newPrice = "15.20";
//        rRecommendBO.oldPrice = "15.20";
//        rRecommendBOs.add(rRecommendBO);
//
//        adapter.addItems(rRecommendBOs);
//    }

    private void initCountDownTime() {
//        timeCountDownUtilBy3View = new TimeCountDownUtilBy3View(5 * 60 * 60 * 1000, 1000, hourTxt, minuteTxt, secondTxt);
        timeCountDownUtilBy3View = new TimeCountDownUtilBy3View(MyUtils.DateToTime("2016-06-28 18:00:00"), 1000, hourTxt, minuteTxt, secondTxt);
        timeCountDownUtilBy3View.start();
    }

//    private void initType() {
////        for (int i = 0; i < 8; i++) {
////            rTypeBO = new RTypeBO();
////            rTypeBO.icon = getString(R.string.base_url) + "/weixin/images/icon" + (i + 1) + ".png";
////            rTypeBO.name = MyConfig.classify[i];
////            rTypeBOs.add(rTypeBO);
////        }
//
//    }
//
//    private void initQhcs() {
//        Glide.with(getActivity()).load(getString(R.string.base_url) + "/weixin/images/index-market-1.png").into(qhcsTitleImg);
//        Glide.with(getActivity()).load("http://img.jiayou9.com/jyzx/upload/company/20160622/20160622173551_317.jpg").into(qhcsContentImg);
//
//        len = qhcs.length;
//        LogUtils.d(TAG, "initQhcs" + len);
//        for (int i = 0; i < len; i++)
//            qhcsList.add(qhcs[i]);
//        qhcsGridAdapter = new PicturesGridAdapter(getActivity());
//        qhcsGrid.setAdapter(qhcsGridAdapter);
//        qhcsGridAdapter.setItems(qhcsList);
//
//        qhcsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putBoolean(getString(R.string.intent_value), position % 2 == 0 ? true : false);
//                openActivity(CommodityInfoActivity.class, bundle);
//            }
//        });
//    }
//
//    private void initLsyz() {
//        Glide.with(getActivity()).load(getString(R.string.base_url) + "/weixin/images/index-market-2.png").into(lsyzTitleImg);
//        Glide.with(getActivity()).load("http://img.jiayou9.com/jyzx/upload/company/20160622/20160622172550_936.jpg").into(lsyzContentImg);
//
//        len = lsyz.length;
//        LogUtils.d(TAG, "initLsyz" + len);
//        for (int i = 0; i < len; i++)
//            lsyzList.add(lsyz[i]);
//        lsyzGridAdapter = new PicturesGridAdapter(getActivity());
//        lsyzGrid.setAdapter(lsyzGridAdapter);
//        lsyzGridAdapter.setItems(lsyzList);
//    }

    public void loadSlideSucceed(List<RAdBO> rAdBOs) {
        isLoadAd = true;
        int len = rAdBOs.size();
        ad = new String[len];
        for (int i = 0; i < len; i++) {
            rAdBO = new RAdBO();
            rAdBO = rAdBOs.get(i);
            ad[i] = NetConfig.ImageUrl + rAdBO.advertisementPic;
        }
        slideShowView.setPics(ad, position -> {

        });
    }

    public void loadSlideFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    public void loadTypeSucceed(RTypeBO rTypeBO) {
        gridAdapter.setUrl(rTypeBO.picUrl);
        gridAdapter.setItems(rTypeBO.categotys);
    }

    public void loadTypeFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    public void loadGrabListSucceed(List<RGrabBO> rGrabBOs) {
        adapter.addItems(rGrabBOs);
    }

    public void loadGrabListFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    public void loadSpecialAdSucceed(RSpecialAdBO rSpecialAdBO) {
        LogUtils.d(TAG, "loadSpecialAdSucceed:" + rSpecialAdBO);
        addView(rSpecialAdBO);
    }

    public void loadSpecialAdFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    public void loadHotSucceed(List<RAdBO> rAdBOs) {
        hotAdapter.setItems(rAdBOs);
    }

    public void loadHotFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    public void loadGuessSucceed(List<RGuessBO> rGuessBOs) {
        guessAdapter.setItems(rGuessBOs);
    }

    public void loadGuessFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    /**
     * 分类菜单点击
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * recycler点击
     *
     * @param position
     * @param id
     * @param view
     */
    @Override
    public void onItemClick(int position, long id, View view) {
        switch (((View) view.getParent()).getId()) {
            case R.id.recycler_main:
                break;
            case R.id.recycler_hot:
                break;
            case R.id.recycler_guess:
                MyUtils.showToast(getActivity().getApplicationContext(), "点击了猜你喜欢的第" + position + "个");
                break;
        }
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView.getScrollY() > scrollView.getHeight())
            topImg.setVisibility(View.VISIBLE);
        else
            topImg.setVisibility(View.INVISIBLE);
    }
}
