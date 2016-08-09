package com.lenovo.domain;

import java.io.Serializable;

/**
 * Created by xiaobai on 16-8-5.
 */
public class Article implements Serializable{
    private static final long serialVersionUID = -6807499073462185531L;
    private String title;
    private String fileName;
    private String content;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
