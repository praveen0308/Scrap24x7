package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.databinding.FragmentSellItemBinding
import com.jmm.brsap.scrap24x7.util.ProgressBarHandler
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.SellItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SellItem : Fragment() {

    //UI
    private var _binding: FragmentSellItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBarHandler: ProgressBarHandler

    //ViewModels
    private val categoryViewModel by viewModels<SellItemViewModel>()

    // Adapters


    // Variable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        progressBarHandler = ProgressBarHandler(requireActivity())
        _binding = FragmentSellItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        categoryViewModel.getScrapCategories()

    }

    private fun subscribeObservers() {

        categoryViewModel.categoryItemList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        Timber.d(it.toString())
                    }

                    displayLoading(false)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    _result.message?.let {
                        displayError(it)
                    }
                }
            }
        })
    }


    private fun displayLoading(state: Boolean) {

        if (state) progressBarHandler.show() else progressBarHandler.hide()
    }
    private fun displayRefreshing(loading: Boolean) {
//        binding.swipeRefreshLayout.isRefreshing = loading
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }
}