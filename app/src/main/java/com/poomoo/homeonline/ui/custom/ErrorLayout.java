package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    public static final int NO_COLLECTED = 5;
    public static final int NO_ORDER = 6;

    private ProgressBar probar;
    private TextView vText;
    private TextView vLoadFailure;
    private OnActiveClickListener listener;
    private int mState = HIDE;

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

        probar = (ProgressBar) view.findViewById(R.id.progressbar);
        vText = (TextView) view.findViewById(R.id.state_text);
        vLoadFailure = (TextView) view.findViewById(R.id.load_failed);

        vLoadFailure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onLoadActiveClick();
            }
        });
        addView(view);
    }


    public void setState(int state) {
        mState = state;
        switch (state) {
            case HIDE:
                setVisibility(GONE);
                break;

            case LOADING:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(GONE);
                vText.setVisibility(VISIBLE);
                vText.setText("加载中...");
                probar.setVisibility(VISIBLE);
                break;

            case LOAD_FAILED:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("数据加载失败");
                vText.setVisibility(GONE);
                probar.setVisibility(GONE);
                break;

            case NOT_NETWORK:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("请检查网络后再试");
                vText.setVisibility(GONE);
                probar.setVisibility(GONE);
                break;

            case EMPTY_DATA:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("没有数据");
                vText.setVisibility(GONE);
                probar.setVisibility(GONE);
                break;

            case NO_COLLECTED:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("没有收藏记录");
                vText.setVisibility(GONE);
                probar.setVisibility(GONE);
                break;

            case NO_ORDER:
                setVisibility(VISIBLE);
                vLoadFailure.setVisibility(VISIBLE);
                vLoadFailure.setText("您还没有订单哦");
                vText.setVisibility(GONE);
                probar.setVisibility(GONE);
                break;
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
