package com.roman.batsu.ui.model

import java.io.Serializable

data class KurjerInfo(
        val image: String? = "",
        val title: String? = "",
        val description: String? = "",
        val author: String? = "",
        val data: String? = "",
        val web_link: String?) : Serializable