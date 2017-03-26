package com.esy.stack.entity;

public class ArticleDetailWithBLOBs extends ArticleDetail {
    private String content;

    private String contentHtmt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtmt() {
        return contentHtmt;
    }

    public void setContentHtmt(String contentHtmt) {
        this.contentHtmt = contentHtmt;
    }
}