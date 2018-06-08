//package com.meeting.client.ui.activity.frame.home;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
//import com.meeting.client.R;
//
//import java.util.List;
//
//public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
//    private List<String> list;
//    private int largeCardHeight, smallCardHeight;
//
//    public SimpleAdapter(List<String> list, Context context) {
//        this.list = list;
//    }
//
//    @Override
//    public void onBindViewHolder(SimpleAdapterViewHolder holder, int position, boolean isItem) {
//        String person = list.get(position);
//        holder.nameTv.setText(person);
//        holder.ageTv.setText(person);
//    }
//
//    @Override
//    public int getAdapterItemViewType(int position) {
//        return 0;
//    }
//
//    @Override
//    public int getAdapterItemCount() {
//        return list.size();
//    }
//
//    @Override
//    public SimpleAdapterViewHolder getViewHolder(View view) {
//        return new SimpleAdapterViewHolder(view, false);
//    }
//
//    public void setData(List<String> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.item_recylerview, parent, false);
//        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
//        return vh;
//    }
//
//    public void insert(String person, int position) {
//        insert(list, person, position);
//    }
//
//    public void remove(int position) {
//        remove(list, position);
//    }
//
//    public void clear() {
//        clear(list);
//    }
//
//    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {
//
//        public View rootView;
//        public TextView nameTv;
//        public TextView ageTv;
//        public int position;
//
//        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
//            super(itemView);
//            if (isItem) {
//                nameTv = (TextView) itemView
//                        .findViewById(R.id.recycler_view_test_item_person_name_tv);
//                ageTv = (TextView) itemView
//                        .findViewById(R.id.recycler_view_test_item_person_age_tv);
//                rootView = itemView
//                        .findViewById(R.id.card_view);
//            }
//
//        }
//    }
//
//    public String getItem(int position) {
//        if (position < list.size())
//            return list.get(position);
//        else
//            return null;
//    }
//
//}