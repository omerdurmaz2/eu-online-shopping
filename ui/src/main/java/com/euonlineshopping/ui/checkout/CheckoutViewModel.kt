package com.euonlineshopping.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.usecase.ClearCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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