package com.jmm.brsap.scrap24x7.ui.admin.pickupRequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ChipFilterChildAdapter
import com.jmm.brsap.scrap24x7.adapters.ChipFilterParentAdapter
import com.jmm.brsap.scrap24x7.adapters.ManagePickupRequestAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManagePickupRequestFilterBinding
import com.jmm.brsap.scrap24x7.model.FilterModel
import com.jmm.brsap.scrap24x7.model.ParentFilterModel
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.FilterEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManagePickupRequestFilterViewModel
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManagePickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagePickupRequestFilter : BaseBottomSheetDialogFragment<FragmentManagePickupRequestFilterBinding>(FragmentManagePickupRequestFilterBinding::inflate),
    ChipFilterChildAdapter.ChipFilterChildInterface {

//    private val viewModel by viewModels<ManagePickupRequestFilterViewModel>()
    private val viewModel by activityViewModels<ManagePickupRequestViewModel>()

    private lateinit var managePickupRequestFilterInterface:ManagePickupRequestFilterInterface

    private lateinit var chipFilterParentAdapter:ChipFilterParentAdapter

    private lateinit var selectedTimeFilter : FilterEnum

    private lateinit var selectedLocationIds : List<Int>

    private val timeFilters = mutableListOf<FilterModel>()
    private val locationFilters = mutableListOf<FilterModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRvFilters()



        binding.btnSubmit.setOnClickListener {
            timeFilters.forEach {
                if (it.isSelected){
                    selectedTimeFilter = it.filterId!!
                }
            }
            val locationIDs = mutableListOf<Int>()

            for (location in locationFilters){
                if (location.isSelected) locationIDs.add(location.id!!)
            }

            selectedLocationIds = locationIDs

            viewModel.assignSelectedTimeFilter(selectedTimeFilter)
            viewModel.setSelectedLocationIds(selectedLocationIds)
            dismiss()
            managePickupRequestFilterInterface.onSheetSubmitted()
        }
    }

    fun setPickupRequestFilterListener(mListener:ManagePickupRequestFilterInterface){
        managePickupRequestFilterInterface = mListener

    }
    override fun subscribeObservers() {

        viewModel.selectedTimeFilter.observe(viewLifecycleOwner,{
            selectedTimeFilter = it
            viewModel.getTimeFilters()
        })

        viewModel.selectedLocationIds.observe(viewLifecycleOwner,{
            selectedLocationIds = it
            viewModel.getLocations()
        })
        viewModel.timeFilters.observe(viewLifecycleOwner, {
           timeFilters.clear()
           timeFilters.addAll(it)
        })

        viewModel.locations.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        val filterList = it.map { location->
                            FilterModel(
                                parentId = FilterEnum.LOCATION_FILTER,
                                id = location.id,
                                title = location.location_name!!
                            )
                        }


                        for (location in filterList){
                            if (selectedLocationIds.isNotEmpty()){
                                for (selectedLocation in selectedLocationIds){
                                    if (location.id == selectedLocation){
                                        location.isSelected = true
                                    }
                                }
                            }

                        }

                        locationFilters.clear()
                        locationFilters.addAll(filterList)

                        prepareFilterList()
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

    private fun prepareFilterList(){
        val filterList = mutableListOf<ParentFilterModel>()
        filterList.add(ParentFilterModel(FilterEnum.TIME_FILTER,"By Time",timeFilters,true))
        filterList.add(ParentFilterModel(FilterEnum.LOCATION_FILTER,"By Location",locationFilters,false))
        chipFilterParentAdapter.setParentFilterModelList(filterList)
    }

    private fun setUpRvFilters() {

        chipFilterParentAdapter = ChipFilterParentAdapter(this)
        binding.rvFilters.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
//            val dividerItemDecoration = DividerItemDecoration(context,
//                layoutManager.orientation)
//            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager

            adapter = chipFilterParentAdapter
        }
    }

    override fun onItemClick(itemList: List<FilterModel>) {

        when(itemList[0].parentId){
            FilterEnum.TIME_FILTER->{
                timeFilters.clear()
                timeFilters.addAll(itemList)
//                selectedTimeFilter = item.filterId!!
            }
            FilterEnum.LOCATION_FILTER->{
                locationFilters.clear()
                locationFilters.addAll(itemList)
            }
        }
    }

//    override fun onItemClick(item: FilterModel) {
//        when(item.parentId){
//            FilterEnum.TIME_FILTER->{
//                selectedTimeFilter = item.filterId!!
//            }
//            FilterEnum.LOCATION_FILTER->{
//
//            }
//        }

//    }

    interface ManagePickupRequestFilterInterface{
        fun onSheetSubmitted()
    }
}