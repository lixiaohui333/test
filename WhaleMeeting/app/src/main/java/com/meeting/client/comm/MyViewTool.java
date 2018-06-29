package com.meeting.client.comm;

import com.meeting.client.comm.util.GsonUtil;
import com.meeting.client.comm.util.SharedPreferencesTool;
import com.meeting.client.domain.logo.SplashLoginHR;
import com.meeting.client.ui.base.UiUtil;

/**
 * Created by Administrator on 2018/6/28.
 */

public class MyViewTool {

    public static SplashLoginHR getLoginHR() {
        if (Config.LOGINHR == null) {
            String json = SharedPreferencesTool.getSharedPreferences(UiUtil.getContext(), Config.SP_LOGINHR, "");
            Config.LOGINHR = GsonUtil.toDomain(json,SplashLoginHR.class);
        }
        return Config.LOGINHR;
    }

    public static void setLoginHR( SplashLoginHR loginHR){
        Config.LOGINHR = loginHR;
        SharedPreferencesTool.setEditor(UiUtil.getContext(),Config.SP_LOGINHR,GsonUtil.toJson(loginHR));
    }
}
