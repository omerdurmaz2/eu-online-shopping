package com.euonlineshopping.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeProductUiModel(
    val isFavorite: Boolean = false,
    val thumbnail: String,
    val title: String,
    val id: Int
) : Parcelable