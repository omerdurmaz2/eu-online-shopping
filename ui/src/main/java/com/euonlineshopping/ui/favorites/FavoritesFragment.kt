package com.euonlineshopping.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.databinding.FragmentFavoritesBinding
import com.euonlineshopping.ui.favorites.adapter.FavoriteProductsAdapter
import com.euonlineshopping.ui.home.adapter.ProductsCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()
    private var productsAdapter: FavoriteProductsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter = FavoriteProductsAdapter(
            productsCallback = productsCallbackListener
        )
        binding.rvFavoriteProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@FavoritesFragment.productsAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState.collect {
                when (val state = it) {
                    is ProductsUiState.Content -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.rvFavoriteProducts.visibility = View.VISIBLE
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
                        binding.rvFavoriteProducts.visibility = View.GONE
                    }
                }
            }
        }
    }

    private val productsCallbackListener = object : ProductsCallback {
        override fun navigateToDetail(product: HomeProductUiModel) {
            val action =
                FavoritesFragmentDirections.actionNavigationFavoritesToProductDetail(product)
            findNavController().navigate(action)
        }

        override fun toggleFavorite(product: HomeProductUiModel) {
            super.toggleFavorite(product)
            viewModel.toggleFavorite(product)
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}