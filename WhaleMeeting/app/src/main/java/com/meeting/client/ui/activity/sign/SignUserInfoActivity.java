package com.meeting.client.ui.activity.sign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.business.gilde.GlideUtil;
import com.meeting.client.comm.Config;
import com.meeting.client.comm.util.TimeTool;
import com.meeting.client.domain.sign.SignLocationDomain;
import com.meeting.client.domain.sign.UserInfoDomain;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.present.sign.userinfo.UserInfoContract;
import com.meeting.client.ui.present.sign.userinfo.UserInfoNetModel;
import com.meeting.client.ui.present.sign.userinfo.UserinfoPresent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUserInfoActivity extends BaseFragmentActivity<UserinfoPresent, UserInfoNetModel> implements UserInfoContract.NetView {


    UserInfoDomain userInfoDomain = null;
    String headUrl = null;
    @BindView(R.id.iv_head_service)
    ImageView ivHeadService;
    @BindView(R.id.iv_head_local)
    ImageView ivHeadLocal;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_sign_btn)
    TextView tvSignBtn;
    @BindView(R.id.tv_sign_status)
    TextView tvSignStatus;
    @BindView(R.id.ll_sign_main)
    LinearLayout llSignMain;

    @Override
    protected boolean intentData() {
        Intent preIntent = getIntent();
        userInfoDomain = (UserInfoDomain) preIntent.getSerializableExtra(Config.EXTRA_DOMAIN);

        headUrl = preIntent.getStringExtra(Config.EXTRA_STRING);

        if (userInfoDomain == null) {

            finish();
            return false;
        }

        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.sign_activity_userinfo);
    }

    @Override
    protected void initUI() {
        setTitle("签到", true);

        setSignBtnView();

        if (headUrl == null) {
            ivHeadLocal.setVisibility(View.GONE);
        } else {
            ivHeadLocal.setVisibility(View.VISIBLE);
            GlideUtil.displayCircle(ivHeadLocal, headUrl);
        }

        GlideUtil.displayCircle(ivHeadService, userInfoDomain.head);


        tvCompany.setText(userInfoDomain.company);

        tvName.setText(userInfoDomain.name);

        tvPost.setText(userInfoDomain.post);

        tvPhonenum.setText(userInfoDomain.phoneNum);

        setSignLocationView(userInfoDomain.locations);
    }

    @Override
    protected void setUI() {
    }

    @Override
    protected void loadInitData() {
        mPresenter.requestSign("");
    }


    @Override
    public void sueecess() {
        showToast("签到成功");

        userInfoDomain.signStatus = true;


        List<SignLocationDomain> list = new ArrayList<>();
        list.add(new SignLocationDomain(userInfoDomain.tempSignName, System.currentTimeMillis()));
        list.add(new SignLocationDomain(userInfoDomain.tempSignName, System.currentTimeMillis()));
        userInfoDomain.locations = list;


        setSignBtnView();

        setSignLocationView(userInfoDomain.locations);


    }

    private void setSignBtnView() {

        if (userInfoDomain.signStatus) {

            tvSignStatus.setText("该用户已签到");
            tvSignStatus.setBackgroundColor(Color.parseColor("#67CC48"));

            if (headUrl == null) {
                tvSignBtn.setText("采集人像");
            } else {
                tvSignBtn.setVisibility(View.GONE);
            }

        } else {
            tvSignStatus.setText("该用户未签到");
            tvSignStatus.setBackgroundColor(Color.parseColor("#FFA634"));
            if (headUrl == null) {
                tvSignBtn.setText("确认签到并采集人像");
            } else {
                tvSignBtn.setText("确认签到");
            }
        }
    }

    private void setSignLocationView(List<SignLocationDomain> locations) {
        if (locations == null || locations.size() == 0) {
            llSignMain.setVisibility(View.GONE);
            return;
        }

        llSignMain.setVisibility(View.VISIBLE);

        View itemView = null;
        TextView tvName = null;
        TextView tvTime = null;
        for (SignLocationDomain domain : locations) {
            itemView = View.inflate(ct, R.layout.sign_user_item_signtime, null);

            tvName = itemView.findViewById(R.id.tv_location);
            tvTime = itemView.findViewById(R.id.tv_time);

            tvName.setText(domain.location);
            tvTime.setText(TimeTool.getTimeMM(domain.time));

            llSignMain.addView(itemView);
        }

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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public static void goActivity(Context ct, UserInfoDomain userInfoDomain, String head) {
        Intent intent = new Intent(ct, SignUserInfoActivity.class);
        intent.putExtra(Config.EXTRA_DOMAIN, userInfoDomain);
        if (!TextUtils.isEmpty(head)) {
            intent.putExtra(Config.EXTRA_STRING, head);
        }
        ct.startActivity(intent);
    }

    @OnClick(R.id.tv_sign_btn)
    public void onClickedSignBtn() {
        if (userInfoDomain.signStatus) {
            //TODO 采集人像

        } else {
            if (headUrl == null) {
                //TODO 跳转到人脸识别


            } else {
                mPresenter.requestSign(userInfoDomain.id);
            }

        }

    }
}


