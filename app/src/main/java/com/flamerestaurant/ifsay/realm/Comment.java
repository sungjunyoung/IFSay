package com.flamerestaurant.ifsay.realm;

import java.util.Date;

import io.realm.RealmObject;

public class Comment extends RealmObject {
    private int commentId;
    private int ifsayId;
    private String content;
    private String writer;
    private Date date;

    public Comment(int commentId, int ifsayId, String content, String writer, Date date) {
        super();
        this.commentId = commentId;
        this.ifsayId = ifsayId;
        this.content = content;
        this.writer = writer;
        this.date = date;
    }

    public Comment() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getIfsayId() {
        return ifsayId;
    }

    public void setIfsayId(int ifsayId) {
        this.ifsayId = ifsayId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
