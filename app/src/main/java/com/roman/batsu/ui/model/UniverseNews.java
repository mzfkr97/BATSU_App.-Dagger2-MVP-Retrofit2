package com.roman.batsu.ui.model;

import java.io.Serializable;

public class UniverseNews implements Serializable {
    private String image;
    private String title;
    private String description;
    private String author;
    private String data;
    private String web_link;

    public UniverseNews(String image, String title, String description, String author, String data, String web_link) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.author = author;
        this.data = data;
        this.web_link = web_link;
    }

    public String getAuthor() {
        return author;
    }

    public String getData() {
        return data;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getWeb_link() {
        return web_link;
    }
}
