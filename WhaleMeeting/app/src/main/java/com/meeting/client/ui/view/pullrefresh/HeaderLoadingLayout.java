package com.meeting.client.ui.view.pullrefresh;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meeting.client.R;


/**
 * 这个类封装了下拉刷新的布局
 * 
 * @author Li Hong
 * @since 2013-7-30
 */
public class HeaderLoadingLayout extends LoadingLayout {
	/** 旋转动画时间 */
	private static final int ROTATE_ANIM_DURATION = 150;
	/** Header的容器 */
	private RelativeLayout mHeaderContainer;
	/** 箭头图片 */
	// private ImageView mArrowImageView;


	/** 状态提示TextView */
	private TextView mHintTextView;

	private ProgressBar pull_to_load_footer_progressbar;

	private ImageView iv_load_status;


	/** 最后更新时间的TextView */
//	private TextView mHeaderTimeView;
	/** 最后更新时间的标题 */
//	private TextView mHeaderTimeViewTitle;
	/** 向上的动画 */
	private Animation mRotateUpAnim;
	/** 向下的动画 */
	private Animation mRotateDownAnim;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 */
	public HeaderLoadingLayout(Context context) {
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
	public HeaderLoadingLayout(Context context, AttributeSet attrs) {
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
		mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);


		mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
		pull_to_load_footer_progressbar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);



		iv_load_status = (ImageView) findViewById(R.id.iv_load_status);
//		AnimationDrawable animationDrawable = (AnimationDrawable) iv_header_progressbar
//				.getDrawable();
//		animationDrawable.start();
//		mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
//		mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_to_refresh_last_update_time_text);

		float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
		float toDegree = -180f; // SUPPRESS CHECKSTYLE
		// 初始化旋转动画
		mRotateUpAnim = new RotateAnimation(0.0f, toDegree,
				Animation.RELATIVE_TO_SELF, pivotValue,
				Animation.RELATIVE_TO_SELF, pivotValue);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(toDegree, 0.0f,
				Animation.RELATIVE_TO_SELF, pivotValue,
				Animation.RELATIVE_TO_SELF, pivotValue);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		// 如果最后更新的时间的文本是空的话，隐藏前面的标题
//		mHeaderTimeViewTitle
//				.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE
//						: View.VISIBLE);
//		mHeaderTimeView.setText(label);
	}
	@Override
	public void setLoadingDrawable(Drawable drawable){
		if(iv_load_status!=null){
			iv_load_status.setImageDrawable(drawable);
		}
	}

	@Override
	public int getContentSize() {
		if (null != mHeaderContainer) {
			return mHeaderContainer.getHeight();
		}

		return (int) (getResources().getDisplayMetrics().density * 60);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, null);
		return container;
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {



		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		// mArrowImageView.clearAnimation();

		iv_load_status.setVisibility(View.VISIBLE);
		iv_load_status.setImageResource(R.drawable.loading_refresh);
		pull_to_load_footer_progressbar.setVisibility(GONE);

		mHintTextView.setText("下拉刷新");
	}

	@Override
	protected void onPullToRefresh() {
		if (State.RELEASE_TO_REFRESH == getPreState()) {
			// mArrowImageView.clearAnimation();
			// mArrowImageView.startAnimation(mRotateDownAnim);
		}

		iv_load_status.setImageResource(R.drawable.loading_refresh);
		iv_load_status.setVisibility(View.VISIBLE);
		pull_to_load_footer_progressbar.setVisibility(GONE);

		mHintTextView.setText("下拉刷新");
	}

	@Override
	protected void onReleaseToRefresh() {

		iv_load_status.setImageResource(R.drawable.loading_songkai);
		iv_load_status.setVisibility(View.VISIBLE);
		mHintTextView.setText("松开刷新");
		pull_to_load_footer_progressbar.setVisibility(GONE);

	}

	@Override
	protected void onRefreshing() {
		// mArrowImageView.clearAnimation();
		// mArrowImageView.setVisibility(View.INVISIBLE);
		iv_load_status.setVisibility(View.VISIBLE);

		iv_load_status.setImageResource(R.drawable.loading_refresh);
		pull_to_load_footer_progressbar.setVisibility(VISIBLE);

		mHintTextView.setText("    ");

	}
}
