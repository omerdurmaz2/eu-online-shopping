package com.euonlineshopping.ui.bottomsheet.filter.adapter

import com.euonlineshopping.domain.model.FilterItemUiModel

interface FiltersCallBack {
    fun onSelect(filter: FilterItemUiModel)
}