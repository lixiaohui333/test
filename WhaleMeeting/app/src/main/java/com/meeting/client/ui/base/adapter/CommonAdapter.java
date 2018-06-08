package com.meeting.client.ui.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author lxh 2016/4/17.
 * @desc  万能适配器
 */
public abstract class CommonAdapter<t> extends BaseAdapter {
    /** 上下文 */
    protected Context mContext;
    /** 数据源 */
    protected List<t> mData;
    private int mLayoutId;

    public CommonAdapter(Context mContext, int layoutId) {
        super();
        this.mContext = mContext;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 :mData.size();
    }

    @Override
    public t getItem(int position) {
        return (t) (mData == null ? 0 : mData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = ViewHolder.getViewHolder(mContext, convertView,
                parent, mLayoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();

    }

    /*** 设置数据 */
    public abstract void setData(List<t> data);

    /*** 实现给View赋数据的方法 */
    public abstract void convert(ViewHolder holder, t item);
}