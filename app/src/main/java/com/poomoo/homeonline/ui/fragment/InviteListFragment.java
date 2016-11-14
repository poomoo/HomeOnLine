package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.poomoo.homeonline.adapter.InviteListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.presenters.MyInvitePresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.base.BaseDaggerListFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RInviteBO;

import java.util.List;

/**
 * 类名 InviteListFragment
 * 描述 我的邀请
 * 作者 李苜菲
 * 日期 2016/7/19 11:19
 */
public class InviteListFragment extends BaseDaggerListFragment<RInviteBO, MyInvitePresenter> {
    private InviteListAdapter adapter;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected BaseListAdapter<RInviteBO> onSetupAdapter() {
        adapter = new InviteListAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER);
        return adapter;
    }

    @Override
    protected void setupFragmentComponent(FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        setEmptyMsg("您还没有邀请到小伙伴");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout.setEnabled(false);
        successful();
    }

    public void succeed(List<RInviteBO> rInviteBOs) {
        onLoadFinishState(action);
        onLoadResultData(rInviteBOs);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg)) {
            mAdapter.clear();
            onNetworkInvalid(action);
        } else
            onLoadErrorState(action);
    }

    @Override
    public void onLoading() {
        super.onLoading();
    }

    /**
     * 再错误页面点击重新加载
     */
    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
    }

    /**
     * 操作成功
     */
    public void successful() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mCurrentPage = 1;
        mAdapter.clear();
    }

}
