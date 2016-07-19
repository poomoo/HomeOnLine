package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;


/**
 * @author 李苜菲
 * @ClassName BottomBar
 * @Description 底部导航栏
 * @date 2016/7/19 11:16
 */
public class BottomBar extends LinearLayout implements OnClickListener {
    private static final String TAG = "BottomBar";

    TextView inform_mainTxt;
    TextView inform_classifyTxt;
    TextView inform_grabTxt;
    TextView inform_cartTxt;
    TextView inform_centerTxt;

    private Context context;
    public static OnItemChangedListener onItemChangedListener;
    private RelativeLayout[] mRelativeLayout = new RelativeLayout[5];
    private TextView[] mTextViews = new TextView[5];
    public static BottomBar instance;

    public BottomBar(Context context) {
        super(context);
        this.context = context;
        instance = this;
        init();
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        instance = this;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_bar, null);
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f));

        mTextViews[0] = (TextView) view.findViewById(R.id.nav_txt_main);
        mTextViews[1] = (TextView) view.findViewById(R.id.nav_txt_classify);
        mTextViews[2] = (TextView) view.findViewById(R.id.nav_txt_grab);
        mTextViews[3] = (TextView) view.findViewById(R.id.nav_txt_cart);
        mTextViews[4] = (TextView) view.findViewById(R.id.nav_txt_center);

        inform_mainTxt = (TextView) view.findViewById(R.id.nav_inform_main);
        inform_classifyTxt = (TextView) view.findViewById(R.id.nav_inform_classify);
        inform_grabTxt = (TextView) view.findViewById(R.id.nav_inform_grab);
        inform_cartTxt = (TextView) view.findViewById(R.id.nav_inform_cart);
        inform_centerTxt = (TextView) view.findViewById(R.id.nav_inform_center);

        mRelativeLayout[0] = (RelativeLayout) view.findViewById(R.id.rlayout_main);
        mRelativeLayout[1] = (RelativeLayout) view.findViewById(R.id.rlayout_classify);
        mRelativeLayout[2] = (RelativeLayout) view.findViewById(R.id.rlayout_grab);
        mRelativeLayout[3] = (RelativeLayout) view.findViewById(R.id.rlayout_cart);
        mRelativeLayout[4] = (RelativeLayout) view.findViewById(R.id.rlayout_center);

        for (RelativeLayout linear : mRelativeLayout) {
            linear.setOnClickListener(this);
        }
        addView(view);

        mTextViews[0].setSelected(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlayout_main:
                onItemChangedListener.onItemChanged(0);
                cancelLinearBackground(0);
                break;
            case R.id.rlayout_classify:
                onItemChangedListener.onItemChanged(1);
                cancelLinearBackground(1);
                break;
            case R.id.rlayout_grab:
                onItemChangedListener.onItemChanged(2);
                cancelLinearBackground(2);
                break;
            case R.id.rlayout_cart:
                onItemChangedListener.onItemChanged(3);
                cancelLinearBackground(3);
                break;
            case R.id.rlayout_center:
                onItemChangedListener.onItemChanged(4);
                cancelLinearBackground(4);
                break;
        }
    }

    public void cancelLinearBackground(int number) {
        mTextViews[0].setSelected(false);
        mTextViews[1].setSelected(false);
        mTextViews[2].setSelected(false);
        mTextViews[3].setSelected(false);
        mTextViews[4].setSelected(false);

        mTextViews[number].setSelected(true);
    }

    public interface OnItemChangedListener {
        public void onItemChanged(int index);
    }

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    /**
     * 显示通知
     *
     * @param type
     * @param number 通知数量
     * @param isShow 是否显示
     */
    public void setInfoNum(int type, int number, boolean isShow) {
        switch (type) {
            case 0:
                if (isShow) {
                    inform_mainTxt.setVisibility(View.VISIBLE);
                    inform_mainTxt.setText(number + "");
                } else
                    inform_mainTxt.setVisibility(View.INVISIBLE);
                break;
            case 1:
                if (isShow) {
                    inform_classifyTxt.setVisibility(View.VISIBLE);
                    inform_classifyTxt.setText(number + "");
                } else
                    inform_mainTxt.setVisibility(View.INVISIBLE);
                break;
            case 2:
                if (isShow) {
                    inform_grabTxt.setVisibility(View.VISIBLE);
                    inform_grabTxt.setText(number + "");
                } else
                    inform_grabTxt.setVisibility(View.INVISIBLE);
                break;
            case 3:
                if (isShow) {
                    inform_cartTxt.setVisibility(View.VISIBLE);
                    inform_cartTxt.setText(number + "");
                    LogUtils.d(TAG, "购物车显示数量" + number + "isShow" + inform_cartTxt.getVisibility());
                } else
                    inform_cartTxt.setVisibility(View.INVISIBLE);
                break;
            case 4:
                if (isShow) {
                    inform_centerTxt.setVisibility(View.VISIBLE);
                    inform_centerTxt.setText(number + "");
                } else
                    inform_centerTxt.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
