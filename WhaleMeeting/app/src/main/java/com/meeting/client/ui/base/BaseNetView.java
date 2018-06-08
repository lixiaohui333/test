package com.meeting.client.ui.base;

import com.meeting.client.domain.base.BaseHR;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface BaseNetView {

    void hideProgress();

    void showProgress();

    void error();

    void apiError(BaseHR baseHR);
}
