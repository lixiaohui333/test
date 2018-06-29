package com.meeting.client.domain.home;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MeetingItemDomain implements Serializable{

    public String Meeting_Name;
    public long Begin_Date;

    public long End_Date;

    public String Address;
    public String Id;


    public int Meeting_Enroll_Count;
    public int SignInCount;
    public String SignInRate;

    public MeetingItemDomain(String meeting_Name, long begin_Date, long end_Date, String address, String id, int meeting_Enroll_Count, int signInCount, String signInRate) {
        Meeting_Name = meeting_Name;
        Begin_Date = begin_Date;
        End_Date = end_Date;
        Address = address;
        Id = id;
        Meeting_Enroll_Count = meeting_Enroll_Count;
        SignInCount = signInCount;
        SignInRate = signInRate;
    }
}
