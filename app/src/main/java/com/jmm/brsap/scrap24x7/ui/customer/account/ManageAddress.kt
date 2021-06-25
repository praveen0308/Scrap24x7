package com.jmm.brsap.scrap24x7.ui.customer.account

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.CustomerAddressListAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageAddressBinding
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.customer.dashboard.AddNewAddress
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.ManageAddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAddress : BaseFragment<FragmentManageAddressBinding>(FragmentManageAddressBinding::inflate),
    CustomerAddressListAdapter.CustomerAddressListInterface {

    private val viewModel by viewModels<ManageAddressViewModel>()
    private lateinit var customerAddressListAdapter: CustomerAddressListAdapter
    private var userId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRVCustomerAddress()


        binding.btnAddNewAddress.setOnClickListener {
            startActivity(Intent(requireActivity(), AddNewAddress::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCustomerAddressList(userId)
    }

    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            userId = it
            viewModel.getCustomerAddressList(userId)
        })
        viewModel.customerAddressList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        customerAddressListAdapter.setAddressList(it)
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


        viewModel.deletedAddress.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Address deleted successfully !!!")
                        viewModel.getCustomerAddressList(userId)
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

    private fun setUpRVCustomerAddress() {
        customerAddressListAdapter = CustomerAddressListAdapter(OtherEnum.EDITABLE)
        binding.rvCustomerAddress.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = customerAddressListAdapter
        }
        customerAddressListAdapter.setListener(this)
    }

    override fun onAddressClick(customerAddress: CustomerAddress) {

    }

    override fun onAddressDelete(customerAddress: CustomerAddress) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        customerAddress.id?.let { viewModel.deleteCustomerAddress(it)
                            dialog.dismiss()
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
        showAlertDialog("Do you really want to delete address?",dialogClickListener)

    }
}