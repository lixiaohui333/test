package com.meeting.client.ui.present.frame.home;

import com.meeting.client.domain.home.recommend.RecommendDomain;
import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class HomeRecommendContract {

    public interface NetView extends BaseNetView {

        void setRecommendNew(RecommendDomain recommendDomain);

        void setRecommendMore(List<RecommendDomain.NewsListBean> dataList,boolean hasMore);
    }

    public interface Presenter extends BasePresenter<NetView> {

        void getHomeRecommendNew();

        void getHomeRecommendMore();
    }

}
