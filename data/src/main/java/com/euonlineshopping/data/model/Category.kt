package com.euonlineshopping.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("name")
    val name: String? = null,
)