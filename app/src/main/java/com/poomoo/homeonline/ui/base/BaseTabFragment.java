package com.poomoo.homeonline.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 类名 BaseTabFragment
 * 描述 带TAB的Fragment基类
 * 作者 李苜菲
 * 日期 2016/7/19 11:35
 */
public abstract class BaseTabFragment extends BaseFragment {
    protected ViewPager mViewPager;
    protected FragmentStatePagerAdapter mAdapter;
    protected ArrayList<ViewPageInfo> mTabs;
    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    public Context mContext;

    private int POSITION = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        if (mAdapter == null) {
            mTabs = new ArrayList<>();
            onSetupTabs();

            mAdapter = new FragmentStatePagerAdapter(getGenuineFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return mTabs.get(position).fragment;
                }

                @Override
                public int getCount() {
                    return mTabs.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return mTabs.get(position).tag;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    if (position == POSITION)
                        super.destroyItem(container, position, object);
                }

                @Override
                public int getItemPosition(Object object) {
                    return POSITION_NONE;
                }
            };
            mViewPager.setAdapter(mAdapter);
        } else {
            mViewPager.setAdapter(mAdapter);
        }
    }

    /**
     * 导航元素
     *
     * @param title
     * @return
     */
    public abstract View setupTabItemView(String title);

    /**
     * 设置Fragment
     */
    public abstract void onSetupTabs();

    public FragmentManager getGenuineFragmentManager() {
        return getFragmentManager();
    }

    /**
     * 添加Fragment对象到ViewPager
     */
    public void addTab(String tag, Class<? extends Fragment> fragment, int catalog) {
        LogUtils.d(TAG, "addTab:" + catalog);
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, catalog);
        mTabs.add(new ViewPageInfo(tag, instantiate(getActivity(), fragment.getName(), bundle)));
    }

    public void setPOSITION(int POSITION) {
        this.POSITION = POSITION;
    }
//    /**
//     * 添加Fragment对象到ViewPager
//     */
//    public void addTab(String tag, Class<? extends Fragment> fragment, String content) {
//        Bundle bundle = new Bundle();
//        bundle.putString(getString(R.string.intent_value), content);
//        mTabs.add(new ViewPageInfo(tag, instantiate(getActivity(), fragment.getName(), bundle)));
//    }

    /**
     * 添加Fragment对象到ViewPager
     */
    public void addTab(String tag, Class<? extends Fragment> fragment, Object o) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_value), (Serializable) o);
        mTabs.add(new ViewPageInfo(tag, instantiate(getActivity(), fragment.getName(), bundle)));
    }

    public void addTab(String tag, Class<? extends Fragment> fragment) {
        mTabs.add(new ViewPageInfo(tag, instantiate(getActivity(), fragment.getName())));
    }

    public void addTab(String tag, Fragment fragment) {
        mTabs.add(new ViewPageInfo(tag, fragment));
    }

    public void setCurrentItem(int index) {
        mViewPager.setCurrentItem(index);
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    public int getPageCount() {
        return mTabs.size();
    }

    /**
     * ViewPageInformation
     */
    public static class ViewPageInfo {
        public String tag;
        public View view;
        public Fragment fragment;

        public ViewPageInfo(String tag, Fragment fragment) {
            this.tag = tag;
            this.fragment = fragment;
        }
    }
}
