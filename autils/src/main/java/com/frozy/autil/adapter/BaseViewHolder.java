package com.frozy.autil.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 基础viewholder类
 */
@SuppressWarnings("unused")
public class BaseViewHolder {

    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;
    private Object associatedObject;
    public int mLayoutId;
    private int mPosition;

    private BaseViewHolder(Context aContext, ViewGroup parent, int layoutId, int position) {
        this.mContext = aContext;
        this.mLayoutId = layoutId;
        this.mPosition = position;

        this.init(parent);
    }

    private void init(ViewGroup parent) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static BaseViewHolder get(Context context, View convertView, ViewGroup parent,
                                     int layoutId, int position) {
        if (convertView == null) {
            return new BaseViewHolder(context, parent, layoutId, position);
        }

        BaseViewHolder existingViewHolder = (BaseViewHolder) convertView.getTag();
        if (existingViewHolder.mLayoutId != layoutId) {
            return new BaseViewHolder(context, parent, layoutId, position);
        }
        existingViewHolder.mPosition = position;
        return existingViewHolder;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     */
    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字符串
     */
    public BaseViewHolder setText(int viewId, CharSequence text, TextView.BufferType type) {
        TextView view = getView(viewId);
        view.setText(text, type);
        return this;
    }

    /**
     * 为TextView设置字符串
     */
    public BaseViewHolder setText(int viewId, int resId) {
        TextView view = getView(viewId);
        view.setText(resId);
        return this;
    }

    /**
     * 为TextView设置字符串
     */
    public BaseViewHolder setText(int viewId, int resId, TextView.BufferType type) {
        TextView view = getView(viewId);
        view.setText(resId, type);
        return this;
    }


    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置背景颜色
     */
    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    @SuppressWarnings("deprecation")
    public BaseViewHolder setTextColorRes(int viewId, Activity activity, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(activity.getResources().getColor(textColorRes));
        return this;
    }

    public BaseViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        }
        else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public Object getAssociatedObject() {
        return associatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }


}
