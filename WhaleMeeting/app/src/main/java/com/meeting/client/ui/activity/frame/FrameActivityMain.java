package com.meeting.client.ui.activity.frame;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.meeting.client.R;
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

        setTitle("活动列表",true);

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
