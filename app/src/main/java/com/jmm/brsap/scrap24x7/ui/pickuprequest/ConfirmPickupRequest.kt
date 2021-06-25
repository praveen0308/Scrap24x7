package com.jmm.brsap.scrap24x7.ui.pickuprequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentConfirmPickupRequestBinding
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequestItem
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.Constants.SDF_EDMY
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertMillisecondsToDate
import com.jmm.brsap.scrap24x7.viewmodel.PickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPickupRequest : BaseFragment<FragmentConfirmPickupRequestBinding>(FragmentConfirmPickupRequestBinding::inflate) {

    //ViewModels

    private val viewModel by activityViewModels<PickupRequestViewModel>()

    // Adapters
    private lateinit var selectedScarpItemAdapter: SelectedScarpItemAdapter

    // Variable
    private lateinit var selectedPickupRequestItems:List<PickupRequestItem>
    private var userId : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setActiveStep(2)
        setUpRVScrapItems()
        subscribeObservers()

        viewModel.getSelectedScrapItems()


        setPickupAddress(viewModel.getSelectedPickupAddress())

        binding.layoutScrapItems.btnAction.setOnClickListener {
            findNavController().navigate(R.id.action_confirmPickupRequest_to_selectPickupItem)
        }
        binding.layoutSelectedAddress.btnAction.setOnClickListener {

        }
        binding.layoutPickupDate.btnAction.setOnClickListener {
            findNavController().navigate(R.id.action_confirmPickupRequest_to_selectPickupDate)
        }


        binding.btnConfirm.setOnClickListener {
            val pickupRequest = PickupRequest(
                customer_id = userId,
                pickup_address_id = viewModel.getSelectedPickupAddress().id,
                location_id = viewModel.getSelectedPickupAddress().location_id,
                pickup_requested_date = convertMillisecondsToDate(viewModel.getSelectedPickupDate(),"yyyy-MM-dd"),
                pickup_status = 1,
                pickup_request_items = selectedPickupRequestItems
            )
            viewModel.raiseNewPickupRequest(pickupRequest)
        }
    }


    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            userId = it
        })
        viewModel.selectedPickupDate.observe(viewLifecycleOwner,{
            setPickupDateLayout(it)
        })
        viewModel.selectedItems.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        selectedPickupRequestItems = it.map {item->
                            PickupRequestItem(
                            item_id = item.id,
                                price = item.unit_price
                        ) }
                        selectedScarpItemAdapter.setItemList(it)
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

        viewModel.raisedPickupRequest.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Pickup request raised successfully !!!")
                        requireActivity().finish()
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

    private fun setUpRVScrapItems() {
        selectedScarpItemAdapter = SelectedScarpItemAdapter()

        binding.layoutScrapItems.apply {
            tvHeading.text = "Scrap items"
            rvData.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = selectedScarpItemAdapter
            }
        }

    }

    private fun setPickupDateLayout(time:Long){
        binding.layoutPickupDate.apply {
            tvHeading.text = "Pickup"
            tvTitle.text = convertMillisecondsToDate(time,SDF_EDMY)
            tvSubtitle.text = "10 AM - 6 PM"

        }
    }

    private fun setPickupAddress(customerAddress: CustomerAddress){
        binding.layoutSelectedAddress.apply {
            tvHeading.text = "Pickup Address"
            tvTitle.text = customerAddress.address1.toString()
        }
    }

}