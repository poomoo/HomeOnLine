/**
 * @Title: PostalService
 * @Package com.dvt.common
 * @author 李苜菲
 * @date 2014年4月28日 上午9:15:18
 * @version V1.0
 */
package com.poomoo.homeonline.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.poomoo.homeonline.R;


/**
 * @author 李苜菲
 * @ClassName MyProgress
 * @Description TODO(有确定和取消按钮的对话框)
 * @date 2014年8月28日 下午4:40:56
 */
public class MyProgress extends ProgressDialog {
    int dialogResult;
    Handler mHandler;
    private Activity context;

    public MyProgress(Activity context) {
        super(context);
        this.context = context;
        dialogResult = 0;
        setOwnerActivity(context);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onCreate();
    }

    public void onCreate() {
        setContentView(R.layout.loading_dialog);
        this.setCanceledOnTouchOutside(false);
    }

    public int showDialog(String Msg) {
        TextView TvErrorInfo = (TextView) findViewById(R.id.tipTextView);
        TvErrorInfo.setText(Msg);
        super.show();

        this.setOnKeyListener((dialog, keyCode, event) -> {
            // TODO 自动生成的方法存根
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
            }
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismiss();
                return true;
            }
            return false;
        });
        return dialogResult;
    }
}
