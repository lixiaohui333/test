package com.meeting.client.ui.view.pullload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;
import com.andview.refreshview.utils.Utils;
import com.meeting.client.R;
import com.meeting.client.business.gilde.GlideUtil;

public class NoMoreDataFooterView extends LinearLayout implements IFooterCallBack {
    private Context mContext;

    private View mContentView;
    private View ll_dixian;
//    private View mProgressBar;
//    private TextView mHintView;
    private TextView mClickView;
    private ImageView iv_loading;
    private boolean showing = true;

    public NoMoreDataFooterView(Context context) {
        super(context);
        initView(context);
    }

    public NoMoreDataFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView xRefreshView) {
        View childView = xRefreshView.getChildAt(1);
        if (childView != null && childView instanceof RecyclerView) {
            show(Utils.isRecyclerViewFullscreen((RecyclerView) childView));
            xRefreshView.enableReleaseToLoadMore(Utils.isRecyclerViewFullscreen((RecyclerView) childView));
        }
        mClickView.setText(R.string.xrefreshview_footer_hint_click);
        mClickView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                xRefreshView.notifyLoadMore();
            }
        });
    }

    @Override
    public void onStateReady() {
//        mHintView.setVisibility(View.GONE);
        iv_loading.setVisibility(GONE);
//        mProgressBar.setVisibility(View.GONE);
        mClickView.setText(R.string.xrefreshview_footer_hint_click);
        mClickView.setVisibility(View.VISIBLE);
        ll_dixian.setVisibility(GONE);
//        show(true);
    }

    @Override
    public void onStateRefreshing() {
//        mHintView.setVisibility(View.GONE);
        iv_loading.setVisibility(VISIBLE);
//        mProgressBar.setVisibility(View.VISIBLE);
        mClickView.setVisibility(View.GONE);
        ll_dixian.setVisibility(GONE);
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        ll_dixian.setVisibility(GONE);
//        mHintView.setVisibility(View.GONE);
        iv_loading.setVisibility(GONE);
//        mProgressBar.setVisibility(View.GONE);
        mClickView.setText(R.string.xrefreshview_footer_hint_release);
        mClickView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateFinish(boolean hideFooter) {
        if (hideFooter) {
//            mHintView.setText(R.string.xrefreshview_footer_hint_normal);
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
//            ll_dixian.setVisibility(VISIBLE);


        }

        ll_dixian.setVisibility(VISIBLE);



//        mProgressBar.setVisibility(View.GONE);
        iv_loading.setVisibility(GONE);
        mClickView.setVisibility(View.GONE);
    }

    @Override
    public void onStateComplete() {
//        mHintView.setText(com.andview.refreshview.R.string.xrefreshview_footer_hint_complete);
//        mHintView.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.GONE);
        ll_dixian.setVisibility(VISIBLE);
        iv_loading.setVisibility(GONE);
        mClickView.setVisibility(View.GONE);
    }

    @Override
    public void show(final boolean show) {
        if (show == showing) {
            return;
        }
        showing = show;
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        mContentView.setLayoutParams(lp);
//        setVisibility(show?VISIBLE:GONE);

    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup moreView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.refresh_xrefreshview_footer, this);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        iv_loading = moreView.findViewById(R.id.iv_loading);


        mContentView = moreView.findViewById(R.id.xrefreshview_footer_content);
        ll_dixian = moreView.findViewById(R.id.ll_dixian);
        ll_dixian.setVisibility(GONE);
//        mProgressBar = moreView
//                .findViewById(R.id.xrefreshview_footer_progressbar);
//        mHintView = (TextView) moreView
//                .findViewById(R.id.xrefreshview_footer_hint_textview);
        mClickView = (TextView) moreView
                .findViewById(R.id.xrefreshview_footer_click_textview);

        GlideUtil.displayAsGif(iv_loading,R.drawable.load_more_loading,true);

//        iv_loading =  moreView
//                .findViewById(R.id.iv_loading);

//        RotateAnimation gearAnim = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.loadmore_loading_progressbar_anim);
//
//        iv_loading.startAnimation(gearAnim);

        //        AnimationDrawable animationDrawable = (AnimationDrawable)iv_loading.getDrawable();
//        animationDrawable.start();

    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
