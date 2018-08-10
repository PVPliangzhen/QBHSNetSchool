package com.qbhsnetschool.app;

import android.support.multidex.MultiDexApplication;

/**
 * created by liangzhen at 2018/8/10
 */
public class QBHSApplication extends MultiDexApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        QBHSCrashHandler.getInstance().setCustomCrashHanler(this);
    }
}
