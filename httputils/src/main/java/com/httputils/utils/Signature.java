package com.httputils.utils;

import android.util.Log;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/1/26.
 */

public class Signature {

    private static final String AL_MD5="MD5";
    private static final Charset T_CHARSET= Charset.forName("utf-8");

    public static String MD5(Object[] data){
        final byte[] hash=MD5_Bytes( Arrays.toString(data));
        if(hash==null)return "";
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public static String MD5_1(Object[] data){
        if(null==data||data.length==0)return "";
        StringBuilder stringer=new StringBuilder();
        for(int i=0;i<data.length;i++)
            stringer.append(data[i]);
        final byte[] hash=MD5_Bytes(stringer.toString());
        if(hash==null)return "";
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    private static byte[] MD5_Bytes(String text){
        if(null==text) return null;
        byte[] hash=null;

        try {
            hash = MessageDigest.getInstance(AL_MD5).digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
