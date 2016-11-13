package com.sj.pojo;

/**
 * Created by Administrator on 2016-11-13.
 */
public class User {
    private String uuid;
    private String userID;
    private String userName;

    public User(String uuid, String userID, String userName) {
        this.setUuuid(uuid);
        this.setUserID(userID);
        this.setUserName(userName);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuuid(String uuuid) {
        this.uuid = uuuid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
