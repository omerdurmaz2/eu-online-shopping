package com.euonlineshopping.ui.checkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.euonlineshopping.ui.R
import com.euonlineshopping.ui.base.BaseFragment
import com.euonlineshopping.ui.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    private val viewModel: CheckoutViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(null)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setupWithNavController(findNavController())

        binding.btnPay.setOnClickListener {
            if (validateForm()) {
                viewModel.pay()
                Toast.makeText(
                    context,
                    getString(R.string.checkout_success_message),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun validateForm(): Boolean {
        var isFormValid = true

        if (binding.etName.text.isNullOrBlank()) {
            binding.tilName.error = getString(R.string.checkout_name_error)
            isFormValid = false
        } else {
            binding.tilName.error = null
        }

        if (binding.etEmail.text.isNullOrBlank()) {
            binding.tilEmail.error = getString(R.string.checkout_email_error)
            isFormValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (binding.etPhone.text.isNullOrBlank()) {
            binding.tilPhone.error = getString(R.string.checkout_phone_error)
            isFormValid = false
        } else {
            binding.tilPhone.error = null
        }

        return isFormValid
    }
}