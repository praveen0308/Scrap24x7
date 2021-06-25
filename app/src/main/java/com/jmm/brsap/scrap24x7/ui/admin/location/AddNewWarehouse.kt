package com.jmm.brsap.scrap24x7.ui.admin.location

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewWarehouseBinding
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.Warehouse
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageWarehouseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewWarehouse : BaseBottomSheetDialogFragment<FragmentAddNewWarehouseBinding>(FragmentAddNewWarehouseBinding::inflate) {

    private val viewModel by viewModels<ManageWarehouseViewModel>()

    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY

    //------
    private var selectedLocationID = 0
    private var warehouseId = 0

    private var locations= mutableListOf<LocationMaster>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        source = requireArguments().getSerializable("ACTION") as AdminEnum
        viewModel.getLocations()
        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            warehouseId = requireArguments().getInt("WarehouseID",0)
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val warehouse = Warehouse(
                    location_id = selectedLocationID,
                    warehouse_name = etName.text.toString(),
                    warehouse_details = etDescription.text.toString(),
                )
                if (source==AdminEnum.ADD){
                    viewModel.addNewWarehouse(warehouse)
                }
                else{
                    viewModel.updateWarehouse(warehouseId,warehouse)
                }

            }
        }
    }

    override fun subscribeObservers() {

        viewModel.addedWarehouse.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Added Successfully!!!")
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

        viewModel.updatedWarehouse.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated Successfully!!!")
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

        viewModel.warehouse.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateWarehouseInfo(it)
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

        viewModel.locations.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        locations.clear()
                        locations.addAll(it)
                        populateLocationDropdown(locations)
                        if (source==AdminEnum.EDIT) viewModel.getWarehouseById(warehouseId)

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


    //------
    private fun populateWarehouseInfo(warehouse: Warehouse) {
        warehouseId = warehouse.id!!
        binding.apply {
            selectedLocationID = warehouse.location_id!!

            actvLocation.setText((locations.find { it.id ==selectedLocationID })?.location_name)
            etName.setText(warehouse.warehouse_name)
            etDescription.setText(warehouse.warehouse_details)

        }
    }

    //------
    private fun populateLocationDropdown(mList: List<LocationMaster>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvLocation.threshold = 100 //start searching for values after typing first character
        binding.actvLocation.setAdapter(arrayAdapter)

        binding.actvLocation.setOnItemClickListener { parent, view, position, id ->
            selectedLocationID = (parent.getItemAtPosition(position) as LocationMaster).id!!

        }
    }




}