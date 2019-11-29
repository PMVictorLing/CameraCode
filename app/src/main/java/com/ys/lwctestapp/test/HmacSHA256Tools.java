package com.ys.lwctestapp.test;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lwc
 * 2019/10/31
 * 描述：$des$
 */
public class HmacSHA256Tools {
    //   SECRET KEY
    private final static String secret_key = "ndE2jdZNFixH9G6Aidsfyf7lYT3PxW";

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String signaSha(String secret,String stringToSign) {
        String sign = null;
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = secret.getBytes("UTF-8"); //secret 为 APP 的密钥。
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length,
                    "HmacSHA256"));
//          sign = new String(Base64.encodeBase64(Sha256.doFinal(stringToSign.getBytes("UTF-8")),"UTF-8"));
//            sign = new String(Base64.encode(Sha256.doFinal(stringToSign.getBytes("UTF-8")),"UTF-8"));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return sign;
    }
}