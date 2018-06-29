package com.meeting.client.domain.logo;

import com.meeting.client.domain.base.BaseHR;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SplashLoginHR extends BaseHR {

    public int Role_Type;
    public String User_Info_Id;

    public String Id;


    @Override
    public String toString() {
        return "SplashLoginHR{" +
                "Role_Type=" + Role_Type +
                ", User_Info_Id='" + User_Info_Id + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }
}
