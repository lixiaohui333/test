package com.meeting.client.ui.present.frame.home;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.recommend.RecommendDomain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */
public class HomeRecommendPresent implements HomeRecommendContract.Presenter, LoadTaskCallBack<BaseHR<RecommendDomain>> {

    protected NetTaskModel netTask;

    protected HomeRecommendContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();

    private int pageIndex = 0;


    public HomeRecommendPresent() {
        LogHelper.i("HomeRecommendPresent init");
    }


    @Override
    public void onSuccess(BaseHR<RecommendDomain> data) {
        if (addview != null) {
            if (pageIndex == 0) {
                addview.setRecommendNew(data.data);
            } else {
                pageIndex = data.data.pageIndex;
                addview.setRecommendMore(data.data.list, data.data.pageIndex!=data.data.totalPage);
            }
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
    public void bindTaskAndView(NetTaskModel netTask, HomeRecommendContract.NetView baseNetView) {
        this.netTask = netTask;
        this.addview = baseNetView;

        LogHelper.i("InterviewPresent bindTaskAndView " + netTask + " " + baseNetView);
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
    public void getHomeRecommendNew() {
        pageIndex = 0;
        Disposable disposable = netTask.execute( this);
        disposables.add(disposable);

    }

    @Override
    public void getHomeRecommendMore() {

        Disposable disposable = netTask.execute( this);
        disposables.add(disposable);
    }
}
