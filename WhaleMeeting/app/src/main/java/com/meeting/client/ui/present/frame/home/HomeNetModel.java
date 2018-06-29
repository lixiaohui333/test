package com.meeting.client.ui.present.frame.home;

import com.meeting.client.business.net.ApiService;
import com.meeting.client.business.net.ApiServiceInstance;
import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.comm.MyViewTool;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.MeetingItemDomain;
import com.meeting.client.domain.logo.SplashLoginHR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/3/29.
 */

public class HomeNetModel implements NetTaskModel {


    @Override
    public Disposable execute(final LoadTaskCallBack taskCallback, String... params) {


        SplashLoginHR loginHR = MyViewTool.getLoginHR();
        if (loginHR == null) {
            taskCallback.onFailed();
            return null;
        }


        ApiService apiService = ApiServiceInstance.getInstance();
        //userInfoId={userInfoId}&roleType={roleType}&meeting_status={meeting_status}
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userInfoId", loginHR.Id);
        paramsMap.put("roleType", loginHR.Role_Type + "");
        paramsMap.put("meeting_status", params[0]);

        Disposable disposable = apiService.getMeetingList(paramsMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseHR<List<MeetingItemDomain>>>() {
                    @Override
                    public void accept(BaseHR<List<MeetingItemDomain>> hrBaseHR) throws Exception {
                        if(hrBaseHR.Status==BaseHR.HTTP_OK) {
                            taskCallback.onSuccess(hrBaseHR.Data);
                        }else{
                            taskCallback.onSysError(hrBaseHR);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        taskCallback.onFailed();
                        if (throwable instanceof HttpException) {
                            int code = ((HttpException) throwable).response().code();
                            LogHelper.i(ApiServiceInstance.TAG, "Consumer HttpException code:" + code);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LogHelper.i(ApiServiceInstance.TAG, "Consumer accept Action");
                        taskCallback.onFinish();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LogHelper.i(ApiServiceInstance.TAG, "Consumer accept onStart");
                        taskCallback.onStart();
                    }
                });


        return disposable;
    }
}
