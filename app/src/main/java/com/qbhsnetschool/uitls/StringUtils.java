package com.qbhsnetschool.uitls;

import android.support.annotation.Nullable;

import java.util.Arrays;

/**
 * 字符串操作工具包
 *
 * @author liangzhen
 * @created 2018-08-11
 */
public class StringUtils {
    /**
     * 判断给定字符串是否空串
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(@Nullable String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return true;
        } else {
            if (str.equalsIgnoreCase("null")) {
                return true;
            }
        }
        return false;
    }

    public static int str2Int(String str) {
        return Integer.parseInt(str);
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static long toLong(String str, int defValue) {
        try {
            return Long.parseLong(str.trim());
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串数组转字符串
     *
     * @param strings 数组
     * @return String
     */
    public static String stringArrayToStringForSearchHistory(String[] strings) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (i == strings.length - 1) {
                sBuffer.append(strings[i]);
            } else {
                sBuffer.append(strings[i] + "-_-");
            }
        }
        return sBuffer.toString();
    }

    /**
     * @param separator 拆分字符依据
     * @param str       要拆分的字符
     * @return 拆分后得到的数组
     */
    public static String[] convertStrToArray(String str, String separator, int size) {
        String[] strArray = null;
        if (size > 0) {
            strArray = str.split(separator, size);
        } else {
            strArray = str.split(separator);
        }
        return strArray;
    }

    /**
     * 对String字符串进行排序
     *
     * @param str String字符串
     * @return
     */
    public static String sortStringBuilder(String str) {
        String[] strArray = convertStrToArray(str.toString(), ",", -1);
        Arrays.sort(strArray);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            builder.append(strArray[i] + ",");
        }
        return builder.toString();
    }

    /**
     * 小数点后如果是纯零，只保留整数
     *
     * @param aDouble
     * @return
     */
    public static String cleanDecimalZero(Double aDouble) {
        try {
            if (!aDouble.toString().equals("NaN") && !aDouble.toString().equals("null")) {
                String[] tempS = aDouble.toString().split("\\.");
                if (Integer.parseInt(tempS[1]) == 0) {
                    return tempS[0];
                }
                return aDouble.toString();
            } else {
                return "null";
            }
        } catch (Exception e) {
            return aDouble.toString();
        }
    }

    /**
     * 转换字符串中的HTML特殊字符
     */
    public static String escapeSpecialHTML(String str) {
        if (str != null) {
            str = str.replaceAll("<br>|<br/>", "\n");
            str = str.replaceAll("&amp;", "&");
            str = str.replaceAll("&quot;", "\"");
            str = str.replaceAll("&lt;", "<");
            str = str.replaceAll("&gt;", ">");
            str = str.replaceAll("&nbsp;", " ");
            str = str.replaceAll("&frasl;", "/");
        }
        return str;
    }
}
