package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentUserDetailSheetBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.ui.admin.ADD_VEHICLE
import com.jmm.brsap.scrap24x7.ui.admin.UPDATE_VEHICLE
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertISOTimeToDateTime
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

/*

Author : Praveen A. Yadav
Created On : 04:56 19-06-2021

*/
@AndroidEntryPoint
class UserDetailSheet : BaseBottomSheetDialogFragment<FragmentUserDetailSheetBinding>(FragmentUserDetailSheetBinding::inflate),
    MasterDetailAdapter.MasterDetailInterface {

    private lateinit var masterDetailAdapter: MasterDetailAdapter
    private val viewModel by viewModels<ManageUserActivityViewModel>()
    private var userId = 0
    private lateinit var userType :AdminEnum
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvDetail()
        userId = requireArguments().getInt("USER_ID")
        userType= requireArguments().getSerializable("USER_TYPE") as AdminEnum

        when(userType){
            AdminEnum.CUSTOMER->{
                viewModel.getCustomerDetailById(userId)

            }
            AdminEnum.EXECUTIVE->{
                viewModel.getExecutiveDetailById(userId) }
            AdminEnum.DRIVER->{
                viewModel.getDriverDetailById(userId) }
            else->{
                //nothing
                 }

        }
    }
    override fun subscribeObservers() {
        viewModel.customer.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(HeadingValueModel("Customer ID",it.customer_id.toString(),OtherEnum.HORIZONTAL))
                        details.add(HeadingValueModel("Name",it.first_name+it.last_name))
                        setSheetTitle(it.first_name+it.last_name)
                        details.add(HeadingValueModel("Email",it.email_id))
                        details.add(HeadingValueModel("Mobile Number",it.mobile_number))
                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Registration Date",
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

        viewModel.executiveDetail.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(HeadingValueModel("Executive ID",it.id.toString(),OtherEnum.HORIZONTAL))
                        details.add(HeadingValueModel("Name",it.name!!))
                        setSheetTitle(it.name)
                        details.add(HeadingValueModel("Address",it.address!!))
                        details.add(HeadingValueModel("Location",it.location_name!!))
                        details.add(HeadingValueModel("Mobile Number",it.mobile_no!!))

                        it.alternative_mobile_no?.let { number->
                            details.add(HeadingValueModel("Alternative Mobile No.",number))
                        }
                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Registration Date",
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

        viewModel.driver.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        val details = mutableListOf<HeadingValueModel>()

                        details.add(HeadingValueModel("Driver ID",it.id.toString(),OtherEnum.HORIZONTAL))
                        details.add(HeadingValueModel("Name",it.driver_name!!))
                        setSheetTitle(it.driver_name)
                        details.add(HeadingValueModel("Address",it.driver_address!!))
                        details.add(HeadingValueModel("Location",it.location_name!!))
                        details.add(HeadingValueModel("Mobile Number",it.driver_mobile_number1!!))

                        it.driver_mobile_number2?.let { number->
                            details.add(HeadingValueModel("Alternative Mobile No.",number))
                        }
                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Registration Date",
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