package com.qbhsnetschool.app;

import android.support.multidex.MultiDexApplication;

public class QBHSApplication extends MultiDexApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        QBHSCrashHandler.getInstance().setCustomCrashHanler(this);
    }
}
