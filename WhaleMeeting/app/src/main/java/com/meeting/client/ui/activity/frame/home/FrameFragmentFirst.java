package com.meeting.client.ui.activity.frame.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.meeting.client.R;
import com.meeting.client.comm.Config;
import com.meeting.client.comm.util.TimeTool;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.domain.home.MeetingItemDomain;
import com.meeting.client.ui.activity.sign.SignMainActivity;
import com.meeting.client.ui.base.BaseFragment;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.base.UiUtil;
import com.meeting.client.ui.present.frame.home.HomeContract;
import com.meeting.client.ui.present.frame.home.HomeNetModel;
import com.meeting.client.ui.present.frame.home.HomePresent;
import com.meeting.client.ui.view.pullload.CustomGifHeader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FrameFragmentFirst extends BaseFragment<HomePresent, HomeNetModel> implements HomeContract.NetView {


    @BindView(R.id.recycler)
    protected RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    protected XRefreshView xRefreshView;


    private Unbinder unbinder;


    public static final String STR_TYPE_PARAMS = "STR_TYPE_PARAMS";

    public static final int TYPE_ALL = 0;
    public static final int TYPE_ING = 1;
    public static final int TYPE_CLOSE = 2;
    private int mType = TYPE_ALL;

//    List<String> datas = null;

    SimpleAdapter adapter;


    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frame_home_fragment_layout, null);
    }


    //    MyRecyclerViewAdapter adapter =null;

    @Override
    protected void initUI() {


        mType = getArguments().getInt(STR_TYPE_PARAMS, 0);

        adapter = new SimpleAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setAutoLoadMore(false);

        xRefreshView.setCustomHeaderView(new CustomGifHeader(getContext()));
//        adapter.setCustomLoadMoreView(new NoMoreDataFooterView(getContext()));
//        xRefreshView.setPinnedTime(1000);
//        xRefreshView.setMoveForHorizontal(true);
//        xRefreshView.enableReleaseToLoadMore(false);
//        xRefreshView.enableRecyclerViewPullUp(true);
//        xRefreshView.enablePullUpWhenLoadCompleted(true);


//        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {

                mPresenter.getHomeRecommendNew(mType + "");
            }
        });

    }

    @Override
    protected void loadInitData() {
        xRefreshView.startRefresh();
    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();

        if (!firstLoadLazy) {
            loadInitData();
            firstLoadLazy = true;
        }

    }


    @Override
    protected void getEventMessage(EventMessage message) {
        super.getEventMessage(message);
        if (message.method.equals(mType + "")) {
            showToast("" + mType + ":" + message.text);
            xRefreshView.startRefresh();
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

    @Override
    public void hideProgress() {
        xRefreshView.stopRefresh();
        xRefreshView.setLoadComplete(false);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void error() {
    }

    @Override
    public void setRecommendNew(List<MeetingItemDomain> listMeeting) {

        adapter.setListMeeting(listMeeting);
    }

    public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {

        List<MeetingItemDomain> listMeeting;


        public void setListMeeting(List<MeetingItemDomain> listMeeting) {
            this.listMeeting = listMeeting;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(SimpleAdapterViewHolder holder, int position, boolean isItem) {
           final MeetingItemDomain itemDomain = listMeeting.get(position);


            holder.tvLocation.setText(itemDomain.Address);
            holder.tvActiveName.setText(itemDomain.Meeting_Name);



            holder.tvStartYear.setText(TimeTool.getTimeYMDcn(itemDomain.Begin_Date));
            holder.tvEndYear.setText(TimeTool.getTimeYMDcn(itemDomain.End_Date));

            holder.tvStartHour.setText(TimeTool.getTimeHM(itemDomain.Begin_Date));
            holder.tvEndHour.setText(TimeTool.getTimeHM(itemDomain.End_Date));

            holder.tvJoinPeople.setText(itemDomain.SignInCount+"");
            holder.tvTotalPeople.setText(itemDomain.Meeting_Enroll_Count+"");
            holder.tvPercentPeople.setText(itemDomain.SignInRate);

            holder.ivTopHead.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in =new Intent(UiUtil.getContext(),SignMainActivity.class);
                    in.putExtra(Config.EXTRA_DOMAIN,itemDomain);
                    BaseFragmentActivity.activity.goactivity(in);


                }
            });

        }

        @Override
        public int getAdapterItemViewType(int position) {
            return 0;
        }

        @Override
        public int getAdapterItemCount() {
            return listMeeting == null ? 0 : listMeeting.size();
        }

        @Override
        public SimpleAdapterViewHolder getViewHolder(View view) {
            return new SimpleAdapterViewHolder(view, false);
        }


        @Override
        public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_gv_active, parent, false);
            SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
            return vh;
        }

        public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_top_head)
            public ImageView ivTopHead;
            @BindView(R.id.tv_location)
            public TextView tvLocation;
            @BindView(R.id.cardview)
            public CardView cardview;
            @BindView(R.id.tv_sign_btn)
            public TextView tvSignBtn;

            @BindView(R.id.tv_active_name)
            public TextView tvActiveName;
            @BindView(R.id.tv_start_hour)
            public TextView tvStartHour;
            @BindView(R.id.tv_start_year)
            public TextView tvStartYear;
            @BindView(R.id.tv_end_hour)
            public TextView tvEndHour;
            @BindView(R.id.tv_end_year)
            public TextView tvEndYear;
            @BindView(R.id.tv_total_people)
            public TextView tvTotalPeople;
            @BindView(R.id.tv_join_people)
            public TextView tvJoinPeople;
            @BindView(R.id.tv_percent_people)
            public TextView tvPercentPeople;

            public int position;

            public SimpleAdapterViewHolder(View itemView, boolean isItem) {
                super(itemView);
                if (isItem) {

                    ButterKnife.bind(this, itemView);
                }

            }
        }

    }


}
