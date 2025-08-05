package com.euonlineshopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.CartUiState
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.domain.usecase.AddProductToCartUseCase
import com.euonlineshopping.domain.usecase.ClearProductFromCartUseCase
import com.euonlineshopping.domain.usecase.GetCartProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import com.euonlineshopping.domain.usecase.RemoveOneProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeOneProductFromCartUseCase: RemoveOneProductFromCartUseCase,
    private val clearProductFromCartUseCase: ClearProductFromCartUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            getCartProductsUseCase.invoke().collect {
                _screenState.emit(it)
            }
        }
    }


    fun removeProduct(product: HomeProductUiModel) {
        viewModelScope.launch { clearProductFromCartUseCase(product) }
    }

    fun increaseCount(product: HomeProductUiModel) {
        viewModelScope.launch { addProductToCartUseCase(product) }
    }

    fun decreaseCount(product: HomeProductUiModel) {
        viewModelScope.launch { removeOneProductFromCartUseCase(product) }
    }
}