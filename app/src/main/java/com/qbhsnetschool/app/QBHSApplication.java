package com.qbhsnetschool.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.qbhsnetschool.entity.User;

/**
 * created by liangzhen at 2018/8/10
 */
public class QBHSApplication extends MultiDexApplication{

    private static Context context;
    public static final boolean RTC_AUDIO = false; // 是否使用语音连麦 （true-使用 ／ false-不使用，采用视频连麦）

    public static final boolean REPLAY_CHAT_FOLLOW_TIME = true; // 是否让回放的聊天内容随时间轴推进展示

    public static final boolean REPLAY_QA_FOLLOW_TIME = true; // 是否让回放的问答内容随时间轴推进展示

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User user = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = this;
        }
        QBHSCrashHandler.getInstance().setCustomCrashHanler(this);
    }

    public static Context getContext() {
        return context;
    }
}
