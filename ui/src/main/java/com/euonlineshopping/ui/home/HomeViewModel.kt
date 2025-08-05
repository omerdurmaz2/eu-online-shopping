package com.euonlineshopping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel  constructor(
    //private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val sortBy: String? = null
    private val order: String? = null

    fun getProducts() {
        viewModelScope.launch {
           // getProductsUseCase.invoke(sortBy, order)
        }
    }
}