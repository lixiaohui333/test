package com.meeting.client.ui.base;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.ui.view.pullrefresh.PullToRefreshBase;
import com.meeting.client.ui.view.pullrefresh.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public abstract class BaseListFragment extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener {


    protected String TAG = BaseListFragment.class.getName();

    public PullToRefreshListView mPullRefreshListView;
    protected ListView actualListView;
    public TextView tv_empty;
    private LinearLayout ll_empty;
    private ImageView iv_empty;
    private TextView tv_empty_btn;
    private TextView tv_reload;


    @Override
    protected View initView(LayoutInflater inflater) {
        viewRoot = inflater.inflate(R.layout.activity_base_list_notitle, null);
        return viewRoot;
    }

    /**
     * 没有数据时的提示信息
     */
    public void showEmptyMessage(String text) {

        if (ll_empty != null) {
            ll_empty.setVisibility(View.VISIBLE);
        }

        if (tv_empty != null) {
            if (!TextUtils.isEmpty(text)) {
                tv_empty.setText(text);
            }
        }

        if (actualListView != null && actualListView.getEmptyView() == null) {
            actualListView.setEmptyView(ll_empty);
        }
    }

    public void showEmptyMessage(String text, int resId) {
        setEmptyImage(resId);
        showEmptyMessage(text);
    }

    public void showEmptyMessage(String text, int resId, String info, View.OnClickListener click) {
        setEmptyImage(resId);
        showEmptyMessage(text);
        setEmptyBtn(info,click);
    }

    public void hideEmptyMessage() {
        if (ll_empty != null) {
            ll_empty.setVisibility(View.GONE);
        }
    }

    public void setEmptyImage(int resId) {

        if (iv_empty != null) {
            iv_empty.setVisibility(View.VISIBLE);
            iv_empty.setBackgroundResource(resId);
        }

    }

    public void setEmptyBtn(String info, View.OnClickListener click) {
        if (tv_empty_btn != null) {
            tv_empty_btn.setText(info);
            tv_empty_btn.setVisibility(View.VISIBLE);
            if (click != null) {
                tv_empty_btn.setOnClickListener(click);
            }
        }
    }

    // 加载更多
    protected abstract void loadMoreData();

    // 上拉刷新
    protected abstract void loadNewData();


    /**
     * @author wkt
     * @Desc: 重置加载的状态 1. 加载失败的状态 重置 2. 没有更多的状态重置
     */
    public void resetLoadState() {
        loadMoreError(false);
        hasMoreData(true);
    }

    /**
     * @param isError true 表示失败，false 表示正常
     * @author wkt
     * @Desc: 上一次加载更多是否失败, 如果失败则不自动加载
     */
    public void loadMoreError(boolean isError) {
        if (mPullRefreshListView != null) {
            mPullRefreshListView.setLoadMoreError(isError);
        }
    }

    /**
     * @param hasMore true 表示有更多，false 表示没有更多
     * @author wkt
     * @Desc: 设置是否可加载更多
     */
    public void hasMoreData(boolean hasMore) {
        if (mPullRefreshListView != null) {
            mPullRefreshListView.setHasMoreData(hasMore);
        }
    }

    /**
     * @param hasmore 是否能加载更多
     * @author lxh
     * @Desc:设置adpater
     */
    protected void setAdapter(boolean hasmore) {
        onPullDownUpRefreshComplete(hasmore);
    }

    public void onPullDownUpRefreshComplete(boolean hasmore) {
        // 刷新结束的时候
        onPullDownUpRefreshComplete();
        mPullRefreshListView.setHasMoreData(hasmore);
    }

    protected void onPullDownUpRefreshComplete() {
        // 刷新结束的时候
        if (mPullRefreshListView == null)
            return;
        mPullRefreshListView.onPullDownRefreshComplete();
        mPullRefreshListView.onPullUpRefreshComplete();
        mPullRefreshListView.setLastUpdatedLabel(getTime());
    }

    // 上拉获取更多
    protected void onPullUpRefreshComplete() {
        // 刷新结束的时候
        if (mPullRefreshListView != null)
            mPullRefreshListView.onPullUpRefreshComplete();
    }


    // 上拉取消
    protected void closePullUpRefresh() {
        // 刷新结束的时候
        if (mPullRefreshListView != null) {
            mPullRefreshListView.setPullLoadEnabled(false);
            mPullRefreshListView.setAutoLoadOnBottomEnabled(false);

        }
    }

    /**
     *设置不再显示没有更多了 不加载更多了
     * params open 是否不显示了
     */
    protected void hasGoneMoreData(boolean goneMoreView) {

        if (goneMoreView) {

            mPullRefreshListView.setNoMoreMsg("");
            mPullRefreshListView.setHasMoreData(false);
        } else {

            mPullRefreshListView.setNoMoreMsg("没有更多了");

            mPullRefreshListView.setHasMoreData(true);
        }

    }


    // 下拉取消
    protected void closePullDownRefresh() {
        // 刷新结束的时候
        if (mPullRefreshListView != null)
            mPullRefreshListView.setPullRefreshEnabled(false);
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
    }

    @Override
    public void onPullDownToRefresh() {
        resetLoadState();
        loadNewData();
    }

    @Override
    public void onPullUpToRefresh() {
        loadMoreData();
    }

    @Override
    protected void initUI() {


        tv_empty = (TextView) viewRoot.findViewById(R.id.empty);
        ll_empty = (LinearLayout) viewRoot.findViewById(R.id.ll_empty);
        iv_empty = (ImageView) viewRoot.findViewById(R.id.iv_empty);
        tv_empty_btn = (TextView) viewRoot.findViewById(R.id.tv_empty_btn);

        progressBar_View = viewRoot.findViewById(R.id.progressBar_View);
        iv_progress = (ImageView) viewRoot.findViewById(R.id.iv_progress);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_progress
                .getDrawable();
        animationDrawable.start();
//        ll_loading =  viewRoot.findViewById(R.id.ll_loading);
//        ll_load_error =  viewRoot.findViewById(R.id.ll_load_error);
//
//        iv_error_icon = (ImageView) viewRoot.findViewById(R.id.iv_error_icon);
//        tv_error_msg = (TextView) viewRoot.findViewById(R.id.tv_error_msg);
        tv_reload = (TextView)viewRoot.findViewById(R.id.tv_reload);

        tv_empty = (TextView) viewRoot.findViewById(R.id.empty);
        ll_empty = (LinearLayout) viewRoot.findViewById(R.id.ll_empty);
        iv_empty = (ImageView) viewRoot.findViewById(R.id.iv_empty);
        tv_empty_btn = (TextView) viewRoot.findViewById(R.id.tv_empty_btn);

        mPullRefreshListView = (PullToRefreshListView) viewRoot
                .findViewById(R.id.listView);
        mPullRefreshListView.setAutoLoadOnBottomEnabled(true);
        actualListView = mPullRefreshListView.getRefreshableView();
        mPullRefreshListView.setOnRefreshListener(this);
        actualListView.setFadingEdgeLength(0);
        actualListView.setCacheColorHint(0);
        actualListView.setDivider(new ColorDrawable(Color.parseColor("#e5e5e5")));
        actualListView.setDividerHeight(1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewRoot = null;
        mPullRefreshListView = null;
        actualListView = null;
        ll_empty = null;
        iv_empty = null;
        tv_empty_btn = null;
//        rl_not_network = null;
        TAG = null;
        tv_empty = null;
    }
}
