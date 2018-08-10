package com.qbhsnetschool.uitls;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class ActivityStackUtils {
    /**
     * 转载Activity的容器
     */
    private List<Activity> mActivityList = new LinkedList<Activity>();
    private static ActivityStackUtils instance = new ActivityStackUtils();

    /**
     * 将构造函数私有化
     */
    private ActivityStackUtils() {}

    /**
     * 获取ExitAppUtils的实例，保证只有一个ExitAppUtils实例存在
     *
     * @return
     */
    public static ActivityStackUtils getInstance() {
        return instance;
    }


    /**
     * 添加Activity实例到mActivityList中，在onCreate()中调用
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 从容器中删除多余的Activity实例，在onDestroy()中调用
     *
     * @param activity
     */
    public void delActivity(Activity activity) {
        mActivityList.remove(activity);
    }


    /**
     * 退出程序的方法
     */
    public void exit() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }

        System.exit(0);
    }

    /**
     * 清除所有的Activity
     */
    public void clearAllActivity() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }

    /**
     * 判断mActivityList中是否包含某个activity
     *
     * @param aClass 需要判断的activity
     */
    public boolean isExitActivity(Class<?> aClass) {
        for (int i = 0; i < mActivityList.size(); i++) {
            if (mActivityList.get(i).getClass().equals(aClass)) {
                return true;
            }
        }
        return false;
    }
}
