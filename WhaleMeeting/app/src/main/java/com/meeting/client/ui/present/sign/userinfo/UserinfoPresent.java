package com.meeting.client.ui.present.sign.userinfo;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseHR;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */
public class UserinfoPresent implements UserInfoContract.Presenter, LoadTaskCallBack<String> {

    protected NetTaskModel netTask;

    protected UserInfoContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();


    public UserinfoPresent() {
        LogHelper.i("SplashPresent init");
    }

    @Override
    public void onSuccess(String data) {
        if (addview != null) {

            addview.sueecess();
            addview.hideProgress();
        }

    }

    @Override
    public void onFinish() {
        if (addview != null) {
            addview.hideProgress();
        }
    }

    @Override
    public void onFailed() {
        if (addview != null) {
            addview.error();
            addview.hideProgress();
        }
    }

    @Override
    public void onSysError(BaseHR baseHR) {
        if (addview != null) {
            addview.apiError(baseHR);
        }
    }

    @Override
    public void onStart() {
        if (addview != null) {
            addview.showProgress();
        }
    }

    @Override
    public void requestSign(String userId) {
        Disposable disposable = netTask.execute(this,userId);
        disposables.add(disposable);
    }


    @Override
    public void bindTaskAndView(NetTaskModel netTask, UserInfoContract.NetView baseNetView) {
        this.netTask = netTask;
        this.addview = baseNetView;
    }

    @Override
    public void onDestroy() {
        for (Disposable disposable :
                disposables) {
            if (!disposable.isDisposed())
                disposable.dispose();
        }
        disposables.clear();
        addview = null;
        netTask = null;
    }




}
