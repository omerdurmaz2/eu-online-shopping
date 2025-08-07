package com.euonlineshopping.ui.bottomsheet.sort

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.euonlineshopping.domain.model.sort.SortOptionUiModel
import com.euonlineshopping.ui.base.BaseBottomSheet
import com.euonlineshopping.ui.bottomsheet.sort.adapter.SortOptionsAdapter
import com.euonlineshopping.ui.bottomsheet.sort.adapter.SortOptionsCallBack
import com.euonlineshopping.ui.databinding.BottomSheetSortBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SortBottomSheet : BaseBottomSheet<BottomSheetSortBinding>(BottomSheetSortBinding::inflate) {
    private val viewModel: SortBottomSheetViewModel by viewModels()
    var applySortCallBack: ((SortOptionUiModel) -> Unit)? = null
    var clearSortCallBack: (() -> Unit)? = null

    private var sortOptionsAdapter: SortOptionsAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortOptionsAdapter = SortOptionsAdapter(
            filtersCallBack = filtersCallBack
        )
        binding.rvSortOptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sortOptionsAdapter
        }

        binding.btnClearFilter.setOnClickListener {
            clearSortCallBack?.invoke()
            dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.screenState.collect { options ->
                sortOptionsAdapter?.submitList(options)
            }
        }

        if (viewModel.selectedSortOption != null) {
            binding.btnClearFilter.visibility = View.VISIBLE
        }
    }

    private val filtersCallBack = object : SortOptionsCallBack {
        override fun onSelect(filter: SortOptionUiModel) {
            applySortCallBack?.invoke(filter)
            dismiss()
        }
    }

    companion object {
        const val SELECTED_SORT_OPTION = "SELECTED_SORT_OPTION "

        fun newInstance(
            selectedSortOption: SortOptionUiModel?,
            applySort: (SortOptionUiModel) -> Unit,
            clearSort: () -> Unit
        ): SortBottomSheet {
            return SortBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_SORT_OPTION, selectedSortOption)
                }
                applySortCallBack = applySort
                clearSortCallBack = clearSort
            }
        }
    }
}