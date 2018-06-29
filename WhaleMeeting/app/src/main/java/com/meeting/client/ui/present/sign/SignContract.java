package com.meeting.client.ui.present.sign;

import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.domain.home.SignUserItemDomain;
import com.meeting.client.ui.base.BaseNetView;
import com.meeting.client.ui.base.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SignContract {

    public interface NetView extends BaseNetView {

        void resultSignDetail(List<SignLocItemDomain> loactions);

        void resultUserInfo(SignUserItemDomain domain);

        void errorUserInfo();

        void apiErrorUser(BaseHR baseHR);

        void codeEmpty();

    }

    public interface Presenter extends BasePresenter<NetView> {
        void requestSignDetail(String meetingId);

        void requestSignUserInfo(String meetingId,String signLocId,String code);
    }

}
