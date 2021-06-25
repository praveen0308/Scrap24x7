package com.jmm.brsap.scrap24x7.ui.pickuprequest

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.adapters.PickupItemPaymentsAdapter
import com.jmm.brsap.scrap24x7.adapters.PickupTrackingAdapter
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentMasterPickupSummaryBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel
import com.jmm.brsap.scrap24x7.model.PickupTrackingStep
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.PickupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val PICKUP_ID = "pickupId"
private const val USER_TYPE = "userType"

@AndroidEntryPoint
class MasterPickupSummary :
    BaseFragment<FragmentMasterPickupSummaryBinding>(FragmentMasterPickupSummaryBinding::inflate),
    PickupItemPaymentsAdapter.PickupItemPaymentsInterface,
    MasterDetailAdapter.MasterDetailInterface {

    private val viewModel by viewModels<PickupDetailViewModel>()

    // Adapters
    private lateinit var selectedScarpItemAdapter: SelectedScarpItemAdapter
    private lateinit var paymentItemAdapter: PickupItemPaymentsAdapter
    private lateinit var pickupTrackingAdapter:PickupTrackingAdapter
    private lateinit var vehicleDetailsAdapter: MasterDetailAdapter
    // Variable
    private var pickupId: Int? = 0
    private lateinit var userType: UserEnum
    private lateinit var selectedPickupRequestItems: List<CategoryItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pickupId = it.getInt(PICKUP_ID)
            userType= it.getSerializable(USER_TYPE) as UserEnum

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRVScrapItems()
        setUpRVVehicleDetails()
//        viewModel.getPickupRequestDetail(pickupId!!)

        viewModel.getPickupRequestAllDetail(pickupId!!)
    }

    override fun subscribeObservers() {
//        viewModel.pickupRequest.observe(viewLifecycleOwner, { _result ->
//            when (_result.status) {
//                Status.SUCCESS -> {
//                    _result._data?.let {
//                        setPickupIdNStatus(it)
//                        setPickupAddress(it.customer_address!!)
//                        setPickupTiming(it.pickup_requested_date as String)
//
//                        selectedPickupRequestItems = it.pickup_request_items!!.map {item->
//                            item.category_item!!
//                        }
//                        setPickupItems(selectedPickupRequestItems)
//                    }
//
//                    displayLoading(false)
//                }
//                Status.LOADING -> {
//                    displayLoading(true)
//                }
//                Status.ERROR -> {
//                    displayLoading(false)
//                    _result.message?.let {
//                        displayError(it)
//                    }
//                }
//            }
//        })

        viewModel.pickupDetail.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        when(userType){
                            UserEnum.EXECUTIVE->{
                                setCustomerDetails(it.customer)
                                it.driver_master?.let { driver_master->
                                    setDriverDetails(driver_master)
                                }


                                it.warehouse?.let{warehouse ->
                                    setWarehouseDetails(warehouse)

                                }
                            }
                            UserEnum.CUSTOMER->{
                                it.executive_master?.let { executive_master->
                                    setExecutiveDetails(executive_master)
                                }
                                setPickupTracking(it.pickup_trackings)

                            }
                            UserEnum.DRIVER->{
                                setCustomerDetails(it.customer)
                                it.vehicle_master?.let {vehicle_master->
                                    setVehicleDetails(vehicle_master)
                                }
                            }
                            else->{
                                setCustomerDetails(it.customer)
                                it.executive_master?.let { executive_master->
                                    setExecutiveDetails(executive_master)
                                }
                                it.driver_master?.let { driver_master->
                                    setDriverDetails(driver_master)
                                }

                                it.warehouse?.let{warehouse ->
                                    setWarehouseDetails(warehouse)

                                }
                                setPickupTracking(it.pickup_trackings)
                                it.vehicle_master?.let {vehicle_master->
                                    setVehicleDetails(vehicle_master)
                                }
                            }
                        }
                        setPickupIdNStatus(it.pickup_request)
                        setPickupAddress(it.customer_address)

                        selectedPickupRequestItems = it.pickup_request.pickup_request_items!!.map {item->
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

    }


    private fun setPickupTracking(pickupTrackings:List<PickupTracking>){
        binding.layoutPickupTracking.isVisible = true
        val stepList= mutableListOf<PickupTrackingStep>()
        for (i in pickupTrackings.indices){
            val it = pickupTrackings[i]
            if (i==pickupTrackings.size-1){
                stepList.add(PickupTrackingStep(
                    title = it.status_name!!,
                    subtitle = "${it.comment} \n${convertISOTimeToAny(it.created_at.toString(),SDF_EdMyhmaa)}",
                    timestamp = convertISOTimeToAny(it.created_at.toString(),SDF_dM).toString(),
                    isActive = true
                ))

            }else{
                stepList.add(
                    PickupTrackingStep(
                        title = it.status_name!!,
                        subtitle = "${it.comment} \n${convertISOTimeToAny(it.created_at.toString(),SDF_EdMyhmaa)}",
                        timestamp = convertISOTimeToAny(it.created_at.toString(),SDF_dM).toString()
                    )

                )

            }

        }
        pickupTrackingAdapter = PickupTrackingAdapter(stepList)
        binding.slPickupTracking.setAdapter(pickupTrackingAdapter)
    }
    private fun setCustomerDetails(customer: Customer){
        binding.apply {
            layoutCustomerDetail.isVisible = true
            tvCustomerName.text = "${customer.first_name} ${customer.last_name}"
            tvMobileNumber.text = customer.mobile_number
        }
    }

    private fun setExecutiveDetails(executiveMaster: ExecutiveMaster){
        binding.apply {
            layoutExecutiveDetail.isVisible = true
            tvExecutiveName.text = "${executiveMaster.name} "
            tvExecutiveContact.text = executiveMaster.mobile_no
        }
    }


    private fun setDriverDetails(driverMaster: DriverMaster){
        binding.apply {
            layoutDriverDetail.isVisible = true
            tvDriverName.text = "${driverMaster.driver_name} "
            tvDriverName.text = driverMaster.driver_mobile_number1
        }
    }

    private fun setVehicleDetails(vehicleMaster: VehicleMaster){
        binding.layoutVehicleDetails.isVisible = true
        val details = mutableListOf<HeadingValueModel>()
        details.add(HeadingValueModel("Vehicle ID",vehicleMaster.id.toString(),OtherEnum.HORIZONTAL))
        details.add(HeadingValueModel("Owner Name",vehicleMaster.owner_name.toString()))
        details.add(HeadingValueModel("Plate No.",vehicleMaster.vehicle_plate_no.toString()))
        details.add(HeadingValueModel("Color",vehicleMaster.vehicle_color.toString()))
        details.add(HeadingValueModel("Vehicle Model",vehicleMaster.vehicle_model.toString()))
        details.add(HeadingValueModel("Year",vehicleMaster.vehicle_year.toString()))
        details.add(HeadingValueModel("Mobile Number",vehicleMaster.owner_mobile_number.toString()))
        vehicleMaster.created_at?.let { date ->
            convertISOTimeToDateTime(date)?.let { formattedDate ->
                HeadingValueModel("Added On",
                    formattedDate
                )
            }?.let { it2 -> details.add(it2) }
        }
        vehicleDetailsAdapter.setHeadingValueModelList(details)
    }

    private fun setWarehouseDetails(warehouse: Warehouse){
        binding.apply {

            tvWarehouse.text = warehouse.warehouse_name
        }
    }


    private fun setPickupItems(selectedPickupRequestItems: List<CategoryItem>) {
        binding.apply {
            layoutPickupItems.isVisible =true
        }
        selectedScarpItemAdapter.setItemList(selectedPickupRequestItems)
    }


    private fun setPickupIdNStatus(pickupRequest:PickupRequest){
        binding.apply {
            layoutHeader.isVisible = true
            tvPickupId.text = pickupRequest.pickup_id.toString()
            cpStatus.text = pickupRequest.status_name

//            ivPickupStatus.colorFilter(getColorAcStatus(pickupRequest.pickup_status!!))
        }
    }

    private fun setPickupAddress(customerAddress: CustomerAddress){
        binding.apply {
            layoutLocationDetail.isVisible = true
            tvPickupAddress.text = customerAddress.address1.toString()

        }
    }
    private fun setPickupTiming(date:String){
        binding.apply {
//            groupPickupTiming.isVisible = true
//            tvPickupTiming.text = "${convertYMD2MDY(date)} (10 AM - 6 PM)"

        }
    }

    private fun setUpRVVehicleDetails() {
        vehicleDetailsAdapter = MasterDetailAdapter(this)
        binding.rvVehicleDetails.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = vehicleDetailsAdapter
        }
    }


    private fun setUpRVScrapItems() {
        selectedScarpItemAdapter = SelectedScarpItemAdapter()
        binding.rvPickupItems.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,3)
            adapter = selectedScarpItemAdapter
        }
    }

    private fun setupPaymentItems() {
        paymentItemAdapter = PickupItemPaymentsAdapter(this)

        binding.rvPickupPayments.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = paymentItemAdapter

        }

    }
    companion object {

        @JvmStatic
        fun newInstance(pickupId: Int,userType:UserEnum) =
            MasterPickupSummary().apply {
                arguments = Bundle().apply {
                    putInt(PICKUP_ID, pickupId)
                    putSerializable(USER_TYPE,userType)
                }
            }
    }

}