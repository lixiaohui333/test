package com.meeting.client.ui.activity.frame.home;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.meeting.client.R;
import com.meeting.client.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FrameFragmentFirst extends BaseFragment {


//    @BindView(R.id.recycler_view)
//    protected RecyclerView recyclerView;

    @BindView(R.id.lv)
    protected ListView lv;
    @BindView(R.id.refresh_layout)
    protected XRefreshView refreshView;
    private Unbinder unbinder;


    public static final String STR_TYPE_PARAMS = "STR_TYPE_PARAMS";

    public static final int TYPE_ALL = 0;
    public static final int TYPE_ING = 1;
    public static final int TYPE_CLOSE = 2;
    private int mType = TYPE_ALL;

    private List<String> dataList = null;

    public static long lastRefreshTime;


    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frame_home_fragment_layout, null);
    }

    //    MyRecyclerViewAdapter adapter =null;

    @Override
    protected void initUI() {



    }

    @Override
    protected void loadInitData() {



    }

    private void getDataList(int size) {

        dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataList.add("" + SystemClock.uptimeMillis());
        }

    }

    private void addDataList(int size) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        for (int i = 0; i < size; i++) {
            dataList.add("" + SystemClock.uptimeMillis());
        }
    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();

        if (!firstLoadLazy) {

//            xRefreshView.setRefreshing(true);
            loadInitData();

            firstLoadLazy = true;
        }

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
