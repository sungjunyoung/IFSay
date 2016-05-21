package com.flamerestaurant.ifsay.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Question extends RealmObject {
    private int questionId;
    private String content;
    private Date date;

    public Question(int questionId, String content, Date date) {
        super();
        this.questionId = questionId;
        this.content = content;
        this.date = date;
    }

    public Question(){

    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
