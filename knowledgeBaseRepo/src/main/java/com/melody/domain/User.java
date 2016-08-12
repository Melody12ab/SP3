package com.melody.domain;

import java.io.Serializable;

/**
 * Created by xiaobai on 16-8-4.
 */
public class User implements Serializable{
    private static final long serialVersionUID = -7517853380000605397L;
    private long ID;
    private String userName;
    private String password;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
