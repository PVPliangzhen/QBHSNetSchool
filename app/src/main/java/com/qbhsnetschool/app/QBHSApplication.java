package com.qbhsnetschool.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.qbhsnetschool.entity.User;
import com.qbhsnetschool.entity.UserManager;

import cn.xiaoneng.api.Ntalker;
import cn.xiaoneng.manager.inf.outer.NtalkerCoreCallback;

/**
 * created by liangzhen at 2018/8/10
 */
public class QBHSApplication extends MultiDexApplication{

    private static Context context;
    public static final boolean RTC_AUDIO = false; // 是否使用语音连麦 （true-使用 ／ false-不使用，采用视频连麦）

    public static final boolean REPLAY_CHAT_FOLLOW_TIME = true; // 是否让回放的聊天内容随时间轴推进展示

    public static final boolean REPLAY_QA_FOLLOW_TIME = true; // 是否让回放的问答内容随时间轴推进展示

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = this;
        }
        QBHSCrashHandler.getInstance().setCustomCrashHanler(this);
        UserManager.getInstance().registerApplication(this);
        Ntalker.getInstance().initSDK(this, "kf_20013", new NtalkerCoreCallback() {
            @Override
            public void successed() {
                System.out.println("success");
            }

            @Override
            public void failed(int i) {
                System.out.println("failure" + i);
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}
