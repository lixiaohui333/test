package com.meeting.client.ui.activity.sign;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.meeting.client.R;
import com.meeting.client.domain.base.BaseHR;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.domain.sign.UserInfoDomain;
import com.meeting.client.ui.activity.sign.aiface.DetectActivity;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.dialog.DialogHelper;
import com.meeting.client.ui.present.sign.SignContract;
import com.meeting.client.ui.present.sign.SignNetModel;
import com.meeting.client.ui.present.sign.SignPresent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignMainActivity extends BaseFragmentActivity<SignPresent, SignNetModel> implements SignContract.NetView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_sign_select)
    TextView tvSignSelect;
    @BindView(R.id.et_signcode)
    EditText etSigncode;

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {


        setContentView(R.layout.sign_activity_main);


    }

    @Override
    protected void initUI() {
        setTitle("签到", true);

        loadInitData();
    }

    @Override
    protected void setUI() {
    }

    @Override
    protected void loadInitData() {
        mPresenter.requestSignDetail();
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
    public void errorUserInfo() {
    }

    @Override
    public void apiErrorUser(BaseHR baseHR) {

    }

    @Override
    public void codeEmpty() {
        showToast("签到码不能为空");
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


    List<String> loactions = null;

    @Override
    public void resultSignDetail(List<String> loactions) {

        this.loactions = loactions;
        getLocationDialog();

    }

    @Override
    public void resultUserInfo(String name) {


        UserInfoDomain user = new UserInfoDomain();
        String headUrl = null;

        user.id = "1";
        user.company = "世纪阳天";
        user.signStatus = false;
        user.post = "CEO";
        user.name = "张三";
        user.phoneNum = "13000000000";
        user.head = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp";

        headUrl = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1606727862.webp";

        user.tempSignName = tvSignSelect.getText().toString();


        SignUserInfoActivity.goActivity(this, user, null);
    }


    AlertDialog locationDialog = null;
    boolean lactionFlag = false;

    private void getLocationDialog() {
        if (loactions == null)
            return;

        if (loactions.size() == 0) {
            showToast("还未设置签到点");
            finish();
        }

        locationDialog = DialogHelper.signLocationDialog(ct, loactions, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectName = loactions.get(position);
                tvSignSelect.setText(selectName);
                lactionFlag = true;

            }
        });

        locationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!lactionFlag)
                    finish();
            }
        });

    }

    @OnClick(R.id.tv_sign_select)
    public void onClickedSignSelect() {
        getLocationDialog();
    }

    @OnClick(R.id.iv_zxing)
    public void onClickedZxing() {


        if (!AndPermission.hasPermission(this, Manifest.permission.CAMERA)) {

            AndPermission.with(this).requestCode(100).permission(Manifest.permission.CAMERA).callback(new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    gostartQR();
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                }
            }).start();
        } else {
            gostartQR();
        }


    }

    private void gostartQR() {
//        Intent intent = new Intent(this, CaptureActivity.class);
//        startActivityForResult(intent, 0);
        goactivity(CaptureActivity.class);
    }


    @Override
    protected void getEventMessage(EventMessage message) {
        super.getEventMessage(message);

        mPresenter.requestSignUserInfo(message.text);

    }

    @OnClick(R.id.tv_sign_btn)
    public void onClickedSign() {

        String edtCode = etSigncode.getText().toString();
        mPresenter.requestSignUserInfo(edtCode);
    }

    @OnClick(R.id.iv_ai)
    public void onClickedAi() {

        if (!AndPermission.hasPermission(this, Manifest.permission.CAMERA)) {

            AndPermission.with(this).requestCode(100).permission(Manifest.permission.CAMERA).callback(new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    goactivity(DetectActivity.class);
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                }
            }).start();
        } else {
            goactivity(DetectActivity.class);
        }

    }

    @OnClick(R.id.ll_sign_userlist)
    public void onClickedUserList() {
        goactivity(SignMeetUserListActivity.class);
    }
}
