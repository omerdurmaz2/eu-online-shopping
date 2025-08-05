package com.euonlineshopping.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("isFavorite")
    val isFavorite: Boolean? = null,
    @SerializedName("price")
    val price: Double? = null,
)