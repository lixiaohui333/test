package com.meeting.client.comm;

import com.meeting.client.domain.logo.SplashLoginHR;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Config {

    public static boolean isLog = true;


    public final static String HTTP_HOST = "http://192.168.34.114:1836/";

    public static SplashLoginHR LOGINHR = null;


    public final static String ALIPAY_PACKAGENAME = "com.eg.android.AlipayGphone";


    public static final String SP_SURESIGN = "SP_SURESIGN";


    public static final String SP_SIGNPRINT = "SP_SIGNPRINT";
    public static final String SP_COLLECTPHOTO = "SP_COLLECTPHOTO";
    public static final String SP_UPDATEPHOTO = "SP_UPDATEPHOTO";


    public static final String EXTRA_DOMAIN = "EXTRA_DOMAIN";
    public static final String EXTRA_STRING = "EXTRA_STRING";


    // 为了apiKey,secretKey为您调用百度人脸在线接口的，如注册，识别等。
    // 为了的安全，建议放在您的服务端，端把人脸传给服务器，在服务端端进行人脸注册、识别放在示例里面是为了您快速看到效果
    public static String apiKey = "GcUfMjFoXae7Mx2UU4WyG7wP";
    public static String secretKey = "HUs8Z9tPHUkOPSC6mzyaSbdAakwMsXDy";
    //com-meeting-client-face-android
    public static String licenseID = "com-meeting-client-face-android";
    public static String licenseFileName = "idl-license.face-android";


    public static final String SP_LOGINHR = "SP_LOGINHR";


}
