package com.lenovo.web;

/**
 * Created by xiaobai on 16-8-9.
 */
public class ArticleInfo {
    private String title;
    private String fileName;
    private String [] content;

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

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
