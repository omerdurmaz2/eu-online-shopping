package com.euonlineshopping.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.usecase.AddProductToCartUseCase
import com.euonlineshopping.domain.usecase.GetFavoriteProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductFavoriteStatusUseCase
import com.euonlineshopping.domain.usecase.ToggleProductFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val toggleProductFavoriteStatusUseCase: ToggleProductFavoriteStatusUseCase,
    private val getProductFavoriteStatusUseCase: GetProductFavoriteStatusUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<HomeProductUiModel?>(
        savedStateHandle.get<HomeProductUiModel>(ProductDetailFragment.PRODUCT)
    )
    val product = _product.asStateFlow()

    fun listenProductFavoriteStatus() {
        viewModelScope.launch {
            getProductFavoriteStatusUseCase(productId = product.value?.id ?: 0).collect {
                _product.update { current ->
                    current?.copy(isFavorite = it)
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            product.value?.let {
                toggleProductFavoriteStatusUseCase.invoke(it)
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            product.value?.let { addProductToCartUseCase.invoke(it) }
        }
    }
}