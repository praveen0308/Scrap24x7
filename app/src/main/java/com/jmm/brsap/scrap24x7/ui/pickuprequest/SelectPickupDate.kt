package com.jmm.brsap.scrap24x7.ui.pickuprequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentSelectPickupDateBinding
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.Constants.SDF_EDMY
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertMillisecondsToDate
import com.jmm.brsap.scrap24x7.util.limitRange
import com.jmm.brsap.scrap24x7.viewmodel.PickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPickupDate : BaseFragment<FragmentSelectPickupDateBinding>(FragmentSelectPickupDateBinding::inflate) {

    //ViewModels
    private val viewModel by activityViewModels<PickupRequestViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setActiveStep(1)
        binding.btnSelectDate.isEnabled =true

        binding.btnSelectDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select pickup date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(limitRange().build())
                    .build()

            datePicker.show(parentFragmentManager,"pickup_date")

            datePicker.addOnPositiveButtonClickListener {

                viewModel.setSelectedPickupDate(it)
                binding.tvSelectedPickupDate.text = "Pickup On  ${convertMillisecondsToDate(it,SDF_EDMY)}"
            }
        }


        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_selectPickupDate_to_confirmPickupRequest)
        }
    }

    override fun subscribeObservers() {
        viewModel.selectedPickupDate.observe(viewLifecycleOwner, {
            binding.tvSelectedPickupDate.text = convertMillisecondsToDate(it,SDF_EDMY)
        })
    }
}