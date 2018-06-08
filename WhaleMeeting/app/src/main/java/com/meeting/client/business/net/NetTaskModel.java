package com.meeting.client.business.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface NetTaskModel {

    Disposable  execute(LoadTaskCallBack callBack,String... params);
}
