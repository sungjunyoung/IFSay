package com.flamerestaurant.ifsay.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ifsay extends RealmObject {
    private int ifsayId;
    private int questionId;
    private String writer;
    private String content;
    private int ifsayCount;
    private Date date;
    private String rgb;

    public Ifsay(int ifsayId, int questionId, String writer, String content, int ifsayCount, Date date, String rgb) {
        super();
        this.ifsayId = ifsayId;
        this.questionId = questionId;
        this.writer = writer;
        this.content = content;
        this.ifsayCount = ifsayCount;
        this.date = date;
        this.rgb = rgb;
    }

    public Ifsay(){
    }

    public int getIfsayId() {
        return ifsayId;
    }

    public void setIfsayId(int ifsayId) {
        this.ifsayId = ifsayId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIfsayCount() {
        return ifsayCount;
    }

    public void setIfsayCount(int ifsayCount) {
        this.ifsayCount = ifsayCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

}
