package com.poomoo.homeonline.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名 BaseListAdapter
 * 描述 RecyclerView适配器基类
 * 作者 李苜菲
 * 日期 2016/7/19 11:29
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter {

    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    public final int BEHAVIOR_MODE;

    public static final int NEITHER = 0;
    public static final int ONLY_HEADER = 1;
    public static final int ONLY_FOOTER = 2;
    public static final int BOTH_HEADER_FOOTER = 3;

    protected static final int VIEW_TYPE_NORMAL = 0;
    protected static final int VIEW_TYPE_HEADER = -1;
    protected static final int VIEW_TYPE_FOOTER = -2;
    //日志标签
    protected final String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected List<T> items;
    protected LayoutInflater mInflater;
    protected int mState;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnLoadingListener onLoadingListener;
    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;

    public BaseListAdapter(Context context, int mode) {
        this(context, new ArrayList<T>(), mode);
    }

    public BaseListAdapter(Context context, List<T> items, int mode) {
        this.mContext = context;
        this.items = items;
        mState = STATE_HIDE;
        mInflater = LayoutInflater.from(mContext);
        BEHAVIOR_MODE = mode;
    }

    public final void addItem(T obj) {
        items.add(obj);
        notifyDataSetChanged();
    }

    public final void addItem(int position, T obj) {
        items.add(position, obj);
        notifyDataSetChanged();
    }

    public void setItems(List<T> objs) {
        items.clear();
        items.addAll(objs);
        notifyDataSetChanged();
    }

    public void addItems(List<T> objs) {
        items.addAll(objs);
        notifyDataSetChanged();
    }

    public final void addItems(int position, List<T> objs) {
        items.addAll(position, objs);
        notifyDataSetChanged();
    }

    public final void removeItem(int positon) {
        items.remove(positon);
        notifyDataSetChanged();
    }

    public final T getItem(int position) {
        return items.get(getIndex(position));
    }

//    public void removeObjectForId(long id) {
//        Iterator<T> iterator = items.iterator();
//        while (iterator.hasNext()) {
//            T obj = iterator.next();
//            if (obj.getId() == id) {
//                iterator.remove();
//                notifyDataSetChanged();
//            }
//        }
//    }

    private int getIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position - 1 : position;
    }

    public final void clear() {
        items.clear();
        mState = STATE_HIDE;
        notifyDataSetChanged();
    }

    public final List<T> getDataSet() {
        return items;
    }

    public final void setState(int state) {
        mState = state;
    }

    public final int getState() {
        return mState;
    }

    public final int getDataSize() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_HEADER;
        if (position + 1 == getItemCount() && (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_FOOTER;
        else return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == ONLY_HEADER) {
            return items.size() + 1;
        } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
            return items.size() + 2;
        } else return items.size();
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        switch (type) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    return onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                else throw new IllegalArgumentException("你使用了VIEW_TYPE_HEADER模式,但是你没有实现相应的接口");
            case VIEW_TYPE_FOOTER:
                return new FooterViewHolder(mInflater.inflate(R.layout.list_footer, parent, false));
            default:
                final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, type);
                if (holder != null) {
                    holder.itemView.setOnClickListener(v -> {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.getItemId(), v);
                    });

                    holder.itemView.setOnLongClickListener(v -> {
                        if (onItemLongClickListener == null)
                            return false;
                        onItemLongClickListener.onLongClick(holder.getAdapterPosition(), holder.getItemId(), v);
                        return true;
                    });
                }
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        switch (h.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    onLoadingHeaderCallBack.onBindHeaderHolder(h, position);
                break;

            case VIEW_TYPE_FOOTER:
//                LogUtils.d("BaseListAdapter", "VIEW_TYPE_FOOTER:" + mState + ":" + onLoadingListener);
                if (mState == STATE_LOAD_MORE && onLoadingListener != null) {
//                    LogUtils.d("thanatos", "loading...");
                    onLoadingListener.onLoading();
                }
                FooterViewHolder fvh = (FooterViewHolder) h;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (mState) {
                    case STATE_INVALID_NETWORK:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.invalid_network));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.loading));
                        fvh.probar.setVisibility(View.VISIBLE);
                        break;
                    case STATE_NO_MORE:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_no_more));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.refreshing));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_ERROR:
                        fvh.mStateText.setText(mContext.getResources().getText(R.string.load_failed));
                        fvh.probar.setVisibility(View.GONE);
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);
                        break;
                }
                break;

            default:
                onBindDefaultViewHolder(h, getIndex(position));
        }
    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    public final void setOnLoadingListener(OnLoadingListener listener) {
        onLoadingListener = listener;
    }

    public final void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack listener) {
        onLoadingHeaderCallBack = listener;
    }


    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position);

    /**
     * footer view holder
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar probar;
        public TextView mStateText;

        public FooterViewHolder(View view) {
            super(view);
            probar = (ProgressBar) view.findViewById(R.id.progressbar);
            mStateText = (TextView) view.findViewById(R.id.state_text);
        }
    }

    /**
     * adapter item on click
     */
    public interface OnItemClickListener {
        void onItemClick(int position, long id, View view);
    }

    /**
     * adapter item on long click
     */
    public interface OnItemLongClickListener {
        void onLongClick(int position, long id, View view);
    }

    /**
     * when footer disappear, we should load data from remote internet
     */
    public interface OnLoadingListener {
        void onLoading();
    }

    /**
     * for load header view
     */
    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }

}
