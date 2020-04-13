package com.roman.batsu.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Rings(//super class
        @field:SerializedName("title")
        val title: String?,
        @field:SerializedName("description")
        val description: String?) : Serializable