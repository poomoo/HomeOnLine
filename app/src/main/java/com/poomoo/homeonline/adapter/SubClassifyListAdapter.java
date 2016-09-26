package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.model.response.RSubClassifyBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 SubClassifyListAdapter
 * 描述 二级分类适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class SubClassifyListAdapter extends BaseListAdapter<RSubClassifyBO> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_NORMAL = 2;
    private ClassifyOnItemClickListener onItemClickListener;
    private RSubClassifyBO item;
    private int width = 0;
    private int margin = 0;

    public SubClassifyListAdapter(Context context, int mode, ClassifyOnItemClickListener onItemClickListener) {
        super(context, mode);
        this.onItemClickListener = onItemClickListener;
        margin = (int) mContext.getResources().getDimension(R.dimen.dp_10);
        width = MyUtils.getScreenWidth(context) * 3 / 4
                - margin * 2//左右边距
                - margin * 2;//item左右边距
        width /= 3;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        LogUtils.d(TAG, "type:" + type);
        switch (type) {
            case VIEW_TYPE_HEADER:
                return new TitleViewHolder(mInflater.inflate(R.layout.item_list_subclassify_title, parent, false));
            case VIEW_TYPE_NORMAL:
            default:
                return new ContentViewHolder(mInflater.inflate(R.layout.item_list_subclassify_content, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) return VIEW_TYPE_HEADER;
        else return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        item = items.get(position);
        switch (h.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                TitleViewHolder titleViewHolder = (TitleViewHolder) h;
                titleViewHolder.classifyTxt.setText(item.categoryName);
                Glide.with(mContext).load(NetConfig.ImageUrl + item.categoryPic).into(titleViewHolder.classifyImg);
                break;
            case VIEW_TYPE_NORMAL:
                ContentViewHolder contentViewHolder = (ContentViewHolder) h;
                int len = item.childrenList.size();
                View parent = LayoutInflater.from(mContext).inflate(R.layout.item_list_subclassify_content_parent, null);
                LinearLayout parentLayout = (LinearLayout) parent.findViewById(R.id.llayout_parent);
                contentViewHolder.linearLayout.removeAllViews();
                for (int i = 0; i < len; i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_sub_classify, null);
                    ImageView img = (ImageView) view.findViewById(R.id.img_thirdClassify);
                    TextView nameTxt = (TextView) view.findViewById(R.id.txt_thirdClassify);

                    LinearLayout.LayoutParams imgLP = new LinearLayout.LayoutParams(width - margin * 2, width - margin * 2);
                    img.setLayoutParams(imgLP);

                    Glide.with(mContext).load(NetConfig.ImageUrl + item.childrenList.get(i).categoryPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.ic_ticket).into(img);
                    nameTxt.setText(item.childrenList.get(i).categoryName);
                    view.setOnClickListener(new OnItemClick(item.childrenList.get(i).id + ""));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
                    if ((i - 1) % 3 == 0)
                        layoutParams.setMargins(margin, 0, margin, 0);
                    view.setLayoutParams(layoutParams);

                    parentLayout.addView(view);
                    if ((i + 1) % 3 == 0 || i == len - 1) {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, (int) mContext.getResources().getDimension(R.dimen.dp_10), 0, 0);
                        parent.setLayoutParams(lp);
                        contentViewHolder.linearLayout.addView(parent);
                        parent = LayoutInflater.from(mContext).inflate(R.layout.item_list_subclassify_content_parent, null);
                        parentLayout = (LinearLayout) parent.findViewById(R.id.llayout_parent);
                    }
                }
                break;
        }
    }

    public static final class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_subTitle)
        ImageView classifyImg;
        @Bind(R.id.txt_subTitle)
        TextView classifyTxt;

        public TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class ContentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.llayout_subClassify)
        LinearLayout linearLayout;

        public ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class OnItemClick implements View.OnClickListener {
        private String categoryId;

        public OnItemClick(String categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(this.categoryId);
        }
    }

}
