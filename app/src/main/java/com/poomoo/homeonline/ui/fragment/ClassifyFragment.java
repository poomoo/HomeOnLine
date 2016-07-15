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
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ClassifyListAdapter;
import com.poomoo.homeonline.adapter.SubClassifyGridAdapter;
import com.poomoo.homeonline.adapter.SubClassifyListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.homeonline.ui.activity.JSAndroidActivity;
import com.poomoo.homeonline.ui.activity.WebViewActivity;
import com.poomoo.homeonline.ui.base.BaseFragment;
import com.poomoo.model.response.RClassifyBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 分类
 * 作者: 李苜菲
 * 日期: 2016/6/28 14:11.
 */
public class ClassifyFragment extends BaseFragment implements BaseListAdapter.OnItemClickListener, ClassifyOnItemClickListener {
    @Bind(R.id.recycler_classify)
    RecyclerView classifyRecycler;
    @Bind(R.id.recycler_content)
    RecyclerView contentRecycler;

    private ClassifyListAdapter classifyListAdapter;
    private SubClassifyListAdapter subClassifyListAdapter;
    private List<String> classify = new ArrayList<>();
    private List<RClassifyBO> rClassifyBOs = new ArrayList<>();

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

    private void initView() {
        initClassify();
        initSubClassify(MyConfig.gzwdjson);
    }

    private void initClassify() {
        classifyRecycler.setLayoutParams(new LinearLayout.LayoutParams(MyUtils.getScreenWidth(getActivity()) / 3, LinearLayout.LayoutParams.MATCH_PARENT));
        classifyRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        classifyRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.ThemeBg))
                .size((int) getResources().getDimension(R.dimen.divider_height2))
                .build());
        int len = MyConfig.classify.length;
        for (int i = 0; i < len; i++)
            classify.add(MyConfig.classify[i]);
        classifyListAdapter = new ClassifyListAdapter(getActivity(), BaseListAdapter.NEITHER, len);
        classifyRecycler.setAdapter(classifyListAdapter);
        classifyListAdapter.addItems(classify);
        classifyListAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long id, View view) {
                classifyListAdapter.getBooleanHashMap().put(position, true);
                classifyListAdapter.notifyDataSetChanged();

                String json = MyConfig.gzwdjson;
                switch (position) {
                    case 0:
                        json = MyConfig.gzwdjson;
                        break;
                    case 1:
                        json = MyConfig.gjgcJson;
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }
                initSubClassify(json);
            }
        });
    }

    private void initSubClassify(String json) {
        contentRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Type type = new TypeToken<List<RClassifyBO>>() {
        }.getType();
        rClassifyBOs = new Gson().fromJson(json, type);
        LogUtils.d(TAG, "initSubClassify:" + rClassifyBOs.toString());
        subClassifyListAdapter = new SubClassifyListAdapter(getActivity(), BaseListAdapter.NEITHER, this);
        contentRecycler.setAdapter(subClassifyListAdapter);
        subClassifyListAdapter.setItems(rClassifyBOs);
        subClassifyListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, long id, View view) {

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
