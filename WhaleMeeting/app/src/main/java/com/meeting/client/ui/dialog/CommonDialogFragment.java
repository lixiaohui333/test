package com.meeting.client.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Haoz on 2017/4/6 0006.
 */
public class CommonDialogFragment extends DialogFragment {

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;

    /**
     * 回调获得需要显示的 dialog
     */
    private OnCallDialog mOnCallDialog;

    public interface OnDialogCancelListener {
        void onCancel();
    }

    public interface OnCallDialog {
        Dialog getDialog(Context context);
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable) {
        return newInstance(callDialog, cancelable, null);
    }

    public static CommonDialogFragment newInstance(OnCallDialog callDialog, boolean cancelable, OnDialogCancelListener cancelListener) {
        CommonDialogFragment instance = new CommonDialogFragment();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mOnCallDialog = callDialog;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == mOnCallDialog) {
            super.onCreate(savedInstanceState);
        }
        return mOnCallDialog.getDialog(getActivity());
    }


    /**
     * 判断弹窗是否显示
     * @return
     */
    public boolean isShowing(){
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * 显示DialogFragment(注此方法会衍生出状态值问题(本人在正常使用时并未出现过))
     * @param manager
     * @param tag
     * @param isResume 在Fragment中使用可直接传入isResumed()
     *                 在FragmentActivity中使用可自定义全局变量 boolean isResumed 在onResume()和onPause()中分别传人判断为true和false
     */
    public void show(FragmentManager manager, String tag, boolean isResume){
        if(!isShowing()){
            if(isResume){
                //正常显示
                if(!isAdded()){
                    show(manager, tag);
                }else{
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.show(this);
                    ft.commit();
                }
            }else{
                //注 此方法会衍生出一些状态问题,慎用（在原代码中 需要设置  mDismissed = false 和 mShownByMe = true 并未在此引用到,如果需要用到相关判断值,此方法不可用）
                FragmentTransaction ft = manager.beginTransaction();
                if(!isAdded()){
                    ft.add(this, tag);
                }else{
                    ft.show(this);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    /**
     * 关闭DialogFragment
     * @param isResume 在Fragment中使用可直接传入isResumed()
     *                 在FragmentActivity中使用可自定义全局变量 boolean isResumed 在onResume()和onPause()中分别传人判断为true和false
     */
    public void dismiss(boolean isResume){
        if(isResume){
            dismiss();
        }else{
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        if(isShowing()){
            super.dismiss();
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if(isShowing()){
            super.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个 dialog 会出现边框
                if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }

            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }
}

