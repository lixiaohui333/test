package com.meeting.client.business.net;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/27.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
//            Observable.from()
//                    .map(new Func1<String, String>() {
//                        @Override
//                        public String call(String s) {
//
//                            return cookieArray[0];
//                        }
//                    })
//                    .subscribe(new c);
            List<String> headers = originalResponse.headers("Set-Cookie");
            for (String s :
                    headers) {

                String[] cookieArray = s.split(";");
                if (cookieArray.length > 0) {
                    String s1 = cookieArray[0];

                    cookieBuffer.append(s1).append(";");
                }
            }

            //cookieBuffer.append(cookie).append(";");

            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}
