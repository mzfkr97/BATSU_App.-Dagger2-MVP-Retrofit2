package com.roman.batsu.ui.note_frags;

import java.io.Serializable;

public class Notification implements Serializable {

    private String title;
    private String first_lesson;

    public Notification(String title, String first_lesson) {
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
