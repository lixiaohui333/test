package com.meeting.client.ui.present.sign.userinfo;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.meeting.client.comm.Config.HTTP_HOST;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UserInfoNetModel implements NetTaskModel {

    public static final String HOST = HTTP_HOST;

    public UserInfoNetModel() {
    }

    private Retrofit retrofit;

    private void createRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Override
    public Disposable execute(final LoadTaskCallBack taskCallback,final String... params) {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                String userId = params[0];
                LogHelper.i("username:"+userId);
                Thread.sleep(1000);
                if("1".equals(userId)){
                    emitter.onNext(true);
                }else{
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean islogin) throws Exception {
                        LogHelper.i("accept:islogin:"+islogin);
                        if (islogin) {
                            taskCallback.onSuccess(null);
                        } else {
                            taskCallback.onSysError(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        taskCallback.onFailed();
                        LogHelper.i("Consumer accept onFailed:"+throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        taskCallback.onFinish();
                        LogHelper.i("Action run onFinish");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        taskCallback.onStart();
                        LogHelper.i("Consumer accept onStart");
                    }
                });

        return disposable;
    }
}
