package com.ys.lwctestapp.test;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;

/**
 * @author lwc
 * 2019/10/31
 * 描述：$des$
 */
public class HttpTest {

    //https://blog.csdn.net/qq_35624642/article/details/76619763

    private static String secret = "secret";

    public static void requestPostPARAMs(){
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");

            byte[] keyBytes = secret.getBytes("UTF-8"); //secret 为 APP 的密钥。



        } catch (Exception e) {


        }

    }
}
