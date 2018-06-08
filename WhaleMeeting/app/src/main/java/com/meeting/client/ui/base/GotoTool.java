package com.meeting.client.ui.base;

import android.content.Intent;
import android.net.Uri;

import com.meeting.client.comm.Config;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/3/29.
 */

public class GotoTool {


    /**
     * @desc 跳转到网页
     * @author lxh  2018/4/4 10:17
     */
    public static void webByBrowser(String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (BaseFragmentActivity.activity == null) {
            UiUtil.getContext().startActivity(intent);
        } else {
            BaseFragmentActivity.activity.startActivity(intent);
        }

    }


    /**
     * @desc 跳转到支付宝支付界面
     * @author lxh  2018/4/4 10:17
     */
    public static void goAlipay(String userID) {


        if (!UiUtil.isAvilible(Config.ALIPAY_PACKAGENAME)) {

            if (BaseFragmentActivity.activity != null)
                BaseFragmentActivity.activity.showToast("您还没有安装支付宝");

            return;
        }

        String qrcode = "HTTPS://QR.ALIPAY.COM/" + userID;

        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
        }
        try {

            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis()));
            UiUtil.getContext().startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
