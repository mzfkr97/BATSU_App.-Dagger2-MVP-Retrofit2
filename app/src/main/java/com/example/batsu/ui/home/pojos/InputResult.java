package com.example.batsu.ui.home.pojos;

import java.util.List;

public class InputResult {


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



    public static class Lessons
    {
        private String lesson_name;
        private String count;
        private String lesson_type;
        private String teacher_surname;

        public String getLesson_name ()
        {
            return lesson_name;
        }


        public String getCount ()
        {
            return count;
        }


        public String getLesson_type() {
            return lesson_type;
        }

        public String getTeacher_surname() {
            return teacher_surname;
        }

        @Override
        public String toString()
        {
            return lesson_name + ", count = " + count;
        }
    }


}
