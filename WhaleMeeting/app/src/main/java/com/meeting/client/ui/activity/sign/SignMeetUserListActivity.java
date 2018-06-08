package com.meeting.client.ui.activity.sign;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.ui.activity.sign.userlist.SignUserListFragment;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.base.BaseViewPagerAdapter;
import com.meeting.client.ui.view.MyViewPager;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignMeetUserListActivity extends BaseFragmentActivity {


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
        setContentView(R.layout.sign_meet_userlist_activity);
    }

    @Override
    protected void initUI() {

        setTitle("参会人", true);

        BaseViewPagerAdapter viewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager());
        SignUserListFragment firstFragment = new SignUserListFragment();
        SignUserListFragment secondFragment = new SignUserListFragment();
        SignUserListFragment thirdFragment = new SignUserListFragment();

        firstFragment.addParams(SignUserListFragment.STR_TYPE_PARAMS, SignUserListFragment.TYPE_ALL);
        secondFragment.addParams(SignUserListFragment.STR_TYPE_PARAMS, SignUserListFragment.TYPE_ING);
        thirdFragment.addParams(SignUserListFragment.STR_TYPE_PARAMS, SignUserListFragment.TYPE_CLOSE);

        viewPagerAdapter.addFragment(firstFragment, "全部");//添加Fragment
        viewPagerAdapter.addFragment(secondFragment, "未签到");
        viewPagerAdapter.addFragment(thirdFragment, "已签到");

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

                    sendEventBus(new EventMessage(SignUserListFragment.class,viewpager.getCurrentItem()+"",v.getText().toString()));

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
                    sendEventBus(new EventMessage(SignUserListFragment.class,viewpager.getCurrentItem()+"",""));
                }
            }
        });

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,15,15);
            }
        });

    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


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

}
