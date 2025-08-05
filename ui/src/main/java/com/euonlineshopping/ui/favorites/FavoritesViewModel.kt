package com.euonlineshopping.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.domain.usecase.AddProductToCartUseCase
import com.euonlineshopping.domain.usecase.GetFavoriteProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import com.euonlineshopping.domain.usecase.ToggleProductFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val toggleProductFavoriteStatusUseCase: ToggleProductFavoriteStatusUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getFavoriteProductsUseCase.invoke().collect {
                _screenState.emit(it)
            }
        }
    }

    fun toggleFavorite(product: HomeProductUiModel) {
        viewModelScope.launch {
            toggleProductFavoriteStatusUseCase.invoke(product)
        }
    }

    fun addToCart(product: HomeProductUiModel) {
        viewModelScope.launch {
            addProductToCartUseCase.invoke(product)
        }
    }
}