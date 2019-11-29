package com.ys.lwctestapp;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lwc
 * 2019/11/28
 * 描述：$des$
 */
class HmacUtils {

    public static String onHMACSHA256(String data, String key) {

        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");


            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

            sha256_HMAC.init(secret_key);

            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();

            for (byte item : array) {

                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

            }

            return sb.toString().toUpperCase();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
