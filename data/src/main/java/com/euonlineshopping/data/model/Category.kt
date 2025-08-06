package com.euonlineshopping.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("slug")
    val slug: Int? = null,
    @SerializedName("name")
    val name: String? = null,
)