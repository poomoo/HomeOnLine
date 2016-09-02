/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.commlib.TimeCountDownUtilBy3View;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.HotAdapter;
import com.poomoo.homeonline.adapter.ListCommodityAdapter;
import com.poomoo.homeonline.adapter.MainGrabAdapter;
import com.poomoo.homeonline.adapter.MainGridAdapter;
import com.poomoo.homeonline.adapter.PicturesGridAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ScrollViewListener;
import com.poomoo.homeonline.presenters.MainFragmentPresenter;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollGridLayoutManager;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.ClassifyInfoActivity;
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.homeonline.ui.activity.MainNewActivity;
import com.poomoo.homeonline.ui.activity.SearchActivity;
import com.poomoo.homeonline.ui.activity.WebViewActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.homeonline.ui.custom.MyScrollView;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RCateBO;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RGrabBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RSpecialAdBO;
import com.poomoo.model.response.RTypeBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 MainFragment
 * 描述 首页
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class MainFragment extends BaseDaggerFragment<MainFragmentPresenter> implements AdapterView.OnItemClickListener, BaseListAdapter.OnItemClickListener, ScrollViewListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
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
    @Bind(R.id.recycler_grab)
    RecyclerView grabRecycler;
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
    private MainGrabAdapter grabAdapter;
    private HotAdapter hotAdapter;
    private ListCommodityAdapter listCommodityAdapter;
    private TimeCountDownUtilBy3View timeCountDownUtilBy3View;

    private String[] ad;
    private RAdBO rAdBO;
    private RListCommodityBO rListCommodityBO;
    private RGrabBO rGrabBO;
    private final int GRAB = 1;
    private final int HOT = 2;
    private final int GUESS = 3;
    private Bundle bundle;
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(getActivity()) / 2));//设置广告栏的宽高比为2:1
        gridAdapter = new MainGridAdapter(getActivity());
        menuGrid.setAdapter(gridAdapter);
        menuGrid.setOnItemClickListener(this);
        mPresenter.getSlide();
        mPresenter.getType();
        mPresenter.getGrabList();
        mPresenter.getSpecialAd();
        mPresenter.getHot();
        mPresenter.getGuess(application.getUserId());

        initCountDownTime();

        initGrab();

        initHot();

        initGuess();

        scrollView.setScrollViewListener(this);

        topImg.setOnClickListener(v -> scrollView.smoothScrollTo(0, 0));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );

        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    @OnClick({R.id.llayout_search, R.id.llayout_toGrab})
    void search(View view) {
        switch (view.getId()) {
            case R.id.llayout_search:
                openActivity(SearchActivity.class);
                break;
            case R.id.llayout_toGrab:
                ((MainNewActivity) getActivity()).jump(2);
                break;
        }
    }

    /**
     * 动态增加专题广告
     */
    private void addView(RSpecialAdBO rSpecialAdBO) {
        specialAdLayout.removeAllViews();
        int len = rSpecialAdBO.advs.size();
        for (int i = 0; i < len; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_special, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, (int) getResources().getDimension(R.dimen.dp_8), 0, 0);
            view.setLayoutParams(lp);
            ImageView titleImg = (ImageView) view.findViewById(R.id.img_special_title);
            ImageView contentImg = (ImageView) view.findViewById(R.id.img_special_content);
            NoScrollGridView gridView = (NoScrollGridView) view.findViewById(R.id.grid_special);

            PicturesGridAdapter picturesGridAdapter = new PicturesGridAdapter(getActivity());
            gridView.setAdapter(picturesGridAdapter);
            picturesGridAdapter.setItems(rSpecialAdBO.advs.get(i).subList(1, rSpecialAdBO.advs.get(i).size()));
            gridView.setTag(i);
            gridView.setOnItemClickListener((parent, view1, position, id) -> {
                rAdBO = rSpecialAdBO.advs.get((int) parent.getTag()).get(position + 1);
                LogUtils.d(TAG, "rAdBO:" + rAdBO);
                if (rAdBO.isCommodity) {//商品广告
                    bundle = new Bundle();
                    bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                    bundle.putInt(getString(R.string.intent_commodityType), 1);
                    openActivity(CommodityInfoActivity.class, bundle);
                } else {//链接
                    bundle = new Bundle();
                    bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                    openActivity(WebViewActivity.class, bundle);
                }
            });

            Glide.with(this).load(rSpecialAdBO.picUrl + (i + 1) + ".png").into(titleImg);

            contentImg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(getActivity()) * 5 / 12));//设置广告栏的宽高比为12:5
            Glide.with(this).load(NetConfig.ImageUrl + rSpecialAdBO.advs.get(i).get(0).advertisementPic).placeholder(R.drawable.replace12b5).priority(Priority.HIGH).into(contentImg);
            LogUtils.d(TAG, "专题广告图片:" + NetConfig.ImageUrl + rSpecialAdBO.advs.get(i).get(0).advertisementPic);
            contentImg.setTag(i);
            contentImg.setOnClickListener(v -> {
                rAdBO = rSpecialAdBO.advs.get((int) contentImg.getTag()).get(0);
                if (rAdBO.isCommodity) {//商品广告
                    bundle = new Bundle();
                    bundle.putInt(getString(R.string.intent_commodityId), rAdBO.commodityId);
                    bundle.putInt(getString(R.string.intent_commodityType), 1);
                    openActivity(CommodityInfoActivity.class, bundle);
                } else {//链接
                    bundle = new Bundle();
                    bundle.putString(getString(R.string.intent_value), rAdBO.connect);
                    openActivity(WebViewActivity.class, bundle);
                }
            });
            specialAdLayout.addView(view);
        }
    }

    /**
     * 限时抢购
     */
    private void initGrab() {
        grabRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        grabAdapter = new MainGrabAdapter(getActivity(), BaseListAdapter.NEITHER);
        grabRecycler.setAdapter(grabAdapter);
        grabRecycler.setTag(GRAB);
        grabAdapter.setOnItemClickListener(this);
    }

    /**
     * 热门推荐
     */
    private void initHot() {
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
        guessRecycler.setLayoutManager(new ScrollGridLayoutManager(getActivity(), 2));
        guessRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());
        guessRecycler.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        listCommodityAdapter = new ListCommodityAdapter(getActivity(), BaseListAdapter.NEITHER, true);
        guessRecycler.setAdapter(listCommodityAdapter);
        guessRecycler.setTag(GUESS);
        listCommodityAdapter.setOnItemClickListener(this);
    }

    private void initCountDownTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tonight = sdf.format(new Date());
        tonight += " 23:59:59";
        timeCountDownUtilBy3View = new TimeCountDownUtilBy3View(MyUtils.DateToTime(tonight), 1000, hourTxt, minuteTxt, secondTxt);
        timeCountDownUtilBy3View.start();
    }

    public void loadSlideSucceed(List<RAdBO> rAdBOs) {
        int len = rAdBOs.size();
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
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_slide), gson.toJson(rAdBOs));
    }

    public void loadSlideFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_slide), "");
        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<RAdBO>>() {
            }.getType();
            List<RAdBO> rAdBOs;
            rAdBOs = new Gson().fromJson(json, type);

            int len = rAdBOs.size();
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
    }

    public void loadTypeSucceed(RTypeBO rTypeBO) {
        gridAdapter.setUrl(rTypeBO.picUrl);
        gridAdapter.setItems(rTypeBO.categotys);
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_type), gson.toJson(rTypeBO));
    }

    public void loadTypeFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_type), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<RTypeBO>() {
            }.getType();
            RTypeBO rTypeBO;
            rTypeBO = new Gson().fromJson(json, type);
            gridAdapter.setUrl(rTypeBO.picUrl);
            gridAdapter.setItems(rTypeBO.categotys);
        }
    }

    public void loadGrabListSucceed(List<RGrabBO> rGrabBOs) {
        grabAdapter.addItems(rGrabBOs);
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_grab), gson.toJson(rGrabBOs));
    }

    public void loadGrabListFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_grab), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<RGrabBO>>() {
            }.getType();
            List<RGrabBO> rGrabBOs;
            rGrabBOs = new Gson().fromJson(json, type);
            grabAdapter.addItems(rGrabBOs);
        }
    }

    public void loadSpecialAdSucceed(RSpecialAdBO rSpecialAdBO) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        addView(rSpecialAdBO);
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_special), gson.toJson(rSpecialAdBO));
    }

    public void loadSpecialAdFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_special), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<RSpecialAdBO>() {
            }.getType();
            RSpecialAdBO rSpecialAdBO;
            rSpecialAdBO = new Gson().fromJson(json, type);
            addView(rSpecialAdBO);
        }
    }

    public void loadHotSucceed(List<RAdBO> rAdBOs) {
        hotAdapter.setItems(rAdBOs);
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_hot), gson.toJson(rAdBOs));
    }

    public void loadHotFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_hot), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<RAdBO>>() {
            }.getType();
            List<RAdBO> rAdBOs;
            rAdBOs = new Gson().fromJson(json, type);
            hotAdapter.setItems(rAdBOs);
        }
    }

    public void loadGuessSucceed(List<RListCommodityBO> rListCommodityBOs) {
        listCommodityAdapter.setItems(rListCommodityBOs);
        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_guess), gson.toJson(rListCommodityBOs));
    }

    public void loadGuessFailed(String msg) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_guess), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<RListCommodityBO>>() {
            }.getType();
            List<RListCommodityBO> rListCommodityBOs;
            rListCommodityBOs = new Gson().fromJson(json, type);
            listCommodityAdapter.setItems(rListCommodityBOs);
        }
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
        bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), ((RCateBO) gridAdapter.getItem(position)).categoryName);
        bundle.putString(getString(R.string.intent_categoryId), ((RCateBO) gridAdapter.getItem(position)).id + "");
        openActivity(ClassifyInfoActivity.class, bundle);
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
            case R.id.recycler_grab:
                rGrabBO = grabAdapter.getItem(position);
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rGrabBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rGrabBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), 2);//抢购商品
                bundle.putInt(getString(R.string.intent_matchId), rGrabBO.id);//matchId
                openActivity(CommodityInfoActivity.class, bundle);
                break;
            case R.id.recycler_hot:
                rAdBO = hotAdapter.getItem(position);
                if (rAdBO.isCommodity) {//商品广告
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
                break;
            case R.id.recycler_guess:
                rListCommodityBO = listCommodityAdapter.getItem(position);
                bundle = new Bundle();
                bundle.putInt(getString(R.string.intent_commodityId), rListCommodityBO.commodityId);
                bundle.putInt(getString(R.string.intent_commodityDetailId), rListCommodityBO.commodityDetailId);
                bundle.putInt(getString(R.string.intent_commodityType), rListCommodityBO.commodityType);
                openActivity(CommodityInfoActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.getGuess(application.getUserId());
        }
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView.getScrollY() > scrollView.getHeight())
            topImg.setVisibility(View.VISIBLE);
        else
            topImg.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setEnabled(scrollView.getScrollY() == 0);
    }

    @Override
    public void onRefresh() {
        mPresenter.getSlide();
        mPresenter.getType();
        mPresenter.getGrabList();
        mPresenter.getSpecialAd();
        mPresenter.getHot();
        mPresenter.getGuess(application.getUserId());
    }
}
