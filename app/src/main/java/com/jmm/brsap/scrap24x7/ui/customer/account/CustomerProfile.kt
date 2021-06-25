package com.jmm.brsap.scrap24x7.ui.customer.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ProfileItemsAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentCustomerProfileBinding
import com.jmm.brsap.scrap24x7.model.ModelProfileItem
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.CustomerProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class CustomerProfile : BaseFragment<FragmentCustomerProfileBinding>(FragmentCustomerProfileBinding::inflate) {
    //UI

    //ViewModels
    private val viewModel by viewModels<CustomerProfileViewModel>()


    // Adapters
    private lateinit var profileItemsAdapter: ProfileItemsAdapter

    // Variable
    private var userId = ""
    private lateinit var customer: Customer
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
//                val data: Intent? = result.data
                showToast("Profile updated successfully !!!")
                viewModel.getCustomerInfo(userId)
            }
            else{
                showToast("Failed !!!")
            }

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRvProfileData()

        binding.btnEditProfile.setOnClickListener {

            openSomeActivityForResult()
        }

    }
    private fun openSomeActivityForResult() {
        val intent = Intent(context, EditCustomerProfile::class.java)
        intent.putExtra("customerInfo",customer)
        resultLauncher.launch(intent)
    }
    private fun setUpRvProfileData() {
        profileItemsAdapter = ProfileItemsAdapter()
        binding.rvProfileData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = profileItemsAdapter
        }
    }

    private fun prepareProfileCategoryList(customer: Customer): ArrayList<ModelProfileItem> {
        val itemList: ArrayList<ModelProfileItem> = ArrayList()

        //List1
        itemList.add(ModelProfileItem("Name", "${customer.first_name} ${customer.last_name}",R.drawable.ic_baseline_person_24))
        itemList.add(ModelProfileItem("Mobile Number", customer.mobile_number, R.drawable.ic_round_phone_24))
        itemList.add(ModelProfileItem("Email address", customer.email_id,R.drawable.ic_baseline_email_24))

        return itemList
    }

    override fun subscribeObservers() {
        viewModel.loginUserName.observe(viewLifecycleOwner,{
            userId = it
            viewModel.getCustomerInfo(it)
        })
        viewModel.customerInfo.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        customer = it
                        prepareProfileData(it)
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

    private fun prepareProfileData(customer: Customer) {
        binding.apply {
            tvProfileTitle.text = "${customer.first_name} ${customer.last_name}"
            cdProfileStatistics.visibility = View.VISIBLE

            tvEarning.text = "0"
            tvRequests.text = customer.request_count.toString()
        }

        profileItemsAdapter.setItemList(prepareProfileCategoryList(customer))

    }
}

