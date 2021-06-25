package com.jmm.brsap.scrap24x7.ui.executive

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityPickupActionBinding
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.util.Constants.OUT_FOR_PICKUP
import com.jmm.brsap.scrap24x7.viewmodel.executive.PickupActionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickupAction : BaseActivity() {

    private lateinit var binding:ActivityPickupActionBinding
    private val viewModel by viewModels<PickupActionViewModel>()

    // Adapters
    private lateinit var selectedScarpItemAdapter: SelectedScarpItemAdapter
    private lateinit var paymentItemAdapter: SelectedScarpItemAdapter

    //Variables
    private var pickupId=0
    private lateinit var latitude:String
    private lateinit var longitude:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupActionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        setUpRVScrapItems()
        pickupId = intent.getIntExtra("ID",0)
//        showFragment(MasterPickupSummary.newInstance(pickupId))
        viewModel.getPickupRequestDetail(pickupId)
        binding.apply {
            btnOutForPickup.setOnClickListener {
                viewModel.updatePickupStatus(pickupId,OUT_FOR_PICKUP)
            }

            btnDirections.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=$latitude,$longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

            btnStartCollection.setOnClickListener {
                val intent = Intent(this@PickupAction,PickupCollection::class.java)
                intent.putExtra("ID",pickupId)
                startActivity(intent)
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.pickupRequest.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        setPickupIdNStatus(it)
                        setPickupAddress(it.customer_address!!)
                        setPickupTiming(it.pickup_requested_date as String)
                        val selectedPickupRequestItems = it.pickup_request_items!!.map {item->
                            item.category_item!!
                        }
                        setPickupItems(selectedPickupRequestItems)


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

        viewModel.pickupStatus.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated Successfully !!!")
                        viewModel.getPickupRequestDetail(pickupId)
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

    private fun setPickupItems(selectedPickupRequestItems: List<CategoryItem>) {
        binding.layoutPickupSummary.apply {
            groupPickupItems.isVisible = true
        }
        selectedScarpItemAdapter.setItemList(selectedPickupRequestItems)
    }


    private fun setPickupIdNStatus(pickupRequest: PickupRequest){
        binding.layoutPickupSummary.apply {
            groupPickupIdNStauts.isVisible = true
            tvPickupId.text = pickupRequest.pickup_id.toString()
            tvPickupStatus.text = pickupRequest.status_name
            ivPickupStatus.colorFilter(getColorAcStatus(pickupRequest.pickup_status!!))


        }
        binding.apply {
            when(pickupRequest.pickup_status){
                5->{
                    groupPickupAction.isVisible = true
                    btnOutForPickup.isVisible = false
                }
                6->{
                    groupPickupAction.isVisible = false
                    btnOutForPickup.isVisible = false
                }
                else->{
                    groupPickupAction.isVisible = false
                }
            }
        }
    }

    private fun setPickupAddress(customerAddress: CustomerAddress){
        latitude = customerAddress.latitude.toString()
        longitude = customerAddress.longitude.toString()
        binding.layoutPickupSummary.apply {
            groupPickupLocation.isVisible = true
            tvPickupLocation.text = customerAddress.address1.toString()
            ivLocationDirection.isVisible = false
        }

    }
    private fun setPickupTiming(date:String){
        binding.layoutPickupSummary.apply {
            groupPickupTiming.isVisible = true
            tvPickupTiming.text = "${convertYMD2MDY(date)} (10 AM - 6 PM)"

        }
    }


    private fun setUpRVScrapItems() {
        selectedScarpItemAdapter = SelectedScarpItemAdapter()
        binding.layoutPickupSummary.rvPickupItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = selectedScarpItemAdapter
        }
    }

    private fun setupPaymentItems() {
        paymentItemAdapter = SelectedScarpItemAdapter()

        binding.layoutPickupSummary.rvPickupItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = selectedScarpItemAdapter

        }

    }
}