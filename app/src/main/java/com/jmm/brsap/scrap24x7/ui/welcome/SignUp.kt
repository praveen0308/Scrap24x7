package com.jmm.brsap.scrap24x7.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmm.brsap.scrap24x7.databinding.FragmentSignUpBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.ProgressBarHandler
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUp : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    //UI

    private val TAG: String = "SignupTAG"

    //ViewModels
    private val viewModel by viewModels<SignUpViewModel>()

    // Adapters

    // Variable


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val first = getColoredSpanned("By tapping Sign Up, you acknowledge that you have agreed to the", ContextCompat.getColor(requireContext(),R.color.colorTextSecondary))
//        val second= getColoredSpanned("Terms and Conditions", ContextCompat.getColor(requireContext(),R.color.colorPrimary))
//        val third = getColoredSpanned("and read the", ContextCompat.getColor(requireContext(),R.color.colorTextSecondary))
//        val fourth= getColoredSpanned("Privacy Policy", ContextCompat.getColor(requireContext(),R.color.colorPrimary))
//
//
//        binding.tvSignupTnc.text = Html.fromHtml("$first $second $third $fourth")
        binding.btnSignup.setOnClickListener {
            val customer = Customer(
                    first_name = binding.etFirstName.text.toString().trim(),
                    last_name = binding.etLastName.text.toString().trim(),
                    email_id = binding.etEmail.text.toString().trim(),
                    mobile_number = binding.etMobileNo.text.toString().trim()
            )
            viewModel.registerNewCustomer(customer)
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun subscribeObservers() {
        viewModel.registrationResponse.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        Log.d(TAG, it.toString())
                        showToast("Registered Successfully !!!")
                        findNavController().navigate(SignUpDirections.actionSignUpToLoginSignupScreen())
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