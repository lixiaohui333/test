package com.meeting.client.ui.view.pullrefresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meeting.client.R;


/**
 * 这个类封装了下拉刷新的布局
 * 
 * @author Li Hong
 * @since 2013-7-30
 */
public class FooterLoadingLayout extends LoadingLayout {
	/** 进度条 */
	private ProgressBar mProgressBar;
	/** 显示的文本 */
	private TextView mHintView;
	/** 没有更多 */
	private CharSequence mNoMoreMsg;
	private LinearLayout mContent;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 */
	public FooterLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 */
	public FooterLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 *            context
	 */
	private void init(Context context) {
		mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);
		mHintView = (TextView) findViewById(R.id.pull_to_load_footer_hint_textview);
		mContent = (LinearLayout) findViewById(R.id.pull_to_load_footer_content);
		mNoMoreMsg ="这是我的底线啦~";
		setState(State.RESET);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(
				R.layout.pull_to_load_footer, null);
		return container;
	}

	/**没有更多的显示文本*/
	public void setNoMoreMsg(CharSequence msg){
		mNoMoreMsg = msg;
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
	}

	@Override
	public int getContentSize() {
		if (getHeight()!= 0) {
			return getHeight();
		}
		return (int) (getResources().getDisplayMetrics().density * 60);
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		mProgressBar.setVisibility(View.GONE);
		mHintView.setVisibility(View.INVISIBLE);

		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		setBackgroundColor(0x00000000);
		mContent.setBackgroundColor(0x00000000);
		mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
		setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPullToRefresh() {
		mProgressBar.setVisibility(View.VISIBLE);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_refresh_header_hint_normal2);
		setVisibility(View.VISIBLE);
	}

	@Override
	protected void onReleaseToRefresh() {
		mProgressBar.setVisibility(View.VISIBLE);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_refresh_header_hint_ready);
		setVisibility(View.VISIBLE);
	}

	@Override
	protected void onLoading() {
		show(true);
		setBackgroundColor(0x00000000);
		mContent.setBackgroundColor(0x00000000);
		mProgressBar.setVisibility(View.VISIBLE);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
		setVisibility(View.VISIBLE);
	}

	@Override
	protected void onNoMoreData() {

		if(TextUtils.isEmpty(mNoMoreMsg)){
			setVisibility(View.GONE);
		}else{
			setBackgroundColor(0x00000000);
			mContent.setBackgroundResource(R.drawable.shape_footer_bg);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(mNoMoreMsg);
			setVisibility(View.VISIBLE);
		}
	}
	protected void onClickToReload(){
		show(true);
		setBackgroundColor(0xffffffff);
		mContent.setBackgroundResource(R.drawable.shape_footer_bg);
		mHintView.setVisibility(View.VISIBLE);
		mHintView.setText(R.string.click_to_reload_more);
		setVisibility(View.VISIBLE);
	}
}
