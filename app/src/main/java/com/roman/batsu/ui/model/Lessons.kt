package com.roman.batsu.ui.model

import com.google.gson.annotations.SerializedName

class Lessons {
    @SerializedName("lesson_name")
    val lessonName: String? = null
    @SerializedName("count")
    val count: String? = null

    @SerializedName("lesson_type")
    val lessonType: String? = null

    @SerializedName("teacher_surname")
    val teacherSurname: String? = null

    override fun toString(): String {
        return "$lessonName, count = $count"
    }
}