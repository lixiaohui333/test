package com.meeting.client.ui.present.setting.about;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.domain.base.BaseHR;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */

public class AboutPresent implements AboutContract.Presenter, LoadTaskCallBack<String> {

    protected NetTaskModel netTask;

    protected AboutContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();


    @Override
    public void onSuccess(String info) {
        if (addview != null) {
            addview.setFeedbackSuccess(info);
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
    public void feedback(String feedInfo) {
        Disposable disposable = netTask.execute(this);
        disposables.add(disposable);
    }


    @Override
    public void bindTaskAndView(NetTaskModel netTask, AboutContract.NetView baseNetView) {
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
