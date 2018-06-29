//package com.meeting.client.ui.activity.logo;
//
//import com.meeting.client.R;
//import com.meeting.client.comm.LogHelper;
//import com.meeting.client.domain.logo.SplashLogoHR;
//import com.meeting.client.ui.base.BaseFragmentActivity;
//import com.meeting.client.ui.present.logo.splash.SplashContract;
//import com.meeting.client.ui.present.logo.splash.SplashNetModel;
//import com.meeting.client.ui.present.logo.splash.SplashPresent;
//
//public class SplashLogoActivity extends BaseFragmentActivity<SplashPresent,SplashNetModel> implements SplashContract.NetView {
//
//
//    @Override
//    protected boolean intentData() {
//        return true;
//    }
//
//    @Override
//    protected void initView() {
//        setContentView(R.layout.logo_activity_splash);
//    }
//
//    @Override
//    protected void initUI() {
//
//        //test
////        goactivity(FrameActivityMain.class);
////        finish();
////        loadInitData();
//
//        baseHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                goactivity(SplashLoginActivity.class);
//                finish();
//            }
//        }, 1000);
//    }
//
//    @Override
//    protected void setUI() {
//
//    }
//
//    @Override
//    protected void loadInitData() {
//
//        mPresenter.getSplashByNet();
//
//    }
//
//
//    @Override
//    public void hideProgress() {
//    }
//
//    @Override
//    public void showProgress() {
//    }
//
//    @Override
//    public void error() {
//        LogHelper.i(TAG + " error");
//    }
//
//
//    @Override
//    public void setSplash(SplashLogoHR splash) {
//    }
//
//
//
//}
