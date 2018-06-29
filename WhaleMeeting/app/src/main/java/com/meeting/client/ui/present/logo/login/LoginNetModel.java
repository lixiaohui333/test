package com.meeting.client.ui.present.logo.login;

import com.meeting.client.business.net.ApiService;
import com.meeting.client.business.net.ApiServiceInstance;
import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.logo.SplashLoginHR;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/3/29.
 */

public class LoginNetModel implements NetTaskModel {


    private ApiService apiService = null;

    public LoginNetModel() {
        createRetrofit();
    }

    private void createRetrofit() {
        apiService = ApiServiceInstance.getInstance();
    }

    @Override
    public Disposable execute(final LoadTaskCallBack taskCallback, final String... params) {


        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("UserName", params[0]);
        bodyMap.put("Password", params[1]);
        bodyMap.put("ClientType", "3");
        bodyMap.put("grant_type", "password");


        Disposable disposable = apiService.login(bodyMap).flatMap(new Function<BaseHR<SplashLoginHR>, ObservableSource<BaseHR<SplashLoginHR>>>() {
            @Override
            public ObservableSource<BaseHR<SplashLoginHR>> apply(BaseHR<SplashLoginHR> hrBaseHR) throws Exception {


                return apiService.loginGetInfo();
            }
        }).map(new Function<BaseHR<SplashLoginHR>, BaseHR<SplashLoginHR>>() {
            @Override
            public BaseHR<SplashLoginHR> apply(BaseHR<SplashLoginHR> hrBaseHR) throws Exception {

                return hrBaseHR;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseHR<SplashLoginHR>>() {
                    @Override
                    public void accept(BaseHR<SplashLoginHR> hrBaseHR) throws Exception {
                        LogHelper.i(ApiServiceInstance.TAG, hrBaseHR.toString());

                        if (hrBaseHR.Status == BaseHR.HTTP_OK) {
                            taskCallback.onSuccess(hrBaseHR.Data);
                        } else {
                            taskCallback.onSysError(hrBaseHR);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (throwable instanceof HttpException) {
                            int code = ((HttpException) throwable).response().code();
                            LogHelper.i(ApiServiceInstance.TAG, "code:" + code);
                        }

                        taskCallback.onFailed();
                        LogHelper.i(ApiServiceInstance.TAG, "Consumer accept onFailed:" + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        taskCallback.onFinish();
                        LogHelper.i(ApiServiceInstance.TAG, "Action run onFinish");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        taskCallback.onStart();
                        LogHelper.i(ApiServiceInstance.TAG, "Consumer accept onStart");
                    }
                });


        return disposable;
    }
}
