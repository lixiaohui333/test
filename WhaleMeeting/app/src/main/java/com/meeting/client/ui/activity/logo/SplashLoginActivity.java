package com.meeting.client.ui.activity.logo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.comm.MyViewTool;
import com.meeting.client.domain.logo.SplashLoginHR;
import com.meeting.client.ui.activity.frame.FrameActivityMain;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.present.logo.login.LoginContract;
import com.meeting.client.ui.present.logo.login.LoginNetModel;
import com.meeting.client.ui.present.logo.login.LoginPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashLoginActivity extends BaseFragmentActivity<LoginPresent, LoginNetModel> implements LoginContract.NetView {


    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_passwd)
    EditText etPasswd;

    @BindView(R.id.tv_login_btn)
    TextView tvLoginBtn;

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.logo_activity_login);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void setUI() {
    }

    @Override
    protected void loadInitData() {
    }


    @Override
    public void hideProgress() {
        dissmissDialog();
    }

    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void error() {

    }

    @Override
    public void setUserInfo(SplashLoginHR userInfo) {

        MyViewTool.setLoginHR(userInfo);
        goactivity(FrameActivityMain.class);
        finish();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login_btn)
    public void onLoginBtnClicked() {
        String strPwd = etPasswd.getText().toString().trim();
        String strUsername = etUsername.getText().toString().trim();


        if (TextUtils.isEmpty(strUsername)) {
            showToast("请输入账号");
            return;
        }

        if (TextUtils.isEmpty(strPwd)) {
            showToast("请输入密码");
            return;
        }
        mPresenter.toLogin(strUsername, strPwd);
    }



}
