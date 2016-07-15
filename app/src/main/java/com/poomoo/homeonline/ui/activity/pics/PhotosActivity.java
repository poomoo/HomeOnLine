package com.poomoo.homeonline.ui.activity.pics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.poomoo.commlib.MyConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ImageBucketAdapter;
import com.poomoo.homeonline.picUtils.AlbumHelper;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.ImageBucket;

import java.io.Serializable;
import java.util.List;

public class PhotosActivity extends BaseActivity {
    private List<ImageBucket> imageBucketList;
    private GridView gridView;
    private ImageBucketAdapter adapter;// 自定义的适配器
    private AlbumHelper helper;
    public static Bitmap bimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_image_bucket;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_bucket;
    }

    protected void initView() {
        setBack();

        initData();

        gridView = (GridView) findViewById(R.id.grid_image_bucket);
        adapter = new ImageBucketAdapter(this);
        gridView.setAdapter(adapter);
        adapter.addItems(imageBucketList);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(PhotosActivity.this, ImageGridActivity.class);
                intent.putExtra(MyConfig.EXTRA_IMAGE_LIST, (Serializable) imageBucketList.get(position).imageList);
                startActivity(intent);
                finish();
            }

        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        imageBucketList = helper.getImagesBucketList(false);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_image);
    }

}
