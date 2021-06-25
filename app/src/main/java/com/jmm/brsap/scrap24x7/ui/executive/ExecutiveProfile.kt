package com.jmm.brsap.scrap24x7.ui.executive

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ProfileItemsAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentCustomerProfileBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentExecutiveProfileBinding
import com.jmm.brsap.scrap24x7.model.ModelProfileItem
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.AddNewExecutive
import com.jmm.brsap.scrap24x7.ui.customer.account.EditCustomerProfile
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.CustomerProfileViewModel
import com.jmm.brsap.scrap24x7.viewmodel.executive.ExecutiveProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ExecutiveProfile : BaseFragment<FragmentExecutiveProfileBinding>(FragmentExecutiveProfileBinding::inflate) {
    //UI

    //ViewModels
    private val viewModel by viewModels<ExecutiveProfileViewModel>()


    // Adapters
    private lateinit var profileItemsAdapter: ProfileItemsAdapter

    // Variable
    private var userId = 0
    private lateinit var executiveMaster: ExecutiveMaster
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
//                val data: Intent? = result.data
                showToast("Profile updated successfully !!!")
                viewModel.getExecutiveInfo(userId)
            }
            else{
                showToast("Cancelled !!!")
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
        val intent = Intent(context, AddNewExecutive::class.java)
        intent.putExtra("ExecutiveID",executiveMaster.id)
        intent.putExtra("ACTION",AdminEnum.EDIT)
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

    private fun prepareProfileCategoryList(executiveMaster: ExecutiveMaster): ArrayList<ModelProfileItem> {
        val itemList: ArrayList<ModelProfileItem> = ArrayList()

        //List1
        itemList.add(ModelProfileItem("Name", "${executiveMaster.name}",R.drawable.ic_baseline_person_24))
        executiveMaster.aadhaar_number?.let {
            itemList.add(ModelProfileItem("Aadhaar No.", it, R.drawable.ic_baseline_announcement_24))
        }

        itemList.add(ModelProfileItem("Mobile Number", executiveMaster.mobile_no!!, R.drawable.ic_round_phone_24))
        executiveMaster.alternative_mobile_no?.let {
            itemList.add(ModelProfileItem("Alternate Mobile No.", it, R.drawable.ic_round_phone_24))
        }

        itemList.add(ModelProfileItem("Location", executiveMaster.location_name!!,R.drawable.ic_round_location_on_24))
        itemList.add(ModelProfileItem("Address", executiveMaster.address!!,R.drawable.ic_baseline_email_24))

        return itemList
    }

    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner,{
            userId = it
            viewModel.getExecutiveInfo(it)
        })
        viewModel.executiveInfo.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        executiveMaster = it
                        prepareProfileData(executiveMaster)
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

    private fun prepareProfileData(executiveMaster: ExecutiveMaster) {
        binding.apply {
            tvProfileTitle.text = "${executiveMaster.name}"
            cdProfileStatistics.visibility = View.VISIBLE

            tvEarning.text = "0"
            tvRequests.text = "0"
        }

        profileItemsAdapter.setItemList(prepareProfileCategoryList(executiveMaster))

    }
}

