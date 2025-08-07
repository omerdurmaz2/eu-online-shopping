package com.euonlineshopping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.home.ProductsUiState
import com.euonlineshopping.domain.model.sort.SortOptionUiModel
import com.euonlineshopping.domain.usecase.GetCategoryProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import com.euonlineshopping.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoryProductsUseCase: GetCategoryProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val screenState = _screenState.asStateFlow()

    var selectedCategory: String? = null
        private set

    var selectedSortOption: SortOptionUiModel? = null
        private set

    var searchTerm: String? = null
        private set

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.invoke(selectedSortOption?.sortBy, selectedSortOption?.order)
                .collect {
                    _screenState.emit(it)
                }
        }
    }

    fun filterProducts(category: String) {
        selectedCategory = category
        viewModelScope.launch {
            getCategoryProductsUseCase.invoke(
                category,
                selectedSortOption?.sortBy,
                selectedSortOption?.order
            ).collect {
                _screenState.emit(it)
            }
        }
    }

    fun clearFilter() {
        selectedCategory = null
        getProducts()
    }

    fun applySort(sortOption: SortOptionUiModel) {
        selectedSortOption = sortOption

        if (selectedCategory.isNullOrEmpty().not()) {
            filterProducts(selectedCategory.orEmpty())
        } else if (searchTerm.isNullOrEmpty().not()) {
            searchProduct(searchTerm.orEmpty())
        } else {
            getProducts()
        }
    }

    fun clearSort() {
        selectedSortOption = null
        getProducts()
    }

    fun searchProduct(searchTerm: String) {
        this.searchTerm = searchTerm
        viewModelScope.launch {
            searchProductsUseCase.invoke(
                searchTerm,
                selectedSortOption?.sortBy,
                selectedSortOption?.order
            ).collect {
                _screenState.emit(it)
            }
        }
    }

    fun clearSearchTerms() {
        searchTerm = null
    }
}