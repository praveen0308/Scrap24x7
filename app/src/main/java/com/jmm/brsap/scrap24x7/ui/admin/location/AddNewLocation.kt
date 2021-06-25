package com.jmm.brsap.scrap24x7.ui.admin.location

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewLocationBinding
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageLocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewLocation :
    BaseBottomSheetDialogFragment<FragmentAddNewLocationBinding>(FragmentAddNewLocationBinding::inflate) {

    private val viewModel by viewModels<ManageLocationViewModel>()
    private var location:LocationMaster?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments !=null){
            location = requireArguments().getSerializable("LOCATION") as LocationMaster
            populateViews(location!!)
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                if (location==null){
                    val locationMaster = LocationMaster(
                        location_name = etName.text.toString().trim(),
                        location_details = etDescription.text.toString().trim()
                    )
                    viewModel.addNewLocation(locationMaster)
                }else{
                    location!!.location_name=etName.text.toString().trim()
                    location!!.location_details=etDescription.text.toString().trim()
                    viewModel.updateLocation(location!!)
                }

            }
        }
    }

    private fun populateViews(location: LocationMaster) {
        binding.apply {
            tvPageTitle.text = "Update Location"
            etName.setText(location.location_name.toString())
            etDescription.setText(location.location_details.toString())
        }
    }

    override fun subscribeObservers() {
        viewModel.addedLocationMaster.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Added successfully!!!")
                        dismiss()
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

        viewModel.updatedLocationMaster.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated successfully!!!")
                        dismiss()
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


}