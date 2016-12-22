/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ClassifyListAdapter;
import com.poomoo.homeonline.adapter.SubClassifyListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.homeonline.presenters.ClassifyFragmentPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.ClassifyListActivity;
import com.poomoo.homeonline.ui.activity.SearchActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RSubClassifyBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名 ClassifyFragment
 * 描述 分类
 * 作者 李苜菲
 * 日期 2016/7/19 11:20
 */
public class ClassifyFragment extends BaseDaggerFragment<ClassifyFragmentPresenter> implements BaseListAdapter.OnItemClickListener, ClassifyOnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.recycler_classify)
    RecyclerView classifyRecycler;
    @Bind(R.id.recycler_content)
    RecyclerView contentRecycler;
    @Bind(R.id.llayout_content)
    LinearLayout layout;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private ClassifyListAdapter classifyListAdapter;
    private SubClassifyListAdapter subClassifyListAdapter;
    private List<RSubClassifyBO> rSubClassifyBOs = new ArrayList<>();
    private List<RSubClassifyBO> rSubClassifyBOs2 = new ArrayList<>();
    private List<RClassifyBO> rClassifyBOs = new ArrayList<>();

    public static int SELECTPOSITION = 0;
    private String json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
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
        initClassify();
        initSubClassify();

        //取缓存
        json = (String) SPUtils.get(getActivity().getApplicationContext(), getString(R.string.sp_classify), "");

        if (!TextUtils.isEmpty(json)) {
            layout.setVisibility(View.VISIBLE);
            Type type = new TypeToken<List<RClassifyBO>>() {
            }.getType();
            rClassifyBOs = new Gson().fromJson(json, type);
            classifyListAdapter.setItems(rClassifyBOs);
            rSubClassifyBOs = rClassifyBOs.get(0).childrenList;
            for (RSubClassifyBO rSubClassifyBO : rSubClassifyBOs) {
                rSubClassifyBOs2.add(rSubClassifyBO);
                rSubClassifyBOs2.add(rSubClassifyBO);
            }
            subClassifyListAdapter.setItems(rSubClassifyBOs2);
        } else
            errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getClassify();
        errorLayout.setOnActiveClickListener(this);
    }

    private void initClassify() {
        classifyRecycler.setLayoutParams(new LinearLayout.LayoutParams(MyUtils.getScreenWidth(getActivity()) * 1 / 4, LinearLayout.LayoutParams.MATCH_PARENT));
        classifyRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classifyRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(ContextCompat.getColor(getActivity(), R.color.ThemeBg))
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
        LogUtils.d(TAG, "contentRecycler width:" + contentRecycler.getWidth() + " " + contentRecycler.getMeasuredWidth());
    }

    public void loadClassifySucceed(List<RClassifyBO> rClassifyBOs) {
        LogUtils.d(TAG, "rClassifyBOs" + rClassifyBOs.get(0).childrenList.get(0).categoryPic);
        errorLayout.setState(ErrorLayout.HIDE, "");
        layout.setVisibility(View.VISIBLE);
        classifyListAdapter.setItems(rClassifyBOs);
        this.rClassifyBOs = rClassifyBOs;
        this.rSubClassifyBOs = rClassifyBOs.get(SELECTPOSITION).childrenList;
        rSubClassifyBOs2 = new ArrayList<>();
        for (RSubClassifyBO rSubClassifyBO : rSubClassifyBOs) {
            rSubClassifyBOs2.add(rSubClassifyBO);
            rSubClassifyBOs2.add(rSubClassifyBO);
        }
        subClassifyListAdapter.setItems(this.rSubClassifyBOs2);

        SPUtils.put(getActivity().getApplicationContext(), getString(R.string.sp_classify), new Gson().toJson(rClassifyBOs));
    }

    public void loadClassifyFailed(String msg) {
        if (TextUtils.isEmpty(json))
            errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        rSubClassifyBOs = rClassifyBOs.get(position).childrenList;
        SELECTPOSITION = position;
        classifyListAdapter.notifyDataSetChanged();
        rSubClassifyBOs2 = new ArrayList<>();
        for (RSubClassifyBO rSubClassifyBO : rSubClassifyBOs) {
            rSubClassifyBOs2.add(rSubClassifyBO);
            rSubClassifyBOs2.add(rSubClassifyBO);
        }
        subClassifyListAdapter.setItems(rSubClassifyBOs2);
    }

    @Override
    public void onClick(String categoryId, boolean isAbroad) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_categoryId), categoryId);
        bundle.putBoolean(getString(R.string.intent_isAbroad), isAbroad);
        openActivity(ClassifyListActivity.class, bundle);
    }

    @OnClick({R.id.llayout_search})
    void search(View view) {
        switch (view.getId()) {
            case R.id.llayout_search:
                openActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onLoadActiveClick() {
        errorLayout.setState(ErrorLayout.LOADING, "");
        mPresenter.getClassify();
    }
}
