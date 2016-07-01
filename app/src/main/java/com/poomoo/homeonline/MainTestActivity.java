package com.poomoo.homeonline;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.homeonline.ui.listener.AdvertisementListener;

public class MainTestActivity extends AppCompatActivity {
    private SlideShowView slideShowView;

    private String[] urls = new String[3];
    private RelativeLayout relativeLayout;
    private AppBarLayout appBarLayout;
    private RelativeLayout bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_maintest);
//        StatusBarUtil.setTransparent(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_title);
        relativeLayout.getBackground().setAlpha(0);

        slideShowView = (SlideShowView) findViewById(R.id.flipper_ad1);
        slideShowView.setLayoutParams(new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, getScreenWidth(this) * 2 / 3));//设置广告栏的宽高比为3:1
        urls[0] = "http://hunchaowang.com/hckj/images/slide1.jpg";
        urls[1] = "http://img5.imgtn.bdimg.com/it/u=1831523257,4273085642&fm=21&gp=0.jpg";
        urls[2] = "http://img0.imgtn.bdimg.com/it/u=2724261082,1059352100&fm=21&gp=0.jpg";
        slideShowView.setPics(urls, new AdvertisementListener() {

            @Override
            public void onResult(int position) {

            }
        });
        bar = (RelativeLayout) findViewById(R.id.rl_title);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    bar.getBackground().setAlpha(0);
                } else {
                    bar.getBackground().setAlpha((int) (0.5 * 255));
                }
            }
        });
    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
