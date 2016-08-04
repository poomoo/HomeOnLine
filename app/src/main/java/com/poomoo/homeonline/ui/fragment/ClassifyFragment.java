/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ClassifyListAdapter;
import com.poomoo.homeonline.adapter.SubClassifyGridAdapter;
import com.poomoo.homeonline.adapter.SubClassifyListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.homeonline.presenters.ClassifyFragmentPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.JSAndroidActivity;
import com.poomoo.homeonline.ui.activity.WebViewActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RSubClassifyBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ClassifyFragment
 * 描述 分类
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class ClassifyFragment extends BaseDaggerFragment<ClassifyFragmentPresenter> implements BaseListAdapter.OnItemClickListener, ClassifyOnItemClickListener {
    @Bind(R.id.recycler_classify)
    RecyclerView classifyRecycler;
    @Bind(R.id.recycler_content)
    RecyclerView contentRecycler;

    private ClassifyListAdapter classifyListAdapter;
    private SubClassifyListAdapter subClassifyListAdapter;
    private List<RSubClassifyBO> rSubClassifyBOs = new ArrayList<>();
    private List<RClassifyBO> rClassifyBOs = new ArrayList<>();

    public static int SELECTPOSITION = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
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
        mPresenter.getClassify();

        initClassify();
        initSubClassify();

        //取缓存
        String json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_classify), "");

        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<RClassifyBO>>() {
            }.getType();
            rClassifyBOs = new Gson().fromJson(json, type);
            classifyListAdapter.setItems(rClassifyBOs);
            rSubClassifyBOs = rClassifyBOs.get(0).childrenList;
            subClassifyListAdapter.setItems(rSubClassifyBOs);
        }
    }

    private void initClassify() {
        classifyRecycler.setLayoutParams(new LinearLayout.LayoutParams(MyUtils.getScreenWidth(getActivity()) / 3, LinearLayout.LayoutParams.MATCH_PARENT));
        classifyRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classifyRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.ThemeBg))
                .size((int) getResources().getDimension(R.dimen.divider_height2))
                .build());

        classifyListAdapter = new ClassifyListAdapter(getActivity(), BaseListAdapter.NEITHER);
        classifyRecycler.setAdapter(classifyListAdapter);
        classifyListAdapter.setOnItemClickListener(this);
    }

    private void initSubClassify() {
        contentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        subClassifyListAdapter = new SubClassifyListAdapter(getActivity(), BaseListAdapter.NEITHER, this);
        contentRecycler.setAdapter(subClassifyListAdapter);
    }

    public void loadClassifySucceed(List<RClassifyBO> rClassifyBOs) {
        LogUtils.d(TAG, "loadClassifySucceed:" + rClassifyBOs);
        classifyListAdapter.setItems(rClassifyBOs);
        this.rClassifyBOs = rClassifyBOs;
        this.rSubClassifyBOs = rClassifyBOs.get(0).childrenList;
        subClassifyListAdapter.setItems(this.rSubClassifyBOs);

        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_classify), new Gson().toJson(rClassifyBOs));
    }

    public void loadClassifyFailed(String msg) {
        MyUtils.showToast(getActivity().getApplicationContext(), msg);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rSubClassifyBOs = rClassifyBOs.get(position).childrenList;
        SELECTPOSITION = position;
        classifyListAdapter.notifyDataSetChanged();
        subClassifyListAdapter.setItems(rSubClassifyBOs);
    }

    @Override
    public void onClick(String categoryId) {
        MyUtils.showToast(getActivity().getApplicationContext(), "点击了" + categoryId);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), "http://www.jiayou9.com/phone/commodity/three_list.html?categoryId=1777");
        openActivity(WebViewActivity.class, bundle);
//        openActivity(WebViewActivity.class);
    }
}
