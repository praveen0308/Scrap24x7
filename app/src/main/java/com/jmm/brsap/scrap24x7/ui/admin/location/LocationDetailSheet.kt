package com.jmm.brsap.scrap24x7.ui.admin.location

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentLocationDetailSheetBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentUserDetailSheetBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertISOTimeToDateTime
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageLocationViewModel
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageWarehouseViewModel
import dagger.hilt.android.AndroidEntryPoint

/*

Author : Praveen A. Yadav
Created On : 06:15 19-06-2021

*/
@AndroidEntryPoint
class LocationDetailSheet : BaseBottomSheetDialogFragment<FragmentLocationDetailSheetBinding>(
    FragmentLocationDetailSheetBinding::inflate),
    MasterDetailAdapter.MasterDetailInterface {

    private lateinit var masterDetailAdapter: MasterDetailAdapter
    private val locationViewModel by viewModels<ManageLocationViewModel>()
    private val warehouseViewModel by viewModels<ManageWarehouseViewModel>()
    private var componentId = 0
    private lateinit var type : AdminEnum
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvDetail()
        componentId = requireArguments().getInt("ID")
        type= requireArguments().getSerializable("TYPE") as AdminEnum

        when(type){
            AdminEnum.LOCATION->{
                locationViewModel.getLocationById(componentId)

            }
            AdminEnum.WAREHOUSE->{
                warehouseViewModel.getWarehouseById(componentId) }
            else->{
                //nothing
            }

        }
    }
    override fun subscribeObservers() {
        locationViewModel.locationMaster.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(
                            HeadingValueModel("Location ID",it.id.toString(),
                                OtherEnum.HORIZONTAL)
                        )
                        details.add(HeadingValueModel("Location Name",it.location_name!!))
                        setSheetTitle(it.location_name!!)
                        details.add(HeadingValueModel("Description",it.location_details!!))

                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Created On",
                                    formattedDate
                                )
                            }?.let { it2 -> details.add(it2) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)
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

        warehouseViewModel.warehouse.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(
                            HeadingValueModel("Warehouse ID",it.id.toString(),
                                OtherEnum.HORIZONTAL)
                        )
                        details.add(HeadingValueModel("Name",it.warehouse_name!!))
                        setSheetTitle(it.warehouse_name)
                        details.add(HeadingValueModel("Detail",it.warehouse_details!!))
                        details.add(HeadingValueModel("Location",it.location_name!!))


                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Created On",
                                    formattedDate
                                )
                            }?.let { model -> details.add(model) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)
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

    private fun setSheetTitle(title:String){
        binding.layoutDetail.tvPageTitle.text = title
    }
    private fun setupRvDetail(){
        masterDetailAdapter = MasterDetailAdapter(this)
        binding.layoutDetail.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(requireActivity())
            val dividerItemDecoration = DividerItemDecoration(requireActivity(),
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = masterDetailAdapter
        }
    }


}