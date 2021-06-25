package com.jmm.brsap.scrap24x7.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentLoginBinding
import com.jmm.brsap.scrap24x7.model.network_models.UserTypeMaster
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository.Companion.LOGIN_DONE
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.admin.HomeAdmin
import com.jmm.brsap.scrap24x7.ui.customer.HomeCustomer
import com.jmm.brsap.scrap24x7.ui.executive.HomeExecutive
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Login : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    //UI

    //ViewModels
    private val viewModel by viewModels<LoginViewModel>()

    // Adapters


    // Variable
    private var userTypeId : Int = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateUserTypeList(getUserTypeList())
        binding.btnLogin.setOnClickListener {
            when(userTypeId){
                1,3->{
                    val username = binding.etUsername.text.toString()
                    val password = binding.etPassword.text.toString()
                    viewModel.checkStaffLogin(username,password)
                }
                2->{
                    viewModel.getCustomerInfo(binding.etUsername.text.toString())
                }
                else->{
                    //nothing
                }
            }

        }
    }

    private fun populateUserTypeList(mList: List<UserTypeMaster>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvUsertype.threshold = 1 //start searching for values after typing first character
        binding.actvUsertype.setAdapter(arrayAdapter)

        binding.actvUsertype.setOnItemClickListener { parent, view, position, id ->
            userTypeId = (parent.getItemAtPosition(position) as UserTypeMaster).id!!
            if (userTypeId==2){
                binding.tilPassword.visibility = View.GONE
            }else{
                binding.tilPassword.visibility = View.VISIBLE
            }
        }
    }

    private fun getUserTypeList():List<UserTypeMaster>{
        val userTypeList = mutableListOf<UserTypeMaster>()
        userTypeList.add(
            UserTypeMaster(
            id = 1,
                user_type = "Admin"
        ))

        userTypeList.add(
            UserTypeMaster(
                id = 2,
                user_type = "Customer"
            ))

        userTypeList.add(
            UserTypeMaster(
                id = 3,
                user_type = "Executive"
            ))

        return userTypeList
    }

    override fun subscribeObservers() {
        viewModel.customerInfo.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
//                        findNavController().navigate(R.id.action_loginSignupScreen_to_OTPVerification)
                        try {
                            viewModel.updateWelcomeStatus(LOGIN_DONE)
                            viewModel.updateUserId(it.customer_id!!)
                            viewModel.updateLoginUserName(it.user_id!!)
                            viewModel.updateUserName(it.first_name)
                            viewModel.updateFirstName(it.first_name)
                            viewModel.updateLastName(it.last_name)
                            viewModel.updateUserRoleID(it.user_type_id!!)
                        }finally {
                            val intent = Intent(requireActivity(), HomeCustomer::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            requireActivity().finish()
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

        viewModel.userMaster.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        try {
                            viewModel.updateWelcomeStatus(LOGIN_DONE)
                            viewModel.updateUserId(it.user_id!!)
                            viewModel.updateLoginUserName(it.user_name!!)
                            viewModel.updateUserName(it.user_display_name!!)
//                            viewModel.updateFirstName(it.user_name!!)
//                            viewModel.updateLastName(it.last_name)
                            viewModel.updateUserRoleID(it.user_type_id!!)
                        }finally {
                            when(userTypeId){
                                1->{
                                    val intent = Intent(requireActivity(), HomeAdmin::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                                3->{
                                    val intent = Intent(requireActivity(), HomeExecutive::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            }

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
    }

}