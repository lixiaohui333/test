package com.meeting.client.ui.activity.frame.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.meeting.client.R;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.view.pullload.NoMoreDataFooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivityMain extends BaseFragmentActivity {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    XRefreshView xRefreshView;

    List<String> datas = null;

    SimpleAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.test_list_layout);
    }

    @Override
    protected void initUI() {
        initdata(20);

        setTitle("哈哈哈");

        adapter = new SimpleAdapter();

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(adapter);

        xRefreshView.setPullLoadEnable(true);


        xRefreshView.setAutoLoadMore(true);


        adapter.setCustomLoadMoreView(new NoMoreDataFooterView(this));

        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);

        xRefreshView.enableReleaseToLoadMore(false);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);


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
    protected void setUI() {
    }

    @Override
    protected void loadInitData() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {

        @Override
        public void onBindViewHolder(SimpleAdapter.SimpleAdapterViewHolder holder, int position, boolean isItem) {
            String s = datas.get(position);
            holder.nameTv.setText("name:" + position + " :" + s);
            holder.ageTv.setText("age:" + position + " :" + s);
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
        public SimpleAdapter.SimpleAdapterViewHolder getViewHolder(View view) {
            return new SimpleAdapter.SimpleAdapterViewHolder(view, false);
        }


        @Override
        public SimpleAdapter.SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recylerview, parent, false);
            SimpleAdapter.SimpleAdapterViewHolder vh = new SimpleAdapter.SimpleAdapterViewHolder(v, true);
            return vh;
        }

        public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

            public View rootView;
            public TextView nameTv;
            public TextView ageTv;
            public int position;

            public SimpleAdapterViewHolder(View itemView, boolean isItem) {
                super(itemView);
                if (isItem) {
                    nameTv = (TextView) itemView
                            .findViewById(R.id.recycler_view_test_item_person_name_tv);
                    ageTv = (TextView) itemView
                            .findViewById(R.id.recycler_view_test_item_person_age_tv);
                    rootView = itemView
                            .findViewById(R.id.card_view);
                }

            }
        }

        //        public String getItem(int position) {
        //            if (position < list.size())
        //                return list.get(position);
        //            else
        //                return null;
        //        }

    }
}
