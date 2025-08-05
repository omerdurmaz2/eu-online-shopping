package com.euonlineshopping.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.domain.usecase.ClearCartUseCase
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {
    fun pay() {
        viewModelScope.launch {
            clearCartUseCase.invoke()
        }
    }
}