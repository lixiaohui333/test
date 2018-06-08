package com.meeting.client.ui.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
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
public class RotateLoadingLayout extends LoadingLayout {
	/** 旋转动画的时间 */
	static final int ROTATION_ANIMATION_DURATION = 1200;
	/** 动画插值 */
	static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
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
//	/** 最后更新时间的标题 */
//	private TextView mHeaderTimeViewTitle;
	/** 旋转的动画 */
	private Animation mRotateAnimation;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 */
	public RotateLoadingLayout(Context context) {
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
	public RotateLoadingLayout(Context context, AttributeSet attrs) {
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


		float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
		float toDegree = 720.0f; // SUPPRESS CHECKSTYLE
		mRotateAnimation = new RotateAnimation(0.0f, toDegree,
				Animation.RELATIVE_TO_SELF, pivotValue,
				Animation.RELATIVE_TO_SELF, pivotValue);
		mRotateAnimation.setFillAfter(true);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, null);

//		ImageView iv_header_progressbar = (ImageView) container
//				.findViewById(R.id.iv_header_progressbar);
//		AnimationDrawable animationDrawable = (AnimationDrawable) iv_header_progressbar
//				.getDrawable();
//		animationDrawable.start();
//		LayoutParams params = (LayoutParams) iv_header_progressbar
//				.getLayoutParams();
//		params.width = UiUtils.dip2px( 25);
//		params.height = UiUtils.dip2px( 25);

		return container;
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		// 如果最后更新的时间的文本是空的话，隐藏前面的标题
	}

	@Override
	public int getContentSize() {
		if (null != mHeaderContainer) {
			return mHeaderContainer.getHeight();
		}

		return (int) (getResources().getDisplayMetrics().density * 60);
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {

		iv_load_status.setVisibility(View.VISIBLE);
		iv_load_status.setImageResource(R.drawable.loading_refresh);
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
	protected void onPullToRefresh() {
		iv_load_status.setImageResource(R.drawable.loading_refresh);
		iv_load_status.setVisibility(View.VISIBLE);
		pull_to_load_footer_progressbar.setVisibility(GONE);

		mHintTextView.setText("下拉刷新");
	}

	@Override
	protected void onRefreshing() {

		iv_load_status.setVisibility(View.VISIBLE);

		iv_load_status.setImageResource(R.drawable.loading_loose);
		pull_to_load_footer_progressbar.setVisibility(VISIBLE);

		mHintTextView.setText("  ");
	}

	@Override
	public void onPull(float scale) {

	}
}
