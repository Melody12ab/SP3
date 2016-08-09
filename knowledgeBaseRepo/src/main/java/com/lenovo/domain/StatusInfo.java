package com.lenovo.domain;

import java.io.Serializable;

/**
 * Created by xiaobai on 16-8-9.
 */
public class StatusInfo implements Serializable {
    private static final long serialVersionUID = -8763831960296437186L;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
