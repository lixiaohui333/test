package com.meeting.client.ui.activity.logo;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.meeting.client.R;
import com.meeting.client.comm.Config;
import com.meeting.client.comm.util.SharedPreferencesTool;
import com.meeting.client.domain.eventbus.EventClose;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.base.UiUtil;
import com.meeting.client.ui.present.logo.settings.SettingsContract;
import com.meeting.client.ui.present.logo.settings.SettingsNetModel;
import com.meeting.client.ui.present.logo.settings.SettingsPresent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashSettingsActivity extends BaseFragmentActivity<SettingsPresent, SettingsNetModel> implements SettingsContract.NetView {




    @BindView(R.id.sw_suresign)
    Switch swSuresign;
    @BindView(R.id.sw_signprint)
    Switch swSignprint;
    @BindView(R.id.sw_collectphoto)
    Switch swCollectphoto;
    @BindView(R.id.sw_updatephoto)
    Switch swUpdatephoto;

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.logo_activity_settings);
    }

    @Override
    protected void initUI() {

        setTitle("设置");

        boolean is_suresign = SharedPreferencesTool.getSharedPreferences(UiUtil.getContext(), Config.SP_SURESIGN, false);
        boolean is_signprint = SharedPreferencesTool.getSharedPreferences(UiUtil.getContext(), Config.SP_SIGNPRINT, false);
        boolean is_collectphoto = SharedPreferencesTool.getSharedPreferences(UiUtil.getContext(), Config.SP_COLLECTPHOTO, false);
        boolean is_updatephoto = SharedPreferencesTool.getSharedPreferences(UiUtil.getContext(), Config.SP_UPDATEPHOTO, false);

        swSuresign.setChecked(is_suresign);
        swSignprint.setChecked(is_signprint);
        swCollectphoto.setChecked(is_collectphoto);
        swUpdatephoto.setChecked(is_updatephoto);

        swSuresign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferencesTool.setEditor(UiUtil.getContext(), Config.SP_SURESIGN, isChecked);

            }
        });
        swSignprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferencesTool.setEditor(UiUtil.getContext(), Config.SP_SIGNPRINT, isChecked);

            }
        });
        swCollectphoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesTool.setEditor(UiUtil.getContext(), Config.SP_COLLECTPHOTO, isChecked);
            }
        });
        swUpdatephoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesTool.setEditor(UiUtil.getContext(), Config.SP_UPDATEPHOTO, isChecked);
            }
        });



    }

    @Override
    protected void setUI() {
    }

    @Override
    protected void loadInitData() {
    }


    @Override
    public void hideProgress() {
        dissmissDialog();
    }

    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void error() {
        setErrorView(true);
    }


    private void setErrorView(boolean error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    public void resultLogout() {
        goactivity(SplashLoginActivity.class);
        sendEventBus(new EventMessage(SplashLoginActivity.class, EventClose.class.getName(), ""));
    }

    @OnClick(R.id.tv_login_btn)
    public void onClickedTvLoginBtn() {


        mPresenter.toLogout();
    }
}
