package com.euonlineshopping.ui.home

import com.euonlineshopping.ui.base.BaseFragment
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.home.HomeProductUiModel
import com.euonlineshopping.domain.model.home.ProductsUiState
import com.euonlineshopping.ui.R
import com.euonlineshopping.ui.bottomsheet.filter.FilterBottomSheet
import com.euonlineshopping.ui.bottomsheet.sort.SortBottomSheet
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import com.euonlineshopping.ui.home.adapter.HomeProductsAdapter
import com.euonlineshopping.ui.home.adapter.ProductsCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private var productsAdapter: HomeProductsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter = HomeProductsAdapter(
            productsCallback = productsCallbackListener
        )
        binding.rvHomeProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeFragment.productsAdapter
        }

        binding.btnFilter.setOnClickListener {
            val filtersBottomSheet = FilterBottomSheet.newInstance(
                selectedFilter = viewModel.selectedCategory,
                applyFilter = {
                    binding.editTextSearch.editableText.clear()
                    binding.editTextSearch.clearFocus()
                    viewModel.clearSearchTerms()
                    viewModel.filterProducts(it)
                }, clearFilter = {
                    viewModel.clearFilter()
                }
            )

            filtersBottomSheet.show(childFragmentManager, filtersBottomSheet.tag)
        }

        binding.btnSort.setOnClickListener {
            val sortOptionsBottomSheet = SortBottomSheet.newInstance(
                selectedSortOption = viewModel.selectedSortOption,
                applySort = {
                    viewModel.applySort(it)
                },
                clearSort = {
                    viewModel.clearSort()
                }
            )

            sortOptionsBottomSheet.show(childFragmentManager, sortOptionsBottomSheet.tag)
        }

        binding.textInputLayout.setEndIconOnClickListener {
            val searchText = binding.editTextSearch.text.toString()

            viewModel.searchProduct(searchText)
        }

        binding.editTextSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = textView.text.toString()
                viewModel.searchProduct(searchText)
                true
            } else {
                false
            }
        }

        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            if (viewModel.searchTerm.isNullOrEmpty().not() && text.toString().isEmpty()) {
                viewModel.clearSort()
                viewModel.clearFilter()
                viewModel.getProducts()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState.collect {
                when (val state = it) {
                    is ProductsUiState.Content -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.rvHomeProducts.visibility = View.VISIBLE
                        productsAdapter?.submitList(state.products)
                        binding.tvProductCount.text =
                            getString(R.string.home_product_count_placeholder, state.productCount)
                    }

                    is ProductsUiState.Error -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llError.visibility = View.VISIBLE
                        binding.tvProductCount.text =
                            getString(R.string.home_product_count_placeholder, 0)
                    }

                    ProductsUiState.Empty -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llEmpty.visibility = View.VISIBLE
                        binding.tvProductCount.text =
                            getString(R.string.home_product_count_placeholder, 0)
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

    private val productsCallbackListener = object : ProductsCallback {
        override fun navigateToDetail(product: HomeProductUiModel) {
            val action = HomeFragmentDirections.actionNavigationHomeToProductDetail(product)
            findNavController().navigate(action)
        }
    }
}