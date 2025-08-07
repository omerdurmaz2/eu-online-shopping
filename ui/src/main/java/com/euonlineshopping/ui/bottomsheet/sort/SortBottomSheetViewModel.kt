package com.euonlineshopping.ui.bottomsheet.sort

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euonlineshopping.domain.model.sort.SortOptionUiModel
import com.euonlineshopping.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortBottomSheetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableStateFlow<List<SortOptionUiModel>>(listOf())
    val screenState = _screenState.asStateFlow()

    val selectedSortOption by lazy {
        savedStateHandle.get<SortOptionUiModel?>(
            SortBottomSheet.SELECTED_SORT_OPTION
        )
    }

    init {
        getSortOptions()
    }

    private fun getSortOptions() {
        viewModelScope.launch {
            _screenState.emit(SORT_OPTIONS.map { it.copy(isSelected = it.id == selectedSortOption?.id) })
        }
    }

    companion object {
        private val SORT_OPTIONS = listOf(
            SortOptionUiModel(
                id = 1,
                order = "asc",
                sortBy = "price",
                titleRes = R.string.sort_option_price_asc,
                isSelected = false
            ),
            SortOptionUiModel(
                id = 2,
                order = "desc",
                sortBy = "price",
                titleRes = R.string.sort_option_price_desc,
                isSelected = false
            ),
            SortOptionUiModel(
                id = 3,
                order = "asc",
                sortBy = "title",
                titleRes = R.string.sort_option_title_asc,
                isSelected = false
            ),
            SortOptionUiModel(
                id = 4,
                order = "desc",
                sortBy = "title",
                titleRes = R.string.sort_option_title_desc,
                isSelected = false
            )
        )
    }
}

