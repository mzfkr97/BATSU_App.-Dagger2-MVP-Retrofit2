package com.roman.batsu.ui.model;

import java.util.List;

public class Home {

        private String day;
        private String date;
        private List<Lessons> lessons;


    public String getDay ()
        {
            return day;
        }

        public List<Lessons> getLessons ()
        {
            return lessons;
        }

    public String getDate() {
        return date;
    }

    @Override
        public String toString()
        {
            return "Количество занятий: " + lessons.get(0);
        }




}
