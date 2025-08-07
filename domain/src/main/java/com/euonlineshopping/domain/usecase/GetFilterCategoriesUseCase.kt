package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.repository.ProductRepository
import com.euonlineshopping.domain.model.filter.FilterItemUiModel
import com.euonlineshopping.domain.model.filter.FiltersUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetFilterCategoriesUseCase @Inject constructor(
    private val productsRepository: ProductRepository
) {
    operator fun invoke(selectedFilter: String?): Flow<FiltersUiState> = flow {
        productsRepository.getFilterCategories().onSuccess { response ->
            if (response.isNotEmpty()) {
                emit(FiltersUiState.Content(response.map { filter ->
                    FilterItemUiModel(
                        id = filter.slug.orEmpty(),
                        name = filter.name.orEmpty(),
                        isSelected = filter.slug == selectedFilter
                    )
                }))
            } else {
                emit(FiltersUiState.Empty)
            }
        }.onFailure {
            emit(FiltersUiState.Error(""))
        }
    }.onStart { emit(FiltersUiState.Loading) }.flowOn(Dispatchers.IO)
}