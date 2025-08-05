package com.euonlineshopping.domain.mapper

import com.euonlineshopping.data.model.Product
import com.euonlineshopping.domain.model.HomeProductUiModel
import kotlinx.coroutines.processNextEventInCurrentThread

internal fun Product.toUiModel(): HomeProductUiModel {
    return HomeProductUiModel(
        isFavorite = isFavorite ?: false,
        thumbnail = thumbnail.orEmpty(),
        title = title.orEmpty(),
        id = id ?: 0
    )
}

internal fun HomeProductUiModel.toApiModel(): Product {
    return Product(
        isFavorite = isFavorite,
        thumbnail = thumbnail,
        title = title,
        id = id
    )
}