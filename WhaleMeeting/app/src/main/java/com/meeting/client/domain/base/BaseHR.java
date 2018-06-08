package com.meeting.client.domain.base;

import java.io.Serializable;

public class BaseHR<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int HTTP_OK = 0;

    public int sysStatus = 0;

    public int apiStatus = 0;

    public String info;

    public long timestamp;

    public T data;

    public BaseHR() {
    }

    @Override
    public String toString() {
        return "BaseHR{" +
                "sysStatus=" + sysStatus +
                ", apiStatus=" + apiStatus +
                ", info='" + info + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
