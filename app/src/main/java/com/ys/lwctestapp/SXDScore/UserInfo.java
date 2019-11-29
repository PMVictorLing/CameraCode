package com.ys.lwctestapp.SXDScore;

/**
 * FileName: test
 * Author: hua
 * Date: 2019-10-17 15:10
 * Description:
 */
public class UserInfo {

    @Override
    public String toString() {
        return "UserInfo{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", uid='" + uid + '\'' +
                ", credit1='" + credit1 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * status : 1
     * message : 登陆成功
     * uid : 1
     * credit1 : 100600.00
     * name : 羊羊羊
     */



    private int status;
    private String message;
    private String uid;
    private String credit1;
    private String name;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredit1() {
        return credit1;
    }

    public void setCredit1(String credit1) {
        this.credit1 = credit1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
