package com.meeting.client.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meeting.client.R;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.comm.util.TUtil;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.eventbus.EventClose;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.ui.activity.logo.SplashSettingsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/3/29.
 */

public abstract class BaseFragmentActivity<T extends BasePresenter, E extends NetTaskModel> extends AppCompatActivity {

    protected String TAG;

    protected String TAG_OBERVETASK = "OberveTaskSJYT";

    //当前运行的activity
    public static BaseFragmentActivity activity;

    protected Context ct;

    protected Intent preIntent;

    protected final Handler baseHandler = new Handler();


    protected T mPresenter;
    protected E mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ct = this;
        activity = this;
        TAG = getClass().getName();

        initView();

        EventBus.getDefault().register(this);

        ButterKnife.bind(this);

        if (intentData()) {

            initMvp()
            ;

            initUI();
        }
    }

    private void initMvp() {

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        if (mPresenter == null || mModel == null)
            return;

        if (this instanceof BaseNetView)
            mPresenter.bindTaskAndView(mModel, (BaseNetView) this);
    }

    public void apiError(BaseHR baseHR) {

        if (baseHR == null)
            return;

        if (baseHR.sysStatus != BaseHR.HTTP_OK || baseHR.apiStatus != BaseHR.HTTP_OK) {
            showToastError(baseHR.info);
        }
    }

    protected void showToast(String info) {

        if (TextUtils.isEmpty(info))
            return;


        Toasty.info(UiUtil.getContext(), info, Toast.LENGTH_SHORT, false).show();
    }


    protected void showToastError(String info) {

        if (TextUtils.isEmpty(info))
            return;

        Toasty.error(UiUtil.getContext(), info, Toast.LENGTH_SHORT, false).show();
    }

    public void goactivity(Class activity) {
        Intent in = new Intent(ct, activity);
        startActivity(in);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void eventMessage(EventMessage message) {
        LogHelper.i(TAG, "eventMessage:----->" + message.toString());

        if (EventClose.class.getName().equals(message.method)) {
            if (!TAG.equals(message.rel)) {
                finish();
            }

        }
        if (TAG.equals(message.rel)) {
            getEventMessage(message);
            LogHelper.i(TAG, message.toString());
        }
    }

    protected void getEventMessage(EventMessage message) {
        LogHelper.i(TAG, message.toString());
    }

    protected void sendEventBus(EventMessage message) {
        EventBus.getDefault().post(message);
    }


    /**
     * 初始化数据，主要是针对intent过来的data 如果返回true 继续执行下面的initUI,false反之.
     */
    protected abstract boolean intentData();

    /**
     * 初始化view，自动调用
     */
    protected abstract void initView();

    /**
     * 初始化UI，自动调用
     */
    protected abstract void initUI();


    /**
     * 设置请求数据后的UI，需手动调用。
     */
    protected abstract void setUI();

    /**
     * 数据请求 需手动调用。 若首次进入界面可实现生命周期onCreateView以后的方法，然后手动调用。
     */
    protected abstract void loadInitData();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isFinishing()) {
            ct = null;
            TAG = null;
            if (mPresenter != null)
                mPresenter.onDestroy();
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setMyTitle(String title, View toolbar) {
        TextView tv_title = toolbar.findViewById(R.id.tv_title);
        if (tv_title != null)
            tv_title.setText(title);
    }

    protected void setMyRightIcon(int resId, View.OnClickListener onClickListener, Toolbar toolbar) {
        ImageView iv_rigth = toolbar.findViewById(R.id.iv_right);
        iv_rigth.setImageResource(resId);
        if (iv_rigth != null)
            iv_rigth.setOnClickListener(onClickListener);
    }


    ProgressDialog dialog = null;

    public void dissmissDialog() {

        if (dialog != null) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    public void showDialog() {

        if (dialog == null)
            dialog = new ProgressDialog(this);

        if (dialog != null) {
            dialog.show();
        }

    }

    public void setTitle(String titile, boolean goSetting) {

        if (goSetting) {
            View iv_right = findViewById(R.id.iv_right);

            if (iv_right != null) {
                iv_right.setVisibility(View.VISIBLE);
                iv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goactivity(SplashSettingsActivity.class);
                    }
                });
            }
        }
        setTitle(titile);
    }

    public void setTitle(String titile) {
        TextView tv_title = findViewById(R.id.tv_title);
        if (tv_title != null)
            tv_title.setText(titile);

        View iv_left = findViewById(R.id.iv_left);
        if (iv_left != null)
            iv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
}
