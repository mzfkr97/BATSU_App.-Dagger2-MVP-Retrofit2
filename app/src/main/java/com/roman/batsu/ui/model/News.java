package com.roman.batsu.ui.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {


    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;


    @SerializedName("time")
    private String time;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("web_link")
    private String web_link;

    @SerializedName("hex")
    private int hex;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }



    public String getTime() {
        return time;
    }


    public String getImage_url() {
        return image_url;
    }


    public String getWeb_link() {
        return web_link;
    }


    public int getHex() {
        return hex;
    }
}
