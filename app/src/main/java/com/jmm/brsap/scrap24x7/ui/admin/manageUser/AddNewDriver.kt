package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAddNewDriverBinding
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AddNewDriverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewDriver : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityAddNewDriverBinding
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY
    private val viewModel by viewModels<AddNewDriverViewModel>()

    private var selectedLocationID = 0
    private var driverMasterId = 0
    private var locations= mutableListOf<LocationMaster>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        binding.toolbarAddNewDriver.setApplicationToolbarListener(this)

        source = intent.getSerializableExtra("ACTION") as AdminEnum
        viewModel.getLocations()
        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            driverMasterId = intent.getIntExtra("DriverID",0)
            binding.toolbarAddNewDriver.setToolbarTitle("Update Driver Details")
        }
        else{
            binding.toolbarAddNewDriver.setToolbarTitle("Add New Driver")
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val driverMaster = DriverMaster(
                    location_id = selectedLocationID,
                    driver_name = etDriverName.text.toString(),
                    driver_pan_number = etPanNumber.text.toString(),
                    driver_aadhar_number = etAadhaarNumber.text.toString(),
                    driver_email_id = etEmailAddress.text.toString(),
                    driver_mobile_number1 = etMobileNumber.text.toString(),
                    driver_mobile_number2 = etAlternateNumber.text.toString(),
//                    vehicle_state = etDriverState.text.toString(),
                    driver_address = etAddress.text.toString(),
                )
                if (source==AdminEnum.ADD){
                    viewModel.addNewDriver(driverMaster)
                }
                else{
                    viewModel.updateDriverInfo(driverMasterId,driverMaster)
                }
            }
        }
    }


    private fun subscribeObservers() {
        viewModel.driverMaster.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        if (source==AdminEnum.ADD){
                            val intent = Intent()
                            intent.putExtra("Message", "Added successfully !!!")
                            setResult(RESULT_OK, intent)
                        }
                        else{
                            val intent = Intent()
                            intent.putExtra("Message", "Updated successfully !!!")
                            setResult(RESULT_OK, intent)
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

        viewModel.driverMasterDetail.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateDriverInfo(it)
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
                        if (source==AdminEnum.EDIT) viewModel.getDriverById(driverMasterId)

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

    private fun populateDriverInfo(driverMaster: DriverMaster) {
        driverMasterId = driverMaster.id!!
        binding.apply {
            selectedLocationID = driverMaster.location_id!!

            actvLocation.setText((locations.find { it.id ==selectedLocationID })?.location_name)
            etDriverName.setText(driverMaster.driver_name)
            etAddress.setText(driverMaster.driver_address)
            etMobileNumber.setText(driverMaster.driver_mobile_number1)
            etAlternateNumber.setText(driverMaster.driver_mobile_number2)
            etEmailAddress.setText(driverMaster.driver_email_id)
            etPanNumber.setText(driverMaster.driver_pan_number)
            etAadhaarNumber.setText(driverMaster.driver_aadhar_number)
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