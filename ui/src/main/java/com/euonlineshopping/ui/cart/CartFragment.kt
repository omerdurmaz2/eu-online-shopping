package com.euonlineshopping.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.CartUiState
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.cart.adapter.CartProductsAdapter
import com.euonlineshopping.ui.cart.adapter.CartProductsCallback
import com.euonlineshopping.ui.databinding.FragmentCartBinding
import com.euonlineshopping.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()
    private var productsAdapter: CartProductsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsAdapter = CartProductsAdapter(productsCallback = productsCallbackListener)

        binding.rvCartProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CartFragment.productsAdapter
        }

        binding.btnCheckout.setOnClickListener {
            val action = CartFragmentDirections.actionNavigationCartToCheckoutFragment()
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState.collect {
                when (val state = it) {
                    is CartUiState.Content -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.rlContent.visibility = View.VISIBLE
                        binding.llError.visibility = View.GONE
                        binding.llEmpty.visibility = View.GONE
                        productsAdapter?.submitList(state.products)
                    }

                    is CartUiState.Error -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llError.visibility = View.VISIBLE
                        binding.rlContent.visibility = View.GONE
                        binding.llEmpty.visibility = View.GONE
                    }

                    CartUiState.Empty -> {
                        binding.cpLoading.visibility = View.GONE
                        binding.llEmpty.visibility = View.VISIBLE
                        binding.rlContent.visibility = View.GONE
                        binding.llError.visibility = View.GONE
                    }

                    CartUiState.Loading -> {
                        binding.cpLoading.visibility = View.VISIBLE
                        binding.llError.visibility = View.GONE
                        binding.rlContent.visibility = View.GONE
                        binding.llEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private val productsCallbackListener = object : CartProductsCallback {
        override fun increaseCount(product: HomeProductUiModel) {
            viewModel.increaseCount(product)
        }

        override fun decreaseCount(product: HomeProductUiModel) {
            viewModel.decreaseCount(product)
        }

        override fun removeProduct(product: HomeProductUiModel) {
            viewModel.removeProduct(product)
        }

        override fun navigateToDetail(product: HomeProductUiModel) {
            val action = CartFragmentDirections.actionNavigationCartToProductDetail(product)
            findNavController().navigate(action)
        }
    }
}