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

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.StatusBarUtil;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.listeners.AdvertisementListener;
import com.poomoo.homeonline.listeners.ScrollViewListener;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.MyScrollView;
import com.poomoo.homeonline.ui.custom.PinchImageView;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.myflayout.FlowLayout;
import com.poomoo.myflayout.TagAdapter;
import com.poomoo.myflayout.TagFlowLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CommodityInfoActivity
 * 描述 商品详情
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class CommodityInfoActivity extends BaseActivity implements ScrollViewListener {
    @Bind(R.id.rlayout_info_title)
    RelativeLayout titleBar;
    @Bind(R.id.myScrollView)
    MyScrollView myScrollView;
    @Bind(R.id.slide_commodity_info)
    SlideShowView slideShowView;
    //    @Bind(R.id.img_info_back)
//    ImageView backImg;
    @Bind(R.id.img_info_collect)
    ImageView collectImg;
    @Bind(R.id.txt_info_name)
    TextView nameTxt;
    @Bind(R.id.txt_info_price)
    TextView priceTxt;
    @Bind(R.id.txt_info_old_price)
    TextView oldPriceTxt;
    @Bind(R.id.txt_inventory)
    TextView inventoryTxt;
    @Bind(R.id.txt_commodity_selected)
    TextView selectedTxt;
    @Bind(R.id.edt_info_count)
    EditText countEdt;
    @Bind(R.id.llayout_commodity_count)
    LinearLayout countLayout;
    @Bind(R.id.llayout_commodity_specification)
    LinearLayout specificationLayout;
    @Bind(R.id.txt_cart_num)
    TextView cartNumTxt;

    private LayoutInflater mInflater;

    private int screenWidth = 0;
    private String[] pics = new String[]{"http://img.jiayou9.com/jyzx//upload/company/20160402/1459581133163.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581133960.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581136663.jpg", "http://img.jiayou9.com/jyzx//upload/company/20160402/1459581137663.jpg"};
    private boolean hasSpecification = false;//是否有规格参数
    private DialogPlus contentDialog = null;
    private ImageView dialogBigImg;
    private EditText dialog_product_sum;
    private TextView dialog_oldPriceTxt;
    private TextView confirmTxt;
    private LinearLayout dialog_specificationLayout;
    private LinearLayout bottomLayout;
    private BigPicPopUpWindow popUpWindow;
    private List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();
    private String select = "";
    private String temp[] = {"颜色颜色颜色颜色", "大小", "尺寸", "类型"};
    private boolean isAllSelected = false;//是否选择了所有的属性

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        screenWidth = MyUtils.getScreenWidth(this);
        hasSpecification = getIntent().getBooleanExtra(getString(R.string.intent_value), false);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_commodity_info;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        StatusBarUtil.setTransparent(this);//R.color.black
        titleBar.getBackground().mutate().setAlpha(0);

        slideShowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenWidth));
        slideShowView.setPics(pics, new AdvertisementListener() {
            @Override
            public void onAdvClick(int position) {

            }
        });

        myScrollView.setScrollViewListener(this);

        oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (hasSpecification) {
            countLayout.setVisibility(View.GONE);
            specificationLayout.setVisibility(View.VISIBLE);
        } else {
            countLayout.setVisibility(View.VISIBLE);
            specificationLayout.setVisibility(View.GONE);
        }
        if (application.getCartNum() == 0)
            cartNumTxt.setVisibility(View.INVISIBLE);
        else {
            cartNumTxt.setVisibility(View.VISIBLE);
            cartNumTxt.setText(application.getCartNum() + "");
        }

        mInflater = LayoutInflater.from(this);

        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(1);
        integers.add(2);
        setSelectedInfo(integers);
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
        getActivityOutToRight();
    }

    /**
     * 选择商品属性
     *
     * @param view
     */
    public void selectSpecification(View view) {
        createDialog(true);
    }

    /**
     * 添加到购物车
     *
     * @param view
     */
    public void addToCart(View view) {
        createDialog(false);
    }

    /**
     * 直接购买
     *
     * @param view
     */
    public void buy(View view) {
        createDialog(false);
    }

    /**
     * 增加商品数量
     *
     * @param view
     */
    public void add(View view) {

    }

    /**
     * 减少商品数量
     *
     * @param view
     */
    public void minus(View view) {

    }

    /**
     * 创建商品属性选择Dialog
     *
     * @param showBottom
     */
    private void createDialog(boolean showBottom) {
        if (contentDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.product_detail_dialog_content, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenHeight(this) * 3 / 4));
            dialog_oldPriceTxt = (TextView) view.findViewById(R.id.txt_dialog_oldPrice);
            dialog_specificationLayout = ((LinearLayout) view.findViewById(R.id.llayout_dialog_specification));
            dialog_product_sum = ((EditText) view.findViewById(R.id.edt_commodity_specification_count));
            confirmTxt = ((TextView) view.findViewById(R.id.txt_dialog_ok));
            bottomLayout = ((LinearLayout) view.findViewById(R.id.llayout_dialog_bottom));

            dialog_oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            addView();

            Holder holder = new ViewHolder(view);
            OnClickListener clickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()) {
                        case R.id.img_commodity_detail:
                            zoomPic();
                            break;
                        case R.id.dialog_close:
                            dialog.dismiss();
                            break;
                        case R.id.txt_dialog_ok:
                            dialog.dismiss();
                            break;
                        case R.id.txt_dialog_cart:
                            if (!isAllSelected) {
                                MyUtils.showToast(getApplicationContext(), "请选择商品属性");
                                return;
                            }
                            dialog.dismiss();
                            break;
                        case R.id.txt_dialog_buy:
                            dialog.dismiss();
                            break;
                        case R.id.dialog_product_sum_add:
                            dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) + 1));
                            break;
                        case R.id.dialog_product_sum_sub:
                            if (Integer.parseInt(dialog_product_sum.getText().toString()) > 1) {
                                dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) - 1));
                            }
                            break;
                    }
                }
            };
            contentDialog = DialogPlus.newDialog(this)
                    .setContentHolder(holder)
                    .setGravity(Gravity.BOTTOM)
                    .setCancelable(true)
                    .setOnClickListener(clickListener)
                    .setContentBackgroundResource(R.color.transParent)
                    .create();
        }


        showSpecificationDialog(showBottom);
    }

    /**
     * 展示商品属性选择Dialog
     *
     * @param showBottom
     */
    private void showSpecificationDialog(boolean showBottom) {
        if (showBottom) {
            confirmTxt.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.VISIBLE);
        } else {
            confirmTxt.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        }
        contentDialog.show();
    }

    /**
     * 动态增加商品的属性
     */
    private void addView() {
        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.product_detail_dialog_content_specification, null);
            TextView textView = (TextView) view.findViewById(R.id.txt_commodity_specification_name);
            final TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flayout_commodity_specification_content);
            textView.setText(temp[i]);
            final List<String> typeInfos = new ArrayList<>();
            if (i == 3)
                typeInfos.add(temp[i]);
            else
                for (int j = 0; j < 10; j++)
                    typeInfos.add(temp[i] + (j + 1));
            final TagAdapter<String> adapterType = new TagAdapter<String>(typeInfos) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv;
                    if (position != 0 && position % 3 == 0)
                        tv = (TextView) mInflater.inflate(R.layout.tv_enable, tagFlowLayout, false);
                    else
                        tv = (TextView) mInflater.inflate(R.layout.tv, tagFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }

                @Override
                public boolean setEnabled(int position, String s) {
                    if (position % 3 == 0)
                        return true;
                    return false;
                }
            };
            tagFlowLayouts.add(tagFlowLayout);
            tagFlowLayout.setAdapter(adapterType);
            tagFlowLayout.setTag(i);
            tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
//                    MyUtils.showToast(getApplicationContext(), "点击了第" + parent.getTag() + "个属性的" + "第" + position + "个子项");
                    getSelectedItem();
                    return false;
                }
            });
            dialog_specificationLayout.addView(view);
        }

    }

    /**
     * 获取选中的商品属性
     */
    private void getSelectedItem() {
        int len = tagFlowLayouts.size();
        select = "选择了 ";
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            String item = tagFlowLayouts.get(i).getSelectedItem();
            if (!TextUtils.isEmpty(item))
                select += "\"" + item + "\"" + " ";
            else
                integers.add(i);
        }
        if (integers.size() > 0) {
            setSelectedInfo(integers);
            isAllSelected = false;
        } else {
            selectedTxt.setText(select);
            isAllSelected = true;
        }
    }

    /**
     * 显示没有选中的属性或者显示选中的全部属性
     *
     * @param integers
     */
    private void setSelectedInfo(List<Integer> integers) {
        select = "选择";
        if (integers == null || integers.size() == 0) {
            int len = temp.length;

            for (int i = 0; i < len; i++) {
                select += temp[i] + " ";
            }
        } else {
            int len = integers.size();

            for (int i = 0; i < len; i++) {
                select += temp[integers.get(i)] + " ";
            }
        }

        selectedTxt.setText(select);
    }

    /**
     * 放大图片
     */
    private void zoomPic() {
        if (popUpWindow == null)
            popUpWindow = new BigPicPopUpWindow(this);
        popUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                titleBar.setBackgroundColor(ContextCompat.getColor(CommodityInfoActivity.this, R.color.ThemeBg));
                titleBar.getBackground().mutate().setAlpha(myScrollView.getScrollY() < screenWidth - titleBar.getHeight() ? 0 : 255);
            }
        });
        Glide.with(this).load(R.drawable.test).into(dialogBigImg);
        popUpWindow.showAtLocation(this.findViewById(R.id.rlayout_commodity_info), Gravity.CENTER, 0, 0);
        titleBar.setBackgroundColor(Color.BLACK);
    }

    /**
     * 查看大图的PopupWindow
     */
    class BigPicPopUpWindow extends PopupWindow {
        private View mMenuView;

        public BigPicPopUpWindow(Context context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mMenuView = inflater.inflate(R.layout.dialog_pic_view, null);
            dialogBigImg = (PinchImageView) mMenuView.findViewById(R.id.img_big);

            this.setContentView(mMenuView);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);

        }
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView.getScrollY() < screenWidth - titleBar.getHeight())
            titleBar.getBackground().mutate().setAlpha(0);
        else
            titleBar.getBackground().mutate().setAlpha(255);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (contentDialog != null && contentDialog.isShowing()) {
                contentDialog.dismiss();
                return true;
            }
            if (popUpWindow != null && popUpWindow.isShowing()) {
                popUpWindow.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
