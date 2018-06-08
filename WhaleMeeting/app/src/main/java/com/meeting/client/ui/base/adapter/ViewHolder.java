package com.meeting.client.ui.base.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author lxh 2016/4/17.
 * @desc    万能适配器的holder
 */
public class ViewHolder {
    /** the object of the TAG */
    private String TAG = getClass().getSimpleName();
    /** the object of the view */
    private SparseArray<View> mViews;
    /** the object of the position */
    private int mPosition;
    /** the object of the converview */
    private View mConvertView;

    /***
     * 构造方法
     *
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        super();
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);

    }

    /**
     * get the object of the ViewHolder
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder getViewHolder(Context context, View convertView,
                                           ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();

            if(holder==null){
                return new ViewHolder(context, parent, layoutId, position);
            }

            // 修改位置变化
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * find the view by the viewId
     *
     * @param viewId
     * @return
     */
    public <T extends View> T findView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            // 添加View对象
            mViews.put(viewId, view);

        }
        return (T) view;
    }

    /**
     * get the object of the convertView
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 为TextView设置值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView textView = findView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 为TextView设置值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, SpannableString text) {
        TextView textView = findView(viewId);
        textView.setText(text);
        return this;
    }


    /**
     * 为TextView设置值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, Spanned text) {
        TextView textView = findView(viewId);
        textView.setText(text);
        return this;
    }


    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = findView(viewId);
        textView.setText(text);
        return this;
    }
    /**
     * 为ImageView设置值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = findView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 为ImageView设置动画
     *
     * @param viewId
     * @param anim
     * @return
     */
    public ViewHolder startAnimation(int viewId, Animation anim) {
        ImageView view = findView(viewId);
        view.setAnimation(anim);
        return this;
    }

    // TODO 待写View设置的辅助的方法

}