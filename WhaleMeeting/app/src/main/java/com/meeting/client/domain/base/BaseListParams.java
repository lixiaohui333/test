package com.meeting.client.domain.base;

import java.io.Serializable;

public class BaseListParams implements Serializable {

    private static final long serialVersionUID = 1L;

    public String rel;

    public int page;

    public BaseListParams(String rel, int page) {
        this.rel = rel;
        this.page = page;
    }
}
