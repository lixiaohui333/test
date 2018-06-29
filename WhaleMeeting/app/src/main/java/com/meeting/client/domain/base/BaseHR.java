package com.meeting.client.domain.base;

import java.io.Serializable;

public class BaseHR<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int HTTP_OK = 1;

    public int Status = 0;
    public String Message ;

    public long TimeStamp;

    public T Data;

    public BaseHR() {
    }


    @Override
    public String toString() {
        return "BaseHR{" +
                "Status=" + Status +
                ", Message=" + Message +
                ", TimeStamp=" + TimeStamp +
                ", Data=" + Data +
                '}';
    }
}
