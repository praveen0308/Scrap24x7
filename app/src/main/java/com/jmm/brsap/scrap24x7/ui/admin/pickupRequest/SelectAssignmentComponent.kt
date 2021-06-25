package com.jmm.brsap.scrap24x7.ui.admin.pickupRequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AssignmentComponentAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentSelectAssignmentComponentBinding
import com.jmm.brsap.scrap24x7.model.ModelAssignmentComponent
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AssignPickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectAssignmentComponent :
    BaseBottomSheetDialogFragment<FragmentSelectAssignmentComponentBinding>
        (FragmentSelectAssignmentComponentBinding::inflate),
    AssignmentComponentAdapter.AssignmentComponentInterface {

    private val viewModel by activityViewModels<AssignPickupRequestViewModel>()

    private lateinit var assignmentComponentAdapter:AssignmentComponentAdapter

    private lateinit var source:AdminEnum
    private var componentId = 0
    private var locationId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvComponents()
        componentId = requireArguments().getInt("ComponentID")
        locationId = requireArguments().getInt("LocationID")
        source = requireArguments().getSerializable("SOURCE") as AdminEnum

        when(source){
            AdminEnum.VEHICLES->viewModel.getVehiclesByLocationId(locationId)
            AdminEnum.DRIVER->viewModel.getDriversByLocationId(locationId)
            AdminEnum.EXECUTIVE->viewModel.getExecutivesByLocationId(locationId)
            else->{
                //nothing
            }
        }

        binding.btnOkay.setOnClickListener {
            dismiss()
        }

    }

    override fun subscribeObservers() {
        viewModel.vehicles.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val componentList = it.map { v->
                            ModelAssignmentComponent(
                                componentId = v.id!!,
                                type = AdminEnum.VEHICLES,
                                title = v.owner_name,
                                subTitle = v.vehicle_plate_no,
                                imageUrl = R.drawable.ic_round_local_shipping_24
                            )
                        }

                        componentList.find { c->c.componentId==componentId }?.isSelected=true

                        assignmentComponentAdapter.setModelAssignmentComponentList(componentList)
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

        viewModel.drivers.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val componentList = it.map { d->
                            ModelAssignmentComponent(
                                componentId = d.id!!,
                                type = AdminEnum.DRIVER,
                                title = d.driver_name,
                                subTitle = "ID: ${d.id}",
                                imageUrl = R.drawable.ic_steering_wheel
                            )
                        }

                        componentList.find { c->c.componentId==componentId }?.isSelected=true

                        assignmentComponentAdapter.setModelAssignmentComponentList(componentList)
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

        viewModel.executives.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val componentList = it.map { e->
                            ModelAssignmentComponent(
                                componentId = e.id!!,
                                type = AdminEnum.EXECUTIVE,
                                title = e.name,
                                subTitle = "ID: ${e.id}",
                                imageUrl = R.drawable.ic_baseline_engineering_24
                            )
                        }

                        componentList.find { c->c.componentId==componentId }?.isSelected=true

                        assignmentComponentAdapter.setModelAssignmentComponentList(componentList)
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

    private fun setRvComponents() {

        assignmentComponentAdapter = AssignmentComponentAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager

            adapter = assignmentComponentAdapter
        }
    }

    override fun onItemClick(item: ModelAssignmentComponent) {
        when(item.type){
            AdminEnum.VEHICLES->viewModel.setSelectedVehicle(item)
            AdminEnum.DRIVER->viewModel.setSelectedDriver(item)
            AdminEnum.EXECUTIVE->viewModel.setSelectedExecutive(item)
            else->{
                //nothing
            }
        }
    }

}