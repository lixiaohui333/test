package com.meeting.client.ui.activity.logo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.domain.logo.SplashLogoHR;
import com.meeting.client.ui.activity.frame.FrameActivityMain;
import com.meeting.client.ui.activity.frame.home.ListActivityMain;
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
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.et_passwd)
    EditText etPasswd;
    @BindView(R.id.tv_login_error)
    TextView tvLoginError;
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

        goactivity(ListActivityMain.class);


        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(errorEdit)
                    setErrorView(false);
            }
        });
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

    boolean errorEdit = false;

    @Override
    public void error() {
        setErrorView(true);
    }

    @Override
    public void setUserInfo(SplashLogoHR userInfo) {

        goactivity(FrameActivityMain.class);
        finish();
        showToast("success");
    }


    private void setErrorView(boolean error) {

        if (error) {
            errorEdit = true;
            tvLoginError.setVisibility(View.VISIBLE);
            llUsername.setBackgroundResource(R.drawable.shape_login_edit_cir_error);
        } else {
            errorEdit = false;
            tvLoginError.setVisibility(View.INVISIBLE);
            llUsername.setBackgroundResource(R.drawable.shape_login_edit_cir_grey);
        }
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
        mPresenter.toLogin(strUsername, strPwd);
    }


}
