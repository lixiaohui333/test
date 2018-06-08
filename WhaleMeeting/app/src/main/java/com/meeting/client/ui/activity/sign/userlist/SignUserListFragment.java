package com.meeting.client.ui.activity.sign.userlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.meeting.client.R;
import com.meeting.client.business.gilde.GlideApp;
import com.meeting.client.business.gilde.GlideRoundTransform;
import com.meeting.client.domain.eventbus.EventMessage;
import com.meeting.client.ui.base.BaseFragment;
import com.meeting.client.ui.view.pullload.CustomGifHeader;
import com.meeting.client.ui.view.pullload.NoMoreDataFooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/13.
 */

public class SignUserListFragment extends BaseFragment {


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

    List<String> datas = null;

    SimpleAdapter adapter;


    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frame_home_fragment_layout, null);
    }


    //    MyRecyclerViewAdapter adapter =null;

    @Override
    protected void initUI() {


        mType = getArguments().getInt(STR_TYPE_PARAMS, 0);

        initdata(0);

        adapter = new SimpleAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setAdapter(adapter);

        xRefreshView.setPullLoadEnable(true);


        xRefreshView.setAutoLoadMore(true);

        xRefreshView.setCustomHeaderView(new CustomGifHeader(getContext()));


        adapter.setCustomLoadMoreView(new NoMoreDataFooterView(getContext()));

        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);

        xRefreshView.enableReleaseToLoadMore(false);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);

        recyclerView.getItemAnimator().setChangeDuration(300);
        recyclerView.getItemAnimator().setMoveDuration(300);

        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        initdata(10);
                        adapter.notifyDataSetChanged();

                        xRefreshView.stopRefresh();
                        xRefreshView.setLoadComplete(false);

                    }
                }, 500);


            }

            @Override
            public void onLoadMore(boolean isSilence) {
                Log.i(TAG, "onLoadMore: " + isSilence);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (datas.size() > 20) {
                            xRefreshView.stopLoadMore(false);
                        } else {
                            int index = datas.size();
                            adddata(5);
                            adapter.notifyDataSetChanged();
                            xRefreshView.stopLoadMore();
                        }
                    }
                }, 2000);

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

//            xRefreshView.setRefreshing(true);
            loadInitData();

            firstLoadLazy = true;
        }

    }

    private void initdata(int size) {
        if (datas == null) {
            datas = new ArrayList<>();
        }

        datas.clear();

        for (int i = 0; i < size; i++) {
            datas.add("i=" + i);
        }
    }

    private void adddata(int size) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        for (int i = 0; i < size; i++) {
            datas.add("i=" + i);
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

    public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {

        private int opened = -1;


        @Override
        public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {
            String s = datas.get(position);
            holder.tvNickname.setText("name:" + s);

            holder.ivTopHead.setVisibility(position == 0 ? View.VISIBLE : View.GONE);



            holder.bindView(position);

            GlideApp.with(ct).load("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1910900710.jpg").transform(new GlideRoundTransform(ct)).into(holder.ivHead);

        }



        @Override
        public int getAdapterItemViewType(int position) {
            return 0;
        }

        @Override
        public int getAdapterItemCount() {
            return datas.size();
        }

        @Override
        public SimpleAdapterViewHolder getViewHolder(View view) {
            return new SimpleAdapterViewHolder(view, false);
        }


        @Override
        public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_sign_userlist, parent, false);
            SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
            return vh;
        }

        public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

            @BindView(R.id.iv_top_head)
            ImageView ivTopHead;
            @BindView(R.id.tv_nickname)
            TextView tvNickname;
            @BindView(R.id.tv_username)
            TextView tvUsername;
            @BindView(R.id.iv_call_phone)
            ImageView ivCallPhone;
            @BindView(R.id.iv_head)
            ImageView ivHead;
            @BindView(R.id.tv_phonenum)
            TextView tvPhonenum;
            @BindView(R.id.tv_company)
            TextView tvCompany;
            @BindView(R.id.tv_post)
            TextView tvPost;
            @BindView(R.id.ll_bootom)
            LinearLayout llBootom;
            @BindView(R.id.cardview)
            CardView cardview;
            @BindView(R.id.iv_arrow)
            ImageView ivArrow;

            public int position;

            public SimpleAdapterViewHolder(View itemView, boolean isItem) {
                super(itemView);
                if (isItem) {

                    ButterKnife.bind(this, itemView);
                    cardview.setOnClickListener(this);
                }

            }

            /**
             * 此方法实现列表数据的绑定和item的展开/关闭
             */
            void bindView(int pos) {
                if (pos == opened){
                    llBootom.setVisibility(View.VISIBLE);
                    ivArrow.setImageResource(R.drawable.ic_sign_up);

                } else{
                    llBootom.setVisibility(View.GONE);
                    ivArrow.setImageResource(R.drawable.ic_sign_down);
                }
            }
            /**
             * item的点击事件
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (opened == getAdapterPosition()) {
                    //当点击的item已经被展开了, 就关闭.
                    opened = -1;
                    notifyItemChanged(getAdapterPosition());
                } else {
                    int oldOpened = opened;
                    opened = getAdapterPosition();
                    notifyItemChanged(oldOpened);
                    notifyItemChanged(opened);
                }
            }


        }

    }
}
