package com.euonlineshopping.ui.bottomsheet.sort.adapter

import com.euonlineshopping.domain.model.sort.SortOptionUiModel

interface SortOptionsCallBack {
    fun onSelect(filter: SortOptionUiModel)
}