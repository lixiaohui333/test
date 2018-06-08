package com.meeting.client.ui.present.frame.interview;

import com.meeting.client.domain.base.BaseDataList;
import com.meeting.client.domain.interview.InterviewItem;
import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

/**
 * Created by Administrator on 2018/3/29.
 */

public class InterviewContract {

    public interface NetView extends BaseNetView {
        void setInterviewList(BaseDataList<InterviewItem> dataList);
    }

    public interface Presenter extends BasePresenter<NetView> {

        void getInterviewList(String rel);

        void getInterviewListMore(String rel,int page);
    }

}
