package com.roman.batsu.ui.main

import android.util.Log

class FileNameFactory {

    fun fileName(intName: Int?): String? {
        var fileNameOutput: String? = null
        try {
            when (intName) {
                0 -> fileNameOutput = "schedule_82.json"
                1 -> fileNameOutput = "schedule_83.json"
                2 -> fileNameOutput = "schedule_84.json"
            }
        } catch (e: Exception) {
            Log.d("TAG", "getMyInputStream: $e")
        }
        return fileNameOutput
    }
}




