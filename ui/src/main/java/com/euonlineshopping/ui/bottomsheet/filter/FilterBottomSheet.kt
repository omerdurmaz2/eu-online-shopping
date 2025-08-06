package com.euonlineshopping.ui.bottomsheet.filter

import android.os.Bundle
import android.view.View
import android.widget.Filter.FilterListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.FilterItemUiModel
import com.euonlineshopping.domain.model.FiltersUiState
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.ui.base.BaseBottomSheet
import com.euonlineshopping.ui.bottomsheet.filter.adapter.FiltersAdapter
import com.euonlineshopping.ui.bottomsheet.filter.adapter.FiltersCallBack
import com.euonlineshopping.ui.databinding.BottomSheetFilterBinding
import com.euonlineshopping.ui.home.HomeFragment
import com.euonlineshopping.ui.home.HomeFragmentDirections
import com.euonlineshopping.ui.home.HomeViewModel
import com.euonlineshopping.ui.home.adapter.HomeProductsAdapter
import com.euonlineshopping.ui.home.adapter.ProductsCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

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
                        if (state.filters.any { filter -> filter.isSelected }) {
                            binding.btnClearFilter.visibility = View.VISIBLE
                        }
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