package com.poomoo.homeonline.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.core.AppAction;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.application.MyApplication;

/**
 * @author 李苜菲
 * @ClassName BaseActivity
 * @Description 基类Activity
 * @date 2016/7/19 11:15
 */
public abstract class BaseActivity extends FragmentActivity {
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public MyApplication application;
    // 核心层的Action实例
    public AppAction appAction;
    //日志标签
    public String TAG = getClass().getSimpleName();
    //进度对话框
    public ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application = (MyApplication) this.getApplication();
        appAction = application.getAppAction();
        // 去掉默认标题栏
//        Window window = getWindow();
//        window.requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(onBindLayout());
    }

    protected abstract int onBindLayout();

    protected abstract int onSetTitle();

    protected void setBack() {
        getHeaderView();
    }

    /**
     * 统一头部条
     *
     * @return lHeaderViewHolder 头部条对象
     */
    protected HeaderViewHolder getHeaderView() {
        HeaderViewHolder headerViewHolder = new HeaderViewHolder();
        headerViewHolder.titleTxt = (TextView) findViewById(R.id.txt_titleBar_name);
        headerViewHolder.backImg = (ImageView) findViewById(R.id.img_titleBar_back);
        headerViewHolder.rightImg = (ImageView) findViewById(R.id.img_titleBar_right);
        headerViewHolder.rightTxt = (TextView) findViewById(R.id.txt_titleBar_right);
        headerViewHolder.titleTxt.setText(getString(onSetTitle()));
        headerViewHolder.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                getActivityOutToRight();
            }
        });
        return headerViewHolder;
    }

    protected class HeaderViewHolder {
        public TextView titleTxt;//标题
        public TextView rightTxt;//右边标题
        public ImageView backImg;//返回键
        public ImageView rightImg;//右边图标
    }

    /**
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        this.startActivity(intent);
        getActivityInFromRight();
    }

    /**
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        this.startActivity(intent);
        getActivityInFromRight();
    }

    /**
     * @param pClass
     * @param requestCode
     */
    protected void openActivityForResult(Class<?> pClass, int requestCode) {
        Intent intent = new Intent(this, pClass);
        startActivityForResult(intent, requestCode);
        getActivityInFromRight();
    }

    /**
     * @param pClass
     * @param requestCode
     */
    protected void openActivityForResult(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
        getActivityInFromRight();
    }

    /**
     * 显示进度对话框
     *
     * @param msg
     */
    protected void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
//        progressDialog.setOnKeyListener(new OnKeyListener() {
//
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                // TODO Auto-generated method stub
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                    Thread thread = HttpUtil.thread;
//                    HttpUtil.thread = null;
//                    thread.interrupt();
//                    finish();
//                }
//                return false;
//            }
//        });
    }

    /**
     * 关闭对话框
     */
    protected void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 系统状态栏高度
     *
     * @return
     */
    protected int getBarHeight() {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * activity切换-> 向左进(覆盖)
     */
    protected void getActivityInFromRight() {
        overridePendingTransition(R.anim.activity_in_from_right,
                R.anim.activity_center);
    }


    /**
     * activity切换-> 向右出(抽出)
     */
    protected void getActivityOutToRight() {
        overridePendingTransition(R.anim.activity_center,
                R.anim.activity_out_to_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            getActivityOutToRight();
        }
        return true;
    }
}
