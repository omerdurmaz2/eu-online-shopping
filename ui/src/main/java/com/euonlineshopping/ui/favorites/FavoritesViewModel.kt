package com.euonlineshopping.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.domain.usecase.GetFavoriteProductsUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase
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
}