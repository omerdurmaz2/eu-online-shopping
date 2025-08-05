package com.euonlineshopping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.ProductsUiState
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val screenState = _screenState.asStateFlow()

    private val sortBy: String? = null
    private val order: String? = null

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.invoke(sortBy, order).collect {
                _screenState.emit(it)
            }
        }
    }
}