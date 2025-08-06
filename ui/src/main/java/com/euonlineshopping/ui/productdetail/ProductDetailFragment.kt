package com.euonlineshopping.ui.productdetail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.euonlineshopping.ui.R
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.databinding.FragmentProductDetailBinding
import com.euonlineshopping.ui.util.calculateDiscountedPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(null)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setupWithNavController(findNavController())

        lifecycleScope.launch {
            viewModel.product.collect { product ->
                product?.let { safeProduct ->
                    Glide.with(requireContext()).load(product.thumbnail)
                        .into(binding.ivProductImage)

                    binding.toolbar.title = product.title
                    binding.tvDiscountPercentage.text = "%${product.discountPercentage}"
                    binding.tvProductName.text = product.title
                    binding.tvProductDescription.text = product.description
                    binding.tvDiscountedPrice.text =
                        product.price.calculateDiscountedPrice(product.discountPercentage)
                            .toString()
                    binding.tvOriginalPrice.text = product.price.toString()
                    binding.tvOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                    val favoriteImage = if (product.isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_add_favorite

                    binding.ivFavorites.apply {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                favoriteImage
                            )
                        )

                        setOnClickListener {
                            viewModel.toggleFavorite()
                        }
                    }

                    binding.btnAddToCart.setOnClickListener {
                        viewModel.addToCart()
                        Toast.makeText(context, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
                    }

                    viewModel.listenProductFavoriteStatus()
                } ?: run {
                    findNavController().popBackStack()
                }
            }
        }
    }

    companion object {
        const val PRODUCT = "key_product"
    }
}