package com.httputils.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/1/26.
 */

public class Encryption {

    private static final String AL_AES="AES";
    private static final Charset T_CHARSET= Charset.forName("utf-8");

    public static String AES(String data,String token){
        if(null==token)
            return data;
        SecretKeySpec skeySpec = new SecretKeySpec(token.getBytes(T_CHARSET), AL_AES);
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return new String(new Base64().encode(cipher.doFinal(data.getBytes(T_CHARSET))));//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
