package com.jmm.brsap.scrap24x7.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAddNewVehicleBinding
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AddNewVehicleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewVehicle : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityAddNewVehicleBinding
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY
    private val viewModel by viewModels<AddNewVehicleViewModel>()
    private var selectedLocationID = 0
    private var vehicleMasterId = 0
    private var locations= mutableListOf<LocationMaster>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()

        binding.toolbarAddNewVehicle.setApplicationToolbarListener(this)
        viewModel.getLocations()

        source = intent.getSerializableExtra("ACTION") as AdminEnum

        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            viewModel.getVehicleById(intent.getIntExtra("VehicleID",0))
        }


        binding.apply {
            btnSubmit.setOnClickListener {
                val vehicleMaster = VehicleMaster(
                    location_id = selectedLocationID,
                    owner_name = etOwnerName.text.toString(),
                    owner_address = etOwnerAddress.text.toString(),
                    owner_mobile_number = etMobileNumber.text.toString(),
                    owner_email_id = etEmailAddress.text.toString(),
                    vehicle_number = etVehicleNumber.text.toString(),
                    vehicle_plate_no = etPlateNumber.text.toString(),
                    vehicle_state = etVehicleState.text.toString(),
                    chassis_number = etChassisNumber.text.toString(),
                    vehicle_color = etVehicleColor.text.toString(),
                    vehicle_make = etVehicleManufacturer.text.toString(),
                    vehicle_model = etVehicleModel.text.toString(),
                    vehicle_year = etVehicleYear.text.toString()
                )
                if (source==AdminEnum.ADD){
                    viewModel.addNewVehicle(vehicleMaster)
                }
                else{
                    viewModel.updateVehicleInfo(vehicleMasterId,vehicleMaster)
                }




            }
        }
    }


    private fun subscribeObservers() {
        viewModel.vehicleMaster.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        if (source==AdminEnum.ADD){
                            val intent = Intent()
                            intent.putExtra("Message", "Added successfully !!!")
                            setResult(ADD_VEHICLE, intent)
                            finish()
                        }
                        else{
                            val intent = Intent()
                            intent.putExtra("Message", "Updated successfully !!!")
                            setResult(UPDATE_VEHICLE, intent)
                            finish()
                        }

                        finish()
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

        viewModel.vehicleMasterDetail.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateVehicleInfo(it)
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

    private fun populateVehicleInfo(vehicleMaster: VehicleMaster) {
        vehicleMasterId = vehicleMaster.id!!
        binding.apply {
            selectedLocationID = vehicleMaster.location_id!!

            actvLocation.setText((locations.find { it.id ==selectedLocationID })?.location_name)
            etOwnerName.setText(vehicleMaster.owner_name)
            etOwnerAddress.setText(vehicleMaster.owner_address)
            etMobileNumber.setText(vehicleMaster.owner_mobile_number)
            etEmailAddress.setText(vehicleMaster.owner_email_id)
            etVehicleNumber.setText(vehicleMaster.vehicle_number)
            etPlateNumber.setText(vehicleMaster.vehicle_plate_no)
            etVehicleState.setText(vehicleMaster.vehicle_state)
            etChassisNumber.setText(vehicleMaster.chassis_number)
            etVehicleColor.setText(vehicleMaster.vehicle_color)
            etVehicleManufacturer.setText(vehicleMaster.vehicle_make)
            etVehicleModel.setText(vehicleMaster.vehicle_model)
            etVehicleYear.setText(vehicleMaster.vehicle_year)
        }
    }

    private fun populateLocationDropdown(mList: List<LocationMaster>){
        val arrayAdapter = ArrayAdapter(this, R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvLocation.threshold = 100 //start searching for values after typing first character
        binding.actvLocation.setAdapter(arrayAdapter)

        binding.actvLocation.setOnItemClickListener { parent, view, position, id ->
            selectedLocationID = (parent.getItemAtPosition(position) as LocationMaster).id!!

        }
    }

    override fun onToolbarNavClick() {

        finish()
    }

    override fun onMenuClick() {

    }
}