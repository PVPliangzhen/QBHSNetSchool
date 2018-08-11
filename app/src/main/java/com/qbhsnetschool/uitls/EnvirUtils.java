package com.qbhsnetschool.uitls;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EnvirUtils {

	private EnvirUtils() {
		super();
	}

	/**
	 * 获取deviceId
	 * @return
	 */
	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		return StringUtils.isEmpty(tm.getDeviceId()) ? "" : tm.getDeviceId();
	}
	
	/**
	 * 获取AndroidManifest.xml  meta-data 值
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getMetaDataValue(Context context, String name) {
		String value = "website";
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (applicationInfo != null && applicationInfo.metaData != null) {
				Object mDataValue = applicationInfo.metaData.get(name);
				if(null != mDataValue){
					value = String.valueOf(mDataValue);
				}
			}
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not read the name in the manifest file.", e);
		}
		return value.toString();
	}
	
	/**
	 * 获取应用版本名称
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context){
		String versionName = "";
		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return versionName;
	}
	
	/**
	 * 获取应用版本号
	 * @param context
	 * @return
	 */
	public static String getAppVersionCode(Context context){
		String versionCode = "";
		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionCode = String.valueOf(packInfo.versionCode);
		} catch (NameNotFoundException e) {
		}
		return versionCode;
	}
	
	/**
	 * 获取手机系统版本
	 * @param context
	 * @return
	 */
	public static String getPhoneSysVersion(Context context){
		String operatingSystem = "";
		operatingSystem = android.os.Build.VERSION.RELEASE;
		try {
			operatingSystem = URLEncoder.encode(operatingSystem, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return operatingSystem;
	}
	
	
	/**
	 * 获取手机型号
	 * @param context
	 * @return
	 */
	public static String getPhoneType(Context context){
		String phoneModel = "";
		phoneModel = android.os.Build.MODEL;
		try {
			phoneModel = URLEncoder.encode(phoneModel, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return phoneModel;
	}
}
