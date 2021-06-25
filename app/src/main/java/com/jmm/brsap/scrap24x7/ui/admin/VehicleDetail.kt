package com.jmm.brsap.scrap24x7.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentUserDetailSheetBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentVehicleDetailBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertISOTimeToDateTime
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageVehicleViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VehicleDetail : BaseBottomSheetDialogFragment<FragmentVehicleDetailBinding>(
    FragmentVehicleDetailBinding::inflate
),
    MasterDetailAdapter.MasterDetailInterface {

    private lateinit var masterDetailAdapter: MasterDetailAdapter
    private val viewModel by viewModels<ManageVehicleViewModel>()
    private var vehicleId = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvDetail()
        vehicleId = requireArguments().getInt("VEHICLE_ID")
        viewModel.getVehicleById(vehicleId)

    }

    override fun subscribeObservers() {


        viewModel.vehicleMaster.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {


                        val details = mutableListOf<HeadingValueModel>()
                        details.add(
                            HeadingValueModel(
                                "Vehicle ID",
                                it.id.toString(),
                                OtherEnum.HORIZONTAL
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Owner Name",
                                it.owner_name.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Owner Address",
                                it.owner_address.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Plate No.",
                                it.vehicle_plate_no.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Color",
                                it.vehicle_color.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Vehicle Model",
                                it.vehicle_model.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Vehicle Number",
                                it.vehicle_number.toString()
                            )
                        )

                        details.add(
                            HeadingValueModel(
                                "Location",
                                it.location_name.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Year",
                                it.vehicle_year.toString()
                            )
                        )
                        details.add(
                            HeadingValueModel(
                                "Mobile Number",
                                it.owner_mobile_number.toString()
                            )
                        )

                        it.owner_email_id?.let { email ->
                            details.add(
                                HeadingValueModel(
                                    "Email Address",
                                    email
                                )
                            )
                        }

                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel(
                                    "Added On",
                                    formattedDate
                                )
                            }?.let { it2 -> details.add(it2) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)

                        setSheetTitle("Vehicle Detail")
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

    private fun setSheetTitle(title: String) {
        binding.layoutDetail.tvPageTitle.text = title
    }

    private fun setupRvDetail() {
        masterDetailAdapter = MasterDetailAdapter(this)
        binding.layoutDetail.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(requireActivity())
            val dividerItemDecoration = DividerItemDecoration(
                requireActivity(),
                layoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = masterDetailAdapter
        }
    }


}