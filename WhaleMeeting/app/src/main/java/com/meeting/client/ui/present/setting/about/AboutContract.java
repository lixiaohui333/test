package com.meeting.client.ui.present.setting.about;

import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

/**
 * Created by Administrator on 2018/3/29.
 */

public class AboutContract {

    public interface NetView extends BaseNetView {

        void setFeedbackSuccess(String info);

    }

    public interface Presenter extends BasePresenter<AboutContract.NetView>{
        void feedback(String feedInfo);
    }
}
