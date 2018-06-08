package com.meeting.client.business.net;

import com.meeting.client.domain.base.BaseDataList;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.recommend.RecommendDomain;
import com.meeting.client.domain.interview.InterviewItem;
import com.meeting.client.domain.logo.SplashLogoHR;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface ApiService {

    @GET("/api/api.interview.logo.splap")
    Observable<BaseHR<SplashLogoHR>> getSplash();


    @GET("/api/api.base")
    Observable<BaseHR> feedback();


    @GET("/api/api.interview.list.item")
    Observable<BaseHR<BaseDataList<InterviewItem>>> getTopic(String rel, String page);


    @GET("/api/api.home.recommend")
    Observable<BaseHR<RecommendDomain>> getHomeRecommend(@Query("page")String page);

}
