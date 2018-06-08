package com.meeting.client.domain.logo;

import com.meeting.client.domain.base.BaseHR;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SplashLogoHR extends BaseHR {

    public int version;
    public String adUrl;
    public int splashDuration;

    @Override
    public String toString() {
        return "SplashLogoHR{" +
                "version=" + version +
                ", adUrl='" + adUrl + '\'' +
                ", splashDuration=" + splashDuration +
                '}';
    }
}
