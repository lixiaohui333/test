package com.meeting.client.domain.base;

import java.io.Serializable;
import java.util.List;

public class BaseDataList<T> implements Serializable{

    private static final long serialVersionUID = 1L;


    public List<T> list;

    public int pageIndex;

    public int totalPage;

}
