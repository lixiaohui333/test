package com.meeting.client.ui.present.frame.home;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.MeetingItemDomain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */
public class HomePresent implements HomeContract.Presenter, LoadTaskCallBack<List<MeetingItemDomain>> {

    protected NetTaskModel netTask;

    protected HomeContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();


    public HomePresent() {
        LogHelper.i("SplashPresent init");
    }

    @Override
    public void onSuccess(List<MeetingItemDomain> data) {
        if (addview != null) {

            addview.setRecommendNew(data);
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
    public void bindTaskAndView(NetTaskModel netTask, HomeContract.NetView baseNetView) {
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


    @Override
    public void getHomeRecommendNew(String type) {
        Disposable disposable = netTask.execute(this, type);
        if (disposable != null)
            disposables.add(disposable);
    }
}
