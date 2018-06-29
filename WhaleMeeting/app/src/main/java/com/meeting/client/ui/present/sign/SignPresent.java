package com.meeting.client.ui.present.sign;

import android.text.TextUtils;

import com.meeting.client.business.net.NetTaskModel;
import com.meeting.client.comm.LogHelper;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.domain.home.SignUserItemDomain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/29.
 */
public class SignPresent implements SignContract.Presenter, SignLoadTaskCallBack {

    protected SignNetModel netTask;

    protected SignContract.NetView addview;

    protected List<Disposable> disposables = new ArrayList<>();


    public SignPresent() {
        LogHelper.i("SplashPresent init");
    }

    @Override
    public void onSuccess(List<SignLocItemDomain> datas) {
        if (addview != null) {
            addview.resultSignDetail(datas);
            addview.hideProgress();
        }
    }

    public void onSuccessUserInfo(SignUserItemDomain domain) {
        if (addview != null) {
            addview.resultUserInfo(domain);
            addview.hideProgress();
        }
    }

    @Override
    public void onFailedUser() {
        if (addview != null) {
            addview.errorUserInfo();
            addview.hideProgress();
        }
    }

    @Override
    public void onSysErrorUser(BaseHR baseHR) {
        if (addview != null) {
            addview.apiErrorUser(baseHR);
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
    public void bindTaskAndView(NetTaskModel netTask, SignContract.NetView baseNetView) {
        this.netTask = (SignNetModel) netTask;
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
    public void requestSignDetail(String meetingId) {
        Disposable disposable = netTask.execute(this,meetingId);
        disposables.add(disposable);
    }

    @Override
    public void requestSignUserInfo(String meetingId,String signLocId,String code) {

        if(TextUtils.isEmpty(code)){
            addview.codeEmpty();
        }else {

            Disposable disposable = netTask.executeUsercode(this,meetingId,signLocId, code);
            disposables.add(disposable);

        }
    }


}
