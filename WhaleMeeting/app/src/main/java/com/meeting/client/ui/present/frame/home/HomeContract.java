package com.meeting.client.ui.present.frame.home;

import com.meeting.client.domain.home.MeetingItemDomain;
import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class HomeContract {

    public interface NetView extends BaseNetView {

        void setRecommendNew(List<MeetingItemDomain> listMeeting);

    }

    public interface Presenter extends BasePresenter<NetView> {

        void getHomeRecommendNew(String type);
    }

}
