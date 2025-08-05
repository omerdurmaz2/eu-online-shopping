package com.euonlineshopping.ui.checkout

import com.euonlineshopping.ui.base.BaseFragment
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.HomeProductUiModel
import com.euonlineshopping.domain.model.ProductsUiState
import com.euonlineshopping.ui.databinding.FragmentCheckoutBinding
import com.euonlineshopping.ui.databinding.FragmentHomeBinding
import com.euonlineshopping.ui.home.adapter.HomeProductsAdapter
import com.euonlineshopping.ui.home.adapter.ProductsCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    private val viewModel: CheckoutViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPay.setOnClickListener {
            viewModel.pay()
            Toast.makeText(context, "Satın alma başarılı", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}