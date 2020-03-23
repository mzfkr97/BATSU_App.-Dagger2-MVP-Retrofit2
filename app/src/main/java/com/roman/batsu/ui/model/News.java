package com.roman.batsu.ui.model;

import com.google.gson.annotations.SerializedName;

public class News extends Rings {


    @SerializedName("time")
    private String time;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("web_link")
    private String web_link;

    @SerializedName("hex")
    private int hex;

    public News(String title, String description) {
        super(title, description);
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
