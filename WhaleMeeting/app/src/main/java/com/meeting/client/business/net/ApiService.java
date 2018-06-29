package com.meeting.client.business.net;

import com.meeting.client.domain.base.BaseDataList;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.MeetingItemDomain;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.domain.home.SignUserItemDomain;
import com.meeting.client.domain.home.recommend.RecommendDomain;
import com.meeting.client.domain.interview.InterviewItem;
import com.meeting.client.domain.logo.SplashLoginHR;
import com.meeting.client.domain.logo.SplashLogoHR;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Observable<BaseHR<RecommendDomain>> getHomeRecommend(@Query("page") String page);


    @FormUrlEncoded
    @POST("/api/v2/account/login")
    Observable<BaseHR<SplashLoginHR>> login(@FieldMap Map<String,String> body);


    @FormUrlEncoded
    @POST("/api/v2/signer/clientsigninchecked")
    Observable<BaseHR<List<SignUserItemDomain>>> signinchecked(@FieldMap Map<String,String> body);

    @GET("/api/v2/user/info")
    Observable<BaseHR<SplashLoginHR>> loginGetInfo();


    @GET("/api/v2/signer/queryusertomeeting")
    Observable<BaseHR<List<MeetingItemDomain>>> getMeetingList(@QueryMap  Map<String,String> params);

    @GET("/api/v2/signer/querysigneradministratorindrops")
    Observable<BaseHR<List<SignLocItemDomain>>> getSignLocList(@QueryMap  Map<String,String> params);

}
