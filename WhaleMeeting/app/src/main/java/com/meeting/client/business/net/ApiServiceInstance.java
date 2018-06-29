package com.meeting.client.business.net;

import com.meeting.client.comm.LogHelper;
import com.meeting.client.ui.base.UiUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.meeting.client.comm.Config.HTTP_HOST;

/**
 * Created by Administrator on 2018/6/27.
 */

public class ApiServiceInstance {


    public static final String TAG = "Http2Service";
    public static final String OKTAG = "Ok2Service";
    private static ApiService apiService = null;

    public static ApiService getInstance() {

        if (apiService == null) {
            synchronized (ApiServiceInstance.class) {
                if (apiService == null) {

                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            LogHelper.w(OKTAG, message);
                        }
                    });
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new AddCookiesInterceptor(UiUtil.getContext())) //这部分
                            .addInterceptor(new ReceivedCookiesInterceptor(UiUtil.getContext())) //这部分
                            .addInterceptor(loggingInterceptor)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {

                                    Request originalRequest = chain.request();
                                    Request authorised = originalRequest.newBuilder()
                                            .header("Authorization", "Basic dGVzdDI6MTExMQ==")
                                            .build();
                                    return chain.proceed(authorised);
                                }
                            })
                            .build();

                    //route
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(HTTP_HOST)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                    apiService = retrofit.create(ApiService.class);
                }
            }
        }


        return apiService;
    }

}
