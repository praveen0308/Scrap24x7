package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAddNewExecutiveBinding
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AddNewExecutiveViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewExecutive : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityAddNewExecutiveBinding
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY
    private val viewModel by viewModels<AddNewExecutiveViewModel>()

    private var selectedLocationID = 0
    private var executiveMasterId = 0
    private var locations= mutableListOf<LocationMaster>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewExecutiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        binding.toolbarAddNewExecutive.setApplicationToolbarListener(this)

        source = intent.getSerializableExtra("ACTION") as AdminEnum
        viewModel.getLocations()
        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            executiveMasterId = intent.getIntExtra("ExecutiveID",0)

        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val executiveMaster = ExecutiveMaster(
                    location_id = selectedLocationID,
                    name = etExecutiveName.text.toString(),
//                    executive_pan_number = etPanNumber.text.toString(),
                    aadhaar_number = etAadhaarNumber.text.toString(),
//                    executive_email_id = etEmailAddress.text.toString(),
                    mobile_no = etMobileNumber.text.toString(),
                    alternative_mobile_no = etAlternateNumber.text.toString(),
//                    vehicle_state = etExecutiveState.text.toString(),
                    address = etAddress.text.toString(),
                )
                if (source==AdminEnum.ADD){
                    viewModel.addNewExecutive(executiveMaster)
                }
                else{
                    viewModel.updateExecutiveInfo(executiveMasterId,executiveMaster)
                }




            }
        }
    }


    private fun subscribeObservers() {
        viewModel.executiveMaster.observe(this, { _result ->
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

        viewModel.executiveMasterDetail.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateExecutiveInfo(it)
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
                        if (source==AdminEnum.EDIT) viewModel.getExecutiveById(executiveMasterId)
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

    private fun populateExecutiveInfo(executiveMaster: ExecutiveMaster) {
        executiveMasterId = executiveMaster.id!!
        binding.apply {
            selectedLocationID = executiveMaster.location_id!!

            actvLocation.setText((locations.find { it.id ==selectedLocationID })?.location_name)
            etExecutiveName.setText(executiveMaster.name)
            etAddress.setText(executiveMaster.address)
            etMobileNumber.setText(executiveMaster.mobile_no)
            etAlternateNumber.setText(executiveMaster.alternative_mobile_no)
//            etEmailAddress.setText(executiveMaster.executive_email_id)
//            etPanNumber.setText(executiveMaster.executive_pan_number)
            etAadhaarNumber.setText(executiveMaster.aadhaar_number)
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