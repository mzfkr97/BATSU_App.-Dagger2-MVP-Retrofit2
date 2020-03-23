package com.roman.batsu.ui.model;

public class Lessons {

    private String lesson_name;
    private String count;
    private String lesson_type;
    private String teacher_surname;

    public String getLesson_name() {
        return lesson_name;
    }


    public String getCount() {
        return count;
    }


    public String getLesson_type() {
        return lesson_type;
    }

    public String getTeacher_surname() {
        return teacher_surname;
    }

    @Override
    public String toString() {
        return lesson_name + ", count = " + count;
    }

}
