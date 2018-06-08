package com.meeting.client.android.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.aip.FaceConfig;
import com.baidu.aip.FaceEnvironment;
import com.baidu.aip.FaceSDKManager;
import com.facebook.stetho.Stetho;
import com.meeting.client.comm.Config;

/**
 * Created by lxh on 2018/3/29.
 */

public class MyApplication extends Application {


    private static MyApplication instance;
    //by lxh
    private static int mainTid;
    private static Handler handler;



    public static MyApplication getInstance() {
        return instance;
    }

    public static Handler getHandler() {
        return handler;
    }

    public Context getContext() {
        return getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        instance = this;

        mainTid = android.os.Process.myTid();
        handler = new Handler();

        initLib();
    }

    /**
     * 初始化SDK
     */
    private void initLib() {
        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
        setFaceConfig();
    }

    private void setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整

        // 模糊度范围 (0-1) 推荐小于0.7
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        // 光照范围 (0-1) 推荐大于40
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        // 裁剪人脸大小
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        // 人脸yaw,pitch,row 角度，范围（-45，45），推荐-15-15
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);

        // 最小检测人脸（在图片人脸能够被检测到最小值）80-200， 越小越耗性能，推荐120-200
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        //
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 人脸遮挡范围 （0-1） 推荐小于0.5
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        // 是否进行质量检测
        config.setCheckFaceQuality(true);
        // 人脸检测使用线程数
        config.setFaceDecodeNumberOfThreads(4);
        config.setVerifyLive(false);
        // 是否开启提示音
        config.setSound(false);

        FaceSDKManager.getInstance().setFaceConfig(config);

    }
}
