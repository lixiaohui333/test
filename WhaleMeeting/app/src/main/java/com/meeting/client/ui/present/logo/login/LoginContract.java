package com.meeting.client.ui.present.logo.login;

import com.meeting.client.domain.logo.SplashLogoHR;
import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

/**
 * Created by Administrator on 2018/3/29.
 */

public class LoginContract {

    public interface NetView extends BaseNetView {

        void setUserInfo(SplashLogoHR userInfo);

    }

    public interface Presenter extends BasePresenter<NetView> {
        void toLogin(String username,String passwd);
    }

}
