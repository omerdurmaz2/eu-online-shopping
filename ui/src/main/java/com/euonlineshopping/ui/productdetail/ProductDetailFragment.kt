package com.euonlineshopping.ui.productdetail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.euonlineshopping.ui.R
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.product.collect { product ->
                product?.let { safeProduct ->
                    Glide.with(requireContext()).load(product.thumbnail).into(binding.ivThumbnail)

                    binding.tvProductName.text = product.title

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