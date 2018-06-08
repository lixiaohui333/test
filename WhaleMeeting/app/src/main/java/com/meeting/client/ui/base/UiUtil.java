package com.meeting.client.ui.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.meeting.client.android.application.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class UiUtil {


    /**
     *  @desc fragment 绑定到view上
     *  @author lxh  2018/3/29 11:53
     */
    public static void addFragmentToActivity(FragmentManager supportFragmentManager, Fragment fragment, int frameId){

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(frameId,fragment);
        fragmentTransaction.commit();
    }

    public static Context getContext(){

        return MyApplication.getInstance().getContext();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName() {

        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "v0.0.1";
        }
    }

    /**
     *  @desc 是否安装了该包
     *  @author lxh  2018/4/4 10:46
     */
    public static boolean isAvilible(String packageName) {
        final PackageManager packageManager = getContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))

                return true;
        }
        return false;
    }

}
