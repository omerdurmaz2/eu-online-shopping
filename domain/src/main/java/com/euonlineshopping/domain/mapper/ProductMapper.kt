package com.euonlineshopping.domain.mapper

import com.euonlineshopping.data.model.Product
import com.euonlineshopping.domain.model.HomeProductUiModel

internal fun Product.toUiModel(count: Int = 1): HomeProductUiModel {
    return HomeProductUiModel(
        isFavorite = isFavorite ?: false,
        thumbnail = thumbnail.orEmpty(),
        title = title.orEmpty(),
        id = id ?: 0,
        price = price ?: 0.0,
        count = count
    )
}

internal fun HomeProductUiModel.toApiModel(): Product {
    return Product(
        isFavorite = isFavorite,
        thumbnail = thumbnail,
        title = title,
        id = id,
        price = price
    )
}