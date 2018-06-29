package com.meeting.client.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.meeting.client.R;
import com.meeting.client.domain.home.SignLocItemDomain;
import com.meeting.client.ui.base.adapter.CommonAdapter;
import com.meeting.client.ui.base.adapter.ViewHolder;

import java.util.List;

public class DialogHelper {


    /**
     *选择签到点
     */
    public static AlertDialog signLocationDialog(Context context, List<SignLocItemDomain> list, final AdapterView.OnItemClickListener clickListener) {
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.style_dailog_from_bottom).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_sign_location, null);
        ListView single_list_lv = (ListView) view.findViewById(R.id.lv_main);
        TodoAdapter adapter = new TodoAdapter(context);
        adapter.setData(list);
        single_list_lv.setAdapter(adapter);
        single_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickListener.onItemClick(parent, view, position, id);
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);


        dialog.show();
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);

        return dialog;
    }

    static class TodoAdapter extends CommonAdapter<SignLocItemDomain> {

        public TodoAdapter(Context mContext) {
            super(mContext, R.layout.dialog_sign_item_text);
        }

        @Override
        public void setData(List<SignLocItemDomain> data) {
            this.mData = data;
            this.notifyDataSetChanged();
        }

        @Override
        public void convert(ViewHolder holder, SignLocItemDomain item) {
            TextView tv_singletext = holder.findView(R.id.tv_info);
            tv_singletext.setText(item.Signer_Drop_Name);
        }
    }

}
