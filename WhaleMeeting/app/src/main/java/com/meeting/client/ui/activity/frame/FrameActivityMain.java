package com.meeting.client.ui.activity.frame;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.ui.activity.frame.home.FrameFragmentFirst;
import com.meeting.client.ui.activity.logo.SplashSettingsActivity;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.base.BaseViewPagerAdapter;
import com.meeting.client.ui.view.MyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrameActivityMain extends BaseFragmentActivity {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    MyViewPager viewpager;

    @BindView(R.id.tv_search)
    EditText tvSearch;

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.frame_activity_main);
    }

    @Override
    protected void initUI() {

        setTitle("活动列表", true);

        BaseViewPagerAdapter viewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager());
        FrameFragmentFirst firstFragment = new FrameFragmentFirst();
        FrameFragmentFirst secondFragment = new FrameFragmentFirst();
        FrameFragmentFirst thirdFragment = new FrameFragmentFirst();
        firstFragment.addParams(FrameFragmentFirst.STR_TYPE_PARAMS, FrameFragmentFirst.TYPE_ALL);
        secondFragment.addParams(FrameFragmentFirst.STR_TYPE_PARAMS, FrameFragmentFirst.TYPE_ING);
        thirdFragment.addParams(FrameFragmentFirst.STR_TYPE_PARAMS, FrameFragmentFirst.TYPE_CLOSE);

        viewPagerAdapter.addFragment(firstFragment, "全部");//添加Fragment
        viewPagerAdapter.addFragment(secondFragment, "进行中");
        viewPagerAdapter.addFragment(thirdFragment, "已结束");

        viewpager.setSlideable(false);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(viewPagerAdapter);


        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("已结束"));
        tabLayout.setupWithViewPager(viewpager);

        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == 0 || actionId == 3) && event != null) {

                    sendEventBus(new EventMessage(FrameFragmentFirst.class,viewpager.getCurrentItem()+"",v.getText().toString()));

                    return true;
                }

                return false;
            }
        });

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if(TextUtils.isEmpty(s1)){
                    sendEventBus(new EventMessage(FrameFragmentFirst.class,viewpager.getCurrentItem()+"",""));
                }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                goactivity(SplashSettingsActivity.class);
                break;
        }
    }
}
