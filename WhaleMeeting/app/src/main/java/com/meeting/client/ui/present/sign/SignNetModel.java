package com.meeting.client.ui.present.sign;

import com.meeting.client.business.net.ApiService;
import com.meeting.client.business.net.ApiServiceInstance;
import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.comm.MyViewTool;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.domain.home.SignUserItemDomain;
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

public class SignNetModel implements NetTaskModel {

    public SignNetModel() {
    }


    public Disposable executeUsercode(final SignLoadTaskCallBack taskCallback, final String... params) {

//signLocId

        SplashLoginHR loginHR = MyViewTool.getLoginHR();
        if (loginHR == null) {
            taskCallback.onFailed();
            return null;
        }

        ApiService apiService = ApiServiceInstance.getInstance();
        //userInfoId={userInfoId}&roleType={roleType}&meeting_status={meeting_status}
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("signerAdministratorId", loginHR.Id);
        paramsMap.put("role", loginHR.Role_Type + "");

        paramsMap.put("SignType", "2");

        paramsMap.put("MeetingId", params[0]);
        paramsMap.put("Signer_Drop_Id", params[1]);
        paramsMap.put("KeyWord", params[2]);


        Disposable disposable = apiService.signinchecked(paramsMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseHR<List<SignUserItemDomain>>>() {
                    @Override
                    public void accept(BaseHR<List<SignUserItemDomain>> hrBaseHR) throws Exception {
                        if (hrBaseHR.Status == BaseHR.HTTP_OK && hrBaseHR.Data.size() > 0) {
                            taskCallback.onSuccessUserInfo(hrBaseHR.Data.get(0));
                        } else {
                            taskCallback.onSysErrorUser(hrBaseHR);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        taskCallback.onFailedUser();
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

    @Override
    public Disposable execute(final LoadTaskCallBack taskCallback, final String... params) {

        SplashLoginHR loginHR = MyViewTool.getLoginHR();
        if (loginHR == null) {
            taskCallback.onFailed();
            return null;
        }

        ApiService apiService = ApiServiceInstance.getInstance();
        //userInfoId={userInfoId}&roleType={roleType}&meeting_status={meeting_status}
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("signerAdministratorId", loginHR.Id);
        paramsMap.put("role", loginHR.Role_Type + "");
        paramsMap.put("meeting_Id", params[0]);


        Disposable disposable = apiService.getSignLocList(paramsMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseHR<List<SignLocItemDomain>>>() {
                    @Override
                    public void accept(BaseHR<List<SignLocItemDomain>> hrBaseHR) throws Exception {
                        if (hrBaseHR.Status == BaseHR.HTTP_OK) {
                            taskCallback.onSuccess(hrBaseHR.Data);
                        } else {
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
