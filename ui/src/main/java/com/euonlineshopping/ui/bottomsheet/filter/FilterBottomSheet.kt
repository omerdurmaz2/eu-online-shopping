package com.euonlineshopping.ui.bottomsheet.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.FilterItemUiModel
import com.euonlineshopping.domain.model.FiltersUiState
import com.euonlineshopping.ui.base.BaseBottomSheet
import com.euonlineshopping.ui.bottomsheet.filter.adapter.FiltersAdapter
import com.euonlineshopping.ui.bottomsheet.filter.adapter.FiltersCallBack
import com.euonlineshopping.ui.databinding.BottomSheetFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomSheet :
    BaseBottomSheet<BottomSheetFilterBinding>(BottomSheetFilterBinding::inflate) {

    private val viewModel: FilterBottomSheetViewModel by viewModels()
    var applyFilterCallback: ((String) -> Unit)? = null
    var clearFilterCallback: (() -> Unit)? = null

    private var filtersAdapter: FiltersAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filtersAdapter = FiltersAdapter(
            filtersCallBack = filtersCallBack
        )
        binding.rvFilters.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filtersAdapter
        }

        binding.btnClearFilter.setOnClickListener {
            clearFilterCallback?.invoke()
            dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState.collect {
                when (val state = it) {
                    is FiltersUiState.Content -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llContent.visibility = View.VISIBLE
                        filtersAdapter?.submitList(state.filters)

                    }

                    is FiltersUiState.Error -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llError.visibility = View.VISIBLE
                    }

                    FiltersUiState.Empty -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llEmpty.visibility = View.VISIBLE
                    }

                    FiltersUiState.Loading -> {
                        binding.cpLoading.visibility = View.VISIBLE
                        binding.llError.visibility = View.GONE
                        binding.llContent.visibility = View.GONE
                    }
                }
            }
        }

        if (viewModel.selectedFilter.isNullOrEmpty().not()) {
            binding.btnClearFilter.visibility = View.VISIBLE
        }
    }

    private val filtersCallBack = object : FiltersCallBack {
        override fun onSelect(filter: FilterItemUiModel) {
            applyFilterCallback?.invoke(filter.id)
            dismiss()
        }
    }

    companion object {
        const val SELECTED_FILTER = "SELECTED_FILTER"

        fun newInstance(
            selectedFilter: String?,
            applyFilter: (String) -> Unit,
            clearFilter: () -> Unit
        ): FilterBottomSheet {
            return FilterBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(SELECTED_FILTER, selectedFilter)
                }
                applyFilterCallback = applyFilter
                clearFilterCallback = clearFilter
            }
        }
    }
}