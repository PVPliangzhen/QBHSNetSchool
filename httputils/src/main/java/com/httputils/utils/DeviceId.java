package com.httputils.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLClientInfoException;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 设备ID工具类
 */
public class DeviceId {

    private static final String CACHE_PATH_NAME = "DeviceInfo";
    private static final String DEVICE_ID = "deviceID";

    private static final String EMPTY_STR = "";
    private static String sDeviceId = EMPTY_STR;

    public static String getIdentify() {
        return sDeviceId;
    }

    public static void setIdentify(String deviceID) {
        sDeviceId = deviceID;
    }

    public static void produceIdentify(Context context) {
        context = context.getApplicationContext();
        String[] info = new String[1];
//        info[0]=getAndroidID(context);
        sDeviceId=getAndroidID(context);
//        sDeviceId = setEmptyStrIfNull(Signature.MD5(info));
    }


    private static String getAndroidID(Context context) {
        return setEmptyStrIfNull(Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID));
    }


    private static String setEmptyStrIfNull(String text) {
        return text == null ? EMPTY_STR : text;
    }


}
