package com.euonlineshopping.domain.model.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeProductUiModel(
    val isFavorite: Boolean = false,
    val thumbnail: String,
    val title: String,
    val description: String,
    val id: Int,
    val price: Double,
    val discountPercentage: Double,
    val count: Int,
) : Parcelable