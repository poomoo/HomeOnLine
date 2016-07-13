package com.poomoo.homeonline.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author ysy
 * @Function 底部选项封装
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
    private LinearLayout[] mLinearLayout = new LinearLayout[5];
    private ImageView[] mImageView = new ImageView[5];
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
        mImageView[0] = (ImageView) view.findViewById(R.id.nav_img_main);
        mImageView[1] = (ImageView) view.findViewById(R.id.nav_img_classify);
        mImageView[2] = (ImageView) view.findViewById(R.id.nav_img_grab);
        mImageView[3] = (ImageView) view.findViewById(R.id.nav_img_cart);
        mImageView[4] = (ImageView) view.findViewById(R.id.nav_img_center);

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

        mLinearLayout[0] = (LinearLayout) view.findViewById(R.id.layout_main);
        mLinearLayout[1] = (LinearLayout) view.findViewById(R.id.layout_classify);
        mLinearLayout[2] = (LinearLayout) view.findViewById(R.id.layout_grab);
        mLinearLayout[3] = (LinearLayout) view.findViewById(R.id.layout_cart);
        mLinearLayout[4] = (LinearLayout) view.findViewById(R.id.layout_center);

        for (LinearLayout linear : mLinearLayout) {
            linear.setOnClickListener(this);
        }
        addView(view);
        mImageView[0].setImageResource(R.drawable.nav1_checked);
        mTextViews[0].setTextColor(getResources().getColor(R.color.ThemeRed));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_main:
                onItemChangedListener.onItemChanged(0);
                cancelLinearBackground(0);
                break;
            case R.id.layout_classify:
                onItemChangedListener.onItemChanged(1);
                cancelLinearBackground(1);
                break;
            case R.id.layout_grab:
                onItemChangedListener.onItemChanged(2);
                cancelLinearBackground(2);
                break;
            case R.id.layout_cart:
                onItemChangedListener.onItemChanged(3);
                cancelLinearBackground(3);
                break;
            case R.id.layout_center:
                onItemChangedListener.onItemChanged(4);
                cancelLinearBackground(4);
                break;
        }
    }

    public void cancelLinearBackground(int number) {
        mImageView[0].setImageResource(R.drawable.nav1_normal);
        mImageView[1].setImageResource(R.drawable.nav2_normal);
        mImageView[2].setImageResource(R.drawable.nav3_normal);
        mImageView[3].setImageResource(R.drawable.nav4_normal);
        mImageView[4].setImageResource(R.drawable.nav5_normal);

        mTextViews[0].setTextColor(getResources().getColor(R.color.black));
        mTextViews[1].setTextColor(getResources().getColor(R.color.black));
        mTextViews[2].setTextColor(getResources().getColor(R.color.black));
        mTextViews[3].setTextColor(getResources().getColor(R.color.black));
        mTextViews[4].setTextColor(getResources().getColor(R.color.black));

        switch (number) {
            case 0:
                mImageView[number].setImageResource(R.drawable.nav1_checked);
                mTextViews[number].setTextColor(getResources().getColor(R.color.ThemeRed));
                break;
            case 1:
                mImageView[number].setImageResource(R.drawable.nav2_checked);
                mTextViews[number].setTextColor(getResources().getColor(R.color.ThemeRed));
                break;
            case 2:
                mImageView[number].setImageResource(R.drawable.nav3_checked);
                mTextViews[number].setTextColor(getResources().getColor(R.color.ThemeRed));
                break;
            case 3:
                mImageView[number].setImageResource(R.drawable.nav4_checked);
                mTextViews[number].setTextColor(getResources().getColor(R.color.ThemeRed));
                break;
            case 4:
                mImageView[number].setImageResource(R.drawable.nav5_checked);
                mTextViews[number].setTextColor(getResources().getColor(R.color.ThemeRed));
                break;
        }
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
