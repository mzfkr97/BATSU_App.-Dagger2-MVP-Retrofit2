package com.roman.batsu.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//superclass
open class Rings(
        @field:SerializedName("title")
        val title: String?,
        @field:SerializedName("description")
        val description: String?) : Serializable