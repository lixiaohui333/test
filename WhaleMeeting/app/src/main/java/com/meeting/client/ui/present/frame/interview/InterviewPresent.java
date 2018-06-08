package com.meeting.client.ui.present.frame.interview;

import com.meeting.client.business.net.LoadTaskCallBack;
import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseDataList;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.interview.InterviewItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */
public class InterviewPresent implements InterviewContract.Presenter, LoadTaskCallBack<BaseDataList<InterviewItem>> {

    protected NetTaskModel netTask;

    protected InterviewContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();


    public InterviewPresent() {
        LogHelper.i("InterviewPresent init");
    }


    @Override
    public void onSuccess(BaseDataList<InterviewItem> data) {
        if (addview != null) {
            addview.setInterviewList(data);
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
    public void bindTaskAndView(NetTaskModel netTask, InterviewContract.NetView baseNetView) {
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
    public void getInterviewList(String rel) {
        getInterviewListMore(rel, 0);
    }

    @Override
    public void getInterviewListMore(String rel, int page) {
        Disposable disposable = netTask.execute(this);
        disposables.add(disposable);
    }
}
