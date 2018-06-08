package com.meeting.client.ui.present.logo.settings;

import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SettingsContract {

    public interface NetView extends BaseNetView {

        void resultLogout();

    }

    public interface Presenter extends BasePresenter<NetView> {
        void toLogout();
    }

}
