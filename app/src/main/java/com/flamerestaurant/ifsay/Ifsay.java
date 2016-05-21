package com.flamerestaurant.ifsay;

import io.realm.RealmObject;

public class Ifsay extends RealmObject {

    private int id;
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
