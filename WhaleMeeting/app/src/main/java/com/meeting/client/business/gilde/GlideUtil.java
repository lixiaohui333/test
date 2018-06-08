package com.meeting.client.business.gilde;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.meeting.client.ui.base.BaseFragmentActivity;
import com.meeting.client.ui.base.UiUtil;

/**
 * Created by Administrator on 2018/4/3.
 */

public class GlideUtil {

    public static void displayCircle(ImageView view, String url) {

        display(view,url,true);
    }

    public static void displayCircle(ImageView view, int resId) {

        display(view,resId,true);
    }

    public static void display(ImageView view, String url) {
        display(view,url,false);
    }
    public static void display(ImageView view, int resId) {
        display(view,resId,false);
    }


    private static void display(ImageView view, int resId,boolean isCircle) {

        Context ct = BaseFragmentActivity.activity;
        if (ct == null) {
            ct = UiUtil.getContext();
        }
        GlideRequest requests=GlideApp
                .with(ct)
                .load(resId)
                .dontAnimate()
                .centerCrop()
                .placeholder(view.getDrawable());
        if(isCircle){
            requests.circleCrop();
        }
        requests.into(view);
    }



    public static void displayAsGif(ImageView view, int resId,boolean gif) {

        Context ct = BaseFragmentActivity.activity;
        if (ct == null) {
            ct = UiUtil.getContext();
        }
        GlideRequest requests;

        if(gif) {
            requests = GlideApp
                    .with(ct).asGif();
        }else{
            requests = GlideApp
                    .with(ct).asBitmap();
        }
        requests.load(resId).into(view);
    }


    private static void display(ImageView view, String url,boolean isCircle) {

        Context ct = BaseFragmentActivity.activity;
        if (ct == null) {
            ct = UiUtil.getContext();
        }
        GlideRequest requests=GlideApp
                .with(ct)
                .load(url)
                .dontAnimate()
                .centerCrop()
                .placeholder(view.getDrawable());
        if(isCircle){
            requests.circleCrop();
        }
        requests.into(view);
    }

    public static void display( String url,SimpleTarget<Drawable> lisener) {

        Context ct = BaseFragmentActivity.activity;
        if (ct == null) {
            ct = UiUtil.getContext();
        }

        GlideApp
                .with(ct)
                .load(url)
                .into(lisener);
    }


}
