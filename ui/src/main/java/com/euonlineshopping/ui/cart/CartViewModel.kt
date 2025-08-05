package com.euonlineshopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.CartUiState
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.domain.usecase.GetCartProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase
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
}