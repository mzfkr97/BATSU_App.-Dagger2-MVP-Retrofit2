package com.roman.batsu.ui.model;

import java.io.Serializable;

public class Rings implements Serializable {

    private String title;
    private String first_lesson;

    public Rings(String title, String first_lesson) {
        this.title = title;
        this.first_lesson = first_lesson;
    }


    public String getTitle() {
        return title;
    }

    public String getFirst_lesson() {
        return first_lesson;
    }
}
