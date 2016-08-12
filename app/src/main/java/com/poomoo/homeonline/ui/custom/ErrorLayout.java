package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;

/**
 * 类名 ErrorLayout
 * 描述 错误View
 * 作者 李苜菲
 * 日期 2016/7/19 11:36
 */
public class ErrorLayout extends FrameLayout {

    public static final int HIDE = 0;
    public static final int LOADING = 1;
    public static final int LOAD_FAILED = 2;
    public static final int NOT_NETWORK = 3;
    public static final int EMPTY_DATA = 4;
    private final String TAG = getClass().getSimpleName();

    //    private ProgressBar probar;
    private RelativeLayout progressLayout;
    private TextView vText;
    private TextView vLoadFailure;
    private OnActiveClickListener listener;
    private int mState = HIDE;
    private Drawable drawable;

    public ErrorLayout(Context context) {
        super(context);
        initView();
    }

    public ErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_error_layout, null);

        progressLayout = (RelativeLayout) view.findViewById(R.id.rlayout_progressBar);
        vText = (TextView) view.findViewById(R.id.state_text);
        vLoadFailure = (TextView) view.findViewById(R.id.load_failed);

        vLoadFailure.setOnClickListener(v -> {
            if (listener != null)
                listener.onLoadActiveClick();
        });
        addView(view);
    }


    public void setState(int state, String emptyMsg) {
        LogUtils.d(TAG, "setState:" + state + " emptyMsg:" + emptyMsg);

        mState = state;
        switch (state) {
            case HIDE:
                setVisibility(GONE);
                break;

            case LOADING:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(GONE);
                vText.setVisibility(GONE);
                vText.setText("加载中...");
                progressLayout.setVisibility(VISIBLE);
                break;

            case LOAD_FAILED:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("数据加载失败,点击重新加载");
                vLoadFailure.setCompoundDrawables(null, null, null, null);
//                vLoadFailure.setText("失败");
                vText.setVisibility(GONE);
                progressLayout.setVisibility(GONE);
                break;

            case NOT_NETWORK:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                drawable = getResources().getDrawable(R.drawable.ic_network_invalid);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (drawable.getMinimumHeight()));
                vLoadFailure.setCompoundDrawables(null, drawable, null, null);
                vLoadFailure.setText("网络请求失败" + "\n\n" + "请检查您的网络" + "\n" + "点击刷新");
                vText.setVisibility(GONE);
                progressLayout.setVisibility(GONE);
                break;

            case EMPTY_DATA:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(GONE);
                vText.setVisibility(VISIBLE);
                vText.setText(emptyMsg);
                progressLayout.setVisibility(GONE);
                break;

//            case NO_COLLECTED:
//                setVisibility(VISIBLE);
//                vLoadFailure.setVisibility(VISIBLE);
//                vLoadFailure.setText("没有收藏记录");
//                vText.setVisibility(GONE);
//                progressLayout.setVisibility(GONE);
//                break;
//
//            case NO_ORDER:
//                setVisibility(VISIBLE);
//                vLoadFailure.setVisibility(VISIBLE);
//                vLoadFailure.setText("您还没有订单哦");
//                vText.setVisibility(GONE);
//                progressLayout.setVisibility(GONE);
//                break;
//
//            case CUSTOM:
//                setVisibility(VISIBLE);
//                vLoadFailure.setVisibility(VISIBLE);
//                vLoadFailure.setText(customMsg);
//                vText.setVisibility(GONE);
//                progressLayout.setVisibility(GONE);
//                break;
        }
    }

    public int getState() {
        return mState;
    }

    public void setOnActiveClickListener(OnActiveClickListener listener) {
        this.listener = listener;
    }

    /**
     * 点击加载失败的图片重新加载
     */
    public interface OnActiveClickListener {
        void onLoadActiveClick();
    }
}
