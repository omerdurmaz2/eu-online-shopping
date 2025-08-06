package com.euonlineshopping.ui.bottomsheet.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val screenState = _screenState.asStateFlow()

    private val sortBy: String? = null
    private val order: String? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.invoke(sortBy, order).collect {
                _screenState.emit(it)
            }
        }
    }
}