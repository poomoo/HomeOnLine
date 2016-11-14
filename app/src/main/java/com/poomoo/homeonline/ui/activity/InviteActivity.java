/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ZoneAdapter;
import com.poomoo.homeonline.presenters.InvitePresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.response.RReceiptBO;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 InviteActivity
 * 描述 邀请奖励
 * 作者 李苜菲
 * 日期 2016/7/19 11:21
 */
public class InviteActivity extends BaseDaggerActivity<InvitePresenter> {
    @Bind(R.id.txt_invite_code)
    TextView codeTxt;
    @Bind(R.id.txt_copy)
    TextView copyTxt;
    @Bind(R.id.txt_invited_num)
    TextView numTxt;
    @Bind(R.id.txt_total_price)
    TextView priceTxt;


    private String code;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_invite;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    /**
     * 新建
     *
     * @param view
     */
    public void toDo(View view) {
        openActivity(RewardInfoActivity.class);
    }

    public void onCopy(View view) {
        code = "N1478591";
        myClip = ClipData.newPlainText("text", code);
        myClipboard.setPrimaryClip(myClip);
        MyUtils.showToast(getApplicationContext(), "邀请码已经复制到粘贴板");
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(InviteActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InviteActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InviteActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InviteActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public void share(View view) {
        new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy", "info_icon_1", "info_icon_1")
                .setShareboardclickCallback((snsPlatform, share_media) -> {
                    if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                        MyUtils.showToast(this, "自定义分享内容");
                    } else {
                        new ShareAction(InviteActivity.this).withText("来自友盟自定义分享面板")
                                .setPlatform(share_media)
                                .setCallback(umShareListener)
                                .share();
                    }
                }).open();
    }

    public void failed(String msg) {
    }
}
