package com.euonlineshopping.domain.model.sort

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortOptionUiModel(
    val id: Int,
    val sortBy: String,
    val order: String,
    val titleRes: Int,
    val isSelected: Boolean
) : Parcelable