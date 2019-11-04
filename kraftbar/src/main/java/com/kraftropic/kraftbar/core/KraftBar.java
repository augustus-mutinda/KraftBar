package com.kraftropic.kraftbar.core;

import android.content.Context;
import android.os.CountDownTimer;

import androidx.fragment.app.FragmentManager;

public class KraftBar {

    private Context context;
    private String type = Constants.loading;
    public static String LOADING = Constants.loading;
    public static String SUCCESS = Constants.success;
    public static String FAILED = Constants.failed;
    private int timeOut = 3000;
    private String summary = "KraftBar with love, Follow on Github.";
    private static KraftBar instance;
    private FragmentManager fragmentManager;
    private boolean cancellable = true;

    private KraftBar(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public static KraftBar with(Context context, FragmentManager fragmentManager) {
        if (instance == null)
            instance = new KraftBar(context, fragmentManager);
        return instance;
    }

    public KraftBar toast(String toast) {
        this.summary = toast;
        return instance;
    }

    public KraftBar style(String type) {
        this.type = type;
        return instance;
    }

    public KraftBar timeOut(int timeOut) {
        this.timeOut = timeOut;
        return instance;
    }

    public KraftBar cancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return instance;
    }

    public void show() {
        final ToastBar toastBar = ToastBar.newInstance(type, summary, cancellable);
        toastBar.show(fragmentManager, "KraftBar");
        (new CountDownTimer(timeOut, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                toastBar.dismiss();
            }
        }).start();
    }

    public ToastBar getKraftToastBar() {
        return ToastBar.newInstance(type, summary, cancellable);
    }
}
