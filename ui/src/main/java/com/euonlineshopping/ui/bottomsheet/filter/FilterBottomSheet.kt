package com.euonlineshopping.ui.bottomsheet.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.FilterItemUiModel
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState?.collect {
                when (val state = it) {
                    is ProductsUiState.Content -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.rvHomeProducts.visibility = View.VISIBLE
                        productsAdapter?.submitList(state.products)
                    }

                    is ProductsUiState.Error -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llError.visibility = View.VISIBLE
                    }

                    ProductsUiState.Empty -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llEmpty.visibility = View.VISIBLE
                    }

                    ProductsUiState.Loading -> {
                        binding.cpLoading.visibility = View.VISIBLE
                        binding.llError.visibility = View.GONE
                        binding.rvHomeProducts.visibility = View.GONE
                    }
                }
            }
        }
    }

    private val filtersCallBack = object : FiltersCallBack {
        override fun onSelect(filter: FilterItemUiModel) {
            TODO("Not yet implemented")
        }
    }
}