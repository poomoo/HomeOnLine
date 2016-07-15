package com.poomoo.homeonline.ui.activity.pics;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ImageGridAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.ImageItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class ImageGridActivity extends BaseActivity {
    private List<ImageItem> dataList;
    private GridView gridView;
    private Button btn;
    private ImageGridAdapter adapter;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    MyUtils.showToast(getApplicationContext(), getString(R.string.toast_3_pics));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_image_grid;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_bucket;
    }

    protected void initView() {
        setBack();

        gridView = (GridView) findViewById(R.id.grid_image);
        btn = (Button) findViewById(R.id.btn_completed);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, mHandler);
        gridView.setAdapter(adapter);
        dataList = (List<ImageItem>) getIntent().getSerializableExtra(MyConfig.EXTRA_IMAGE_LIST);
        adapter.setItems(dataList);

        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                btn.setText(getString(R.string.btn_complete) + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void toCompleted(View view) {
        //图片地址
        ArrayList<String> list = new ArrayList<>();
        Collection<String> c = adapter.map.values();
        Iterator<String> it = c.iterator();
        for (; it.hasNext(); ) {
            list.add(it.next());
        }

        if (Bimp.act_bool) {
            finish();
            Bimp.act_bool = false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (Bimp.drr.size() < 3) {
                Bimp.drr.add(list.get(i));
            }
        }
        finish();
    }
}
