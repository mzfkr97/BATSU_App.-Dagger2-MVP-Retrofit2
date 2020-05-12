package com.roman.batsu.ui.model

class Home {
    val day: String? = null
    val date: String? = null
    val lessons: List<Lessons>? = null

    override fun toString(): String {
        return "Количество занятий: " + lessons!![0]
    }
}