package com.euonlineshopping.ui.bottomsheet.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.filter.FiltersUiState
import com.euonlineshopping.domain.usecase.GetFilterCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFilterCategoriesUseCase: GetFilterCategoriesUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<FiltersUiState>(FiltersUiState.Loading)
    val screenState = _screenState.asStateFlow()

    val selectedFilter by lazy { savedStateHandle.get<String?>(FilterBottomSheet.SELECTED_FILTER) }

    init {
        getFilters()
    }

    private fun getFilters() {
        viewModelScope.launch {
            getFilterCategoriesUseCase.invoke(selectedFilter).collect {
                _screenState.emit(it)
            }
        }
    }
}