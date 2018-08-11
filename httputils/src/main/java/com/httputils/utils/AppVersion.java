package com.httputils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.sql.SQLClientInfoException;

/**
 * ANDROID APP VERSION
 */
public class AppVersion {
    private static String sAppVerName;//the app's version name;
    private static int sAppVerCode;//the app's version code


    public static void setVersion(String versionName, int versionCode) {
        sAppVerName = versionName;
        sAppVerCode = versionCode;
    }

    public static void getAppVersion(Context context) {
        // 获取package manager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            final PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            sAppVerCode = packInfo.versionCode;
            sAppVerName = packInfo.versionName;
            Log.w("AppVersion", "!!!sAppVerName: " + sAppVerName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getVersionNameByContext(Context context) {
        // 获取package manager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            final PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//            sAppVerCode = packInfo.versionCode;
            sAppVerName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sAppVerName;
    }

    public static String getVersionName() {
        return sAppVerName;
    }

    public static int getAppVerCode() {
        return sAppVerCode;
    }

}
