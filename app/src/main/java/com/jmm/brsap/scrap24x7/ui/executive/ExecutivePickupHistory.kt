package com.jmm.brsap.scrap24x7.ui.executive

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ExecutivePickupHistoryAdapter
import com.jmm.brsap.scrap24x7.adapters.ManagePickupRequestAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentExecutivePickupHistoryBinding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.admin.pickupRequest.AssignPickupRequest
import com.jmm.brsap.scrap24x7.ui.admin.pickupRequest.ManagePickupRequestFilter
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManagePickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExecutivePickupHistory : BaseFragment<FragmentExecutivePickupHistoryBinding>(
    FragmentExecutivePickupHistoryBinding::inflate),
    ManagePickupRequestFilter.ManagePickupRequestFilterInterface,
    ExecutivePickupHistoryAdapter.ManagePickupRequestInterface {

    private val viewModel by activityViewModels<ManagePickupRequestViewModel>()

    private lateinit var managePickupRequestAdapter: ExecutivePickupHistoryAdapter

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // For filtering purpose
    private lateinit var startDate :String
    private lateinit var endDate :String
    private var activeRequestTypeFilter = FilterEnum.ALL
    private var locationIds = mutableListOf<Int>()
    private val pickupRequests = mutableListOf<PickupRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode == Activity.RESULT_OK) {
                    showToast(data!!.getStringExtra("Message")!!)
                } else {
                    showToast("Cancelled !!")
                }

            }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvPickupRequests()

        binding.btnFilter.setOnClickListener {
            val bottomSheet = ManagePickupRequestFilter()
            bottomSheet.setPickupRequestFilterListener(this)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getPickupHistoryList(getDateRange(FilterEnum.LAST_MONTH), getTodayDate(),locationIds)
            binding.refreshLayout.isRefreshing = false
        }


    }
    private fun setRvPickupRequests() {
        managePickupRequestAdapter = ExecutivePickupHistoryAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = managePickupRequestAdapter
        }
    }

    override fun subscribeObservers() {
        viewModel.selectedPickupRequestType.observe(viewLifecycleOwner,{
            activeRequestTypeFilter = it
            populatePickupRequests()
        })

        viewModel.selectedTimeFilter.observe(viewLifecycleOwner,{
            when(it){
                FilterEnum.LAST_MONTH, FilterEnum.LAST_WEEK, FilterEnum.YESTERDAY, FilterEnum.TODAY->{
                    startDate= getDateRange(it)
                    endDate= getTodayDate()
                    binding.tvDateInfo.text = getDateLabelAcToFilter(it)
                }
                FilterEnum.TOMORROW, FilterEnum.THIS_WEEK, FilterEnum.THIS_MONTH->{
                    startDate= getTodayDate()
                    endDate= getDateRange(it)
                    binding.tvDateInfo.text = getDateLabelAcToFilter(it)
                }

                FilterEnum.CUSTOM->{
                    val materialDateBuilder =
                        MaterialDatePicker.Builder.dateRangePicker()
                    materialDateBuilder.setTitleText("SELECT A DATE")

                    val materialDatePicker = materialDateBuilder.build()
                    materialDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")

                    materialDatePicker.addOnPositiveButtonClickListener { selection ->
                        startDate = convertMillisecondsToDate(selection.first, "yyyy-MM-dd")
                        endDate = convertMillisecondsToDate(selection.second, "yyyy-MM-dd")
                        binding.tvDateInfo.text = "From ${convertYMD2Any(startDate,"dd MMM,yyyy")} to ${convertYMD2Any(endDate,"dd MMM,yyyy")}"

                    }
                }
            }

            viewModel.getPickupHistoryList(startDate, endDate,locationIds)
        })
        viewModel.selectedLocationIds.observe(viewLifecycleOwner,{
            locationIds = it.toMutableList()
            viewModel.getPickupHistoryList(startDate, endDate,locationIds)
        })

        viewModel.pickupRequestList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        pickupRequests.clear()
                        pickupRequests.addAll(it)
                        populatePickupRequests()
                        if (it.isEmpty()){
                            binding.myEmptyView.visibility = View.VISIBLE
                        }
                        else{

                            binding.myEmptyView.visibility = View.GONE
                        }

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

        viewModel.pickupStatus.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated Successfully !!!")
                        populatePickupRequests()
//                        viewModel.getPickupHistoryList(getDateRange(FilterEnum.LAST_WEEK), getTodayDate(),locationIds)
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

    private fun populatePickupRequests(){
        when(activeRequestTypeFilter){
            FilterEnum.ALL->{
                managePickupRequestAdapter.setPickupRequestList(pickupRequests)
            }
            FilterEnum.PICKUP->{
                val filteredList = mutableListOf<PickupRequest>()
                val datesList = getDatesList(startDate,endDate)

                for (request in pickupRequests){
                    if(datesList.contains(request.pickup_requested_date)){
                        filteredList.add(request)
                    }
                }
                managePickupRequestAdapter.setPickupRequestList(filteredList)
            }
            FilterEnum.RAISED->{
                val filteredList = mutableListOf<PickupRequest>()
                val datesList = getDatesList(startDate,endDate)

                for (request in pickupRequests){
                    if(datesList.contains(request.raised_on)){
                        filteredList.add(request)
                    }
                }
                managePickupRequestAdapter.setPickupRequestList(filteredList)
            }
        }
    }


    override fun onRejected(request: PickupRequest) {
        request.id?.let { viewModel.updatePickupStatus(it,3) }
    }

    override fun onAccepted(request: PickupRequest) {
        val intent = Intent(requireActivity(), AssignPickupRequest::class.java)
        intent.putExtra("PickupID", request.pickup_id)
        intent.putExtra("ID", request.id)
        resultLauncher.launch(intent)

//        request.pickup_id?.let { viewModel.updatePickupStatus(it,2) }
    }

    override fun onHold(request: PickupRequest) {
        request.id?.let { viewModel.updatePickupStatus(it,4) }
    }

    override fun onSheetSubmitted() {
//        viewModel.selectedLocationIds.observe(viewLifecycleOwner,{
//            locationIds = it.toMutableList()
//        })
//
//        viewModel.selectedTimeFilter.observe(viewLifecycleOwner,{
//            if (it == FilterEnum.CUSTOM) {
//                val materialDateBuilder =
//                    MaterialDatePicker.Builder.dateRangePicker()
//                materialDateBuilder.setTitleText("SELECT A DATE")
//
//                val materialDatePicker = materialDateBuilder.build()
//                materialDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
//
//                materialDatePicker.addOnPositiveButtonClickListener { selection ->
//                    val startDate = convertMillisecondsToDate(selection.first, "yyyy-MM-dd")
//                    val endDate = convertMillisecondsToDate(selection.second, "yyyy-MM-dd")
//
//                    viewModel.getPickupHistoryList(startDate, endDate,locationIds)
//
//                    //Do something...
//                }
//            } else {
//                viewModel.getPickupHistoryList(getDateRange(it), getTodayDate(),locationIds)
//            }
//        })
//

    }

}