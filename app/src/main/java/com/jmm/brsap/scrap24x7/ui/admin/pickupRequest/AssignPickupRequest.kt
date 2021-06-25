package com.jmm.brsap.scrap24x7.ui.admin.pickupRequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAssignPickupRequestBinding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AssignPickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignPickupRequest : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityAssignPickupRequestBinding

    private val viewModel by viewModels<AssignPickupRequestViewModel>()

    //variable
    private var selectedVehicleMasterId= 0
    private var selectedDriverMasterId=0
    private var selectedExecutiveMasterId= 0
    private lateinit var pickupId : String

    private var locationId = 0
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignPickupRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAssignPickup.setApplicationToolbarListener(this)
        initialViewState()
        subscribeObservers()
        pickupId = intent.getStringExtra("PickupID").toString()
        id = intent.getIntExtra("ID",0)
        viewModel.getPickupRequestById(id)

        binding.btnSubmit.setOnClickListener {
            viewModel.acceptPickupRequest(pickupId,selectedDriverMasterId,selectedExecutiveMasterId,selectedVehicleMasterId)
        }

        binding.pickupVehicleDetail.apply {
            btnChoose.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.VEHICLES)
                bundle.putInt("LocationID",locationId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
            btnChange.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.VEHICLES)
                bundle.putInt("LocationID",locationId)
                bundle.putInt("ComponentID",selectedVehicleMasterId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
        }

        binding.pickupDriverDetail.apply {
            btnChoose.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.DRIVER)
                bundle.putInt("LocationID",locationId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
            btnChange.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.DRIVER)
                bundle.putInt("LocationID",locationId)
                bundle.putInt("ComponentID",selectedDriverMasterId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
        }

        binding.pickupExecutiveDetail.apply {
            btnChoose.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.EXECUTIVE)
                bundle.putInt("LocationID",locationId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
            btnChange.setOnClickListener {
                val bottomSheet = SelectAssignmentComponent()
                val bundle = Bundle()
                bundle.putSerializable("SOURCE",AdminEnum.EXECUTIVE)
                bundle.putInt("LocationID",locationId)
                bundle.putInt("ComponentID",selectedExecutiveMasterId)
                bottomSheet.arguments = bundle
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
        }


    }

    private fun subscribeObservers() {

        viewModel.message.observe(this,{
//            binding.tvPickupId.text = it
        })
        viewModel.selectedVehicle.observe(this,{
            selectedVehicleMasterId =it.componentId
            binding.apply {

                pickupVehicleDetail.selectedItemDetailLayout.isVisible=true

                enableSubmitButton()
                pickupVehicleDetail.apply {
                    tvNoItemChosen.isVisible = false
                    btnChoose.isVisible=false
                    btnChange.isVisible=true
                    tvMainTitle.text = it.title
                    tvSubTitle.text = it.subTitle
                    ivIcon.setImageResource(R.drawable.ic_round_local_shipping_24)
                }
            }
        })

        viewModel.selectedDriver.observe(this,{
            selectedDriverMasterId =it.componentId
            enableSubmitButton()
            binding.apply {
                pickupDriverDetail.selectedItemDetailLayout.isVisible=true
                pickupDriverDetail.apply {
                    tvNoItemChosen.isVisible = false
                    btnChoose.isVisible=false
                    btnChange.isVisible=true
                    tvMainTitle.text = it.title
                    tvSubTitle.text = it.subTitle
                    ivIcon.setImageResource(R.drawable.ic_steering_wheel)
                }
            }
        })

        viewModel.selectedExecutive.observe(this,{
            selectedExecutiveMasterId =it.componentId
            enableSubmitButton()
            binding.apply {
                pickupExecutiveDetail.selectedItemDetailLayout.isVisible=true
                pickupExecutiveDetail.apply {
                    tvNoItemChosen.isVisible = false
                    btnChoose.isVisible=false
                    btnChange.isVisible=true
                    tvMainTitle.text = it.title
                    tvSubTitle.text = it.subTitle
                    ivIcon.setImageResource(R.drawable.ic_baseline_engineering_24)
                }
            }
        })

        viewModel.pickupRequest.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        setPickupDetail(it)
                        locationId = it.location_id!!
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

        viewModel.acceptedPickupRequest.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val intent = Intent()
                        intent.putExtra("Message", "Updated successfully !!!")
                        setResult(RESULT_OK, intent)
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
    }

    private fun enableSubmitButton(){
        if (selectedVehicleMasterId!=0 && selectedExecutiveMasterId!=0 && selectedDriverMasterId!=0){
            binding.btnSubmit.isEnabled = true
        }
    }

    private fun setPickupDetail(pickupRequest: PickupRequest) {
        binding.apply {
            tvPickupId.text = pickupRequest.pickup_id.toString()
            tvPickupLocation.text = pickupRequest.customer_address?.address1.toString()
        }
    }

    private fun initialViewState() {
        binding.apply {

            pickupVehicleDetail.apply {
                tvTitle.text = "Pickup Vehicle"
                tvNoItemChosen.text = "No vehicles selected.."
                btnChoose.text = "Select Vehicle"
            }

            pickupDriverDetail.apply {
                tvTitle.text = "Pickup Driver"
                tvNoItemChosen.text = "No driver assigned."
                btnChoose.text = "Select Driver"
            }

            pickupExecutiveDetail.apply {
                tvTitle.text = "Pickup Executive"
                tvNoItemChosen.text = "No executive assigned.."
                btnChoose.text = "Select Executive"
            }
        }

    }

    override fun onToolbarNavClick() {
        val intent = Intent()
        setResult(RESULT_CANCELED,intent)
        finish()
    }

    override fun onMenuClick() {
    }


}