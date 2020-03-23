package com.roman.batsu.ui.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rings implements Serializable {

    //super class
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public Rings(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
