package com.frozy.autil.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class CommonAdapter<T> extends BaseAdapter implements Filterable {
    private final String TAG = getClass().getName();

    private Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    protected MultiItemTypeSupport<T> mMultiItemSupport;

    public CommonAdapter(Context mContext, List<T> mDatas, int itemLayoutId) {
        this.mContext = mContext;
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = itemLayoutId;
    }

    public CommonAdapter(Context mContext, List<T> mDatas, MultiItemTypeSupport<T> mMultiItemSupport) {
        this.mContext = mContext;
        this.mMultiItemSupport = mMultiItemSupport;
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mDatas.size())
            return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemSupport != null)
            return mMultiItemSupport.getViewTypeCount();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemSupport != null)
            return mMultiItemSupport.getItemViewType(position, mDatas.get(position));
        return 0;
    }

    public void addBeginItem(T item, boolean notifyDataSetChanged) {
        addItem(item, 0, false, notifyDataSetChanged);
    }

    public void addEndItem(T item, boolean notifyDataSetChanged) {
        addItem(item, mDatas.size(), false, notifyDataSetChanged);
    }

    public void addItem(T item, int position, boolean unique, boolean notifyDataSetChanged) {
        if (position >= 0 && position <= mDatas.size()) {
            if (unique) {
                if (!mDatas.contains(item)) {
                    mDatas.add(position, item);
                }
            }
            else {
                mDatas.add(position, item);
            }
            if (notifyDataSetChanged) {
                notifyDataSetChanged();
            }
        }
        else {
            Log.e(TAG, "positon at addItem() err ");
        }
    }

    public void addBeginItems(List<T> items, int position, boolean notifyDataSetChanged) {
        addItems(items, 0, notifyDataSetChanged);
    }

    public void addEndItems(List<T> items, boolean notifyDataSetChanged) {
        addItems(items, mDatas.size(), notifyDataSetChanged);
    }

    public void addItems(List<T> items, int position, boolean notifyDataSetChanged) {
        mDatas.addAll(position, items);
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public T findItem(T item) {
        int index = mDatas.indexOf(item);
        if (index != -1) {
            return null;
        }
        else {
            return mDatas.get(index);
        }
    }

    public int findItemIndex(T item) {
        return mDatas.indexOf(item);
    }

    public void updateItem(T item, int position, boolean notifyDataSetChanged) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.set(position, item);
            if (notifyDataSetChanged) {
                notifyDataSetChanged();
            }
        }
        else {
            Log.e(TAG, "positon at upDateItem() err ");
        }
    }

    public void updateAll(List<T> items, boolean notifyDataSetChanged) {
        if (items != null) {
            mDatas.clear();
            mDatas.addAll(items);
            if (notifyDataSetChanged) {
                notifyDataSetChanged();
            }
        }
        else {
            Log.e(TAG, "items at updateAll() null !! ");
        }
    }

    public void remove(T item, boolean notifyDataSetChanged) {
        int index = mDatas.indexOf(item);
        remove(index, notifyDataSetChanged);
    }

    public void remove(int position, boolean notifyDataSetChanged) {
        if (position >= 0 && position <= mDatas.size()) {
            mDatas.remove(position);
            if (notifyDataSetChanged) {
                notifyDataSetChanged();
            }
        }
        else {
            Log.e(TAG, "positon at remove() err ");
        }
    }

    public void clear(boolean notifyDataSetChanged) {
        mDatas.clear();
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseViewHolder viewHolder = getViewHolder(position, convertView, parent);
        T item = getItem(position);
        viewHolder.setAssociatedObject(item);
        convert(viewHolder, item, position);
        return viewHolder.getConvertView();
    }

    private BaseViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        if (mMultiItemSupport != null) {
            return BaseViewHolder.get(mContext, convertView, parent,
                    mMultiItemSupport.getLayoutId(position, mDatas.get(position)), position);
        }
        else {
            return BaseViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
        }
    }

    public List<T> getItems() {
        return mDatas;
    }

    public abstract void convert(BaseViewHolder helper, T item, int position);

    public interface MultiItemTypeSupport<T> {
        int getLayoutId(int position, T t);

        int getViewTypeCount();

        int getItemViewType(int postion, T t);
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
