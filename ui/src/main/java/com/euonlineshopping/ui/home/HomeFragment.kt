package com.euonlineshopping.ui.home

import com.euonlineshopping.ui.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.ProductsUiState
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private var productsAdapter: HomeProductsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter = HomeProductsAdapter()
        binding.rvHomeProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeFragment.productsAdapter
        }

        lifecycleScope.launch {
            viewModel.screenState.collect {
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

    companion object {
        fun newInstance() = HomeFragment()
    }
}