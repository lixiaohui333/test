package com.meeting.client.ui.present.sign;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.domain.home.SignUserItemDomain;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface SignLoadTaskCallBack extends LoadTaskCallBack<List<SignLocItemDomain>> {

//    void onSuccess(List<String> data);

    void onSuccessUserInfo(SignUserItemDomain data);

    void onFailedUser();

    void onSysErrorUser(BaseHR baseHR);
}
