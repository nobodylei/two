package com.rjwl.reginet.gaotuo.entity;

/**
 * Created by Administrator on 2018/6/7.
 */

public class Notice {
    private int companyId;
    private String content;
    private int id;
    private String path;
    private String title;

    public Notice(int companyId, String content, String path, String title) {
        this.companyId = companyId;
        this.content = content;
        this.path = path;
        this.title = title;
    }

    public Notice() {

    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "companyId=" + companyId +
                ", content='" + content + '\'' +
                ", id=" + id +
                ", path='" + path + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
