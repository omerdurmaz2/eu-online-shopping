package com.euonlineshopping.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}