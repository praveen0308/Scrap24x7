package com.jmm.brsap.scrap24x7.ui.customer.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.jmm.brsap.scrap24x7.databinding.ActivityEditCustomerProfileBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.CustomerProfileViewModel

class EditCustomerProfile : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityEditCustomerProfileBinding
    private val viewModel by viewModels<CustomerProfileViewModel>()
    private var customerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCustomerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        binding.toolbarEditCustomerProfile.setApplicationToolbarListener(this)
        populateCustomerInfo(intent.getSerializableExtra("customerInfo") as Customer)

        binding.btnSaveChanges.setOnClickListener {
            val customer = Customer(
                    customer_id = customerId,
                    first_name = binding.etFirstName.text.toString().trim(),
                    last_name = binding.etLastName.text.toString().trim(),
                    email_id = binding.etEmail.text.toString().trim(),
                    mobile_number = binding.etMobileNo.text.toString().trim()
            )
            viewModel.updateCustomerInfo(customer)
        }
    }

    override fun onToolbarNavClick() {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun populateCustomerInfo(customer: Customer){
        customerId = customer.customer_id!!
        binding.apply {
            etFirstName.setText(customer.first_name)
            etLastName.setText(customer.last_name)
            etEmail.setText(customer.email_id)
            etMobileNo.setText(customer.mobile_number)
        }
    }
    override fun onMenuClick() {

    }

    private fun subscribeObservers(){
        viewModel.updateResponse.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        viewModel.updateUserName(it.first_name)
                        viewModel.updateFirstName(it.first_name)
                        viewModel.updateLastName(it.last_name)
                        val intent = Intent()
                        intent.putExtra("userId", "1234")
                        setResult(Activity.RESULT_OK, intent)
                        finish()
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
}