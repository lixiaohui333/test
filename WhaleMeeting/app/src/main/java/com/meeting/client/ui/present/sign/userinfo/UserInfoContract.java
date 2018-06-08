package com.meeting.client.ui.present.sign.userinfo;

import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UserInfoContract {

    public interface NetView extends BaseNetView {
        void sueecess();
    }

    public interface Presenter extends BasePresenter<NetView> {
        void requestSign(String userId);
    }

}
