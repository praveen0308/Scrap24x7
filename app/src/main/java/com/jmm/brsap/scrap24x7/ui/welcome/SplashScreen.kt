package com.jmm.brsap.scrap24x7.ui.welcome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentSplashScreenBinding
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository.Companion.LOGIN_DONE
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository.Companion.NEW_USER
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository.Companion.ON_BOARDING_DONE
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    private val viewModel by viewModels<SplashScreenViewModel>()
    private var userRoleID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            performNavigation()
        }, 2000)

    }

    private fun performNavigation() {

        viewModel.userRoleID.observe(viewLifecycleOwner, {
            userRoleID = it
        })
        viewModel.welcomeStatus.observe(viewLifecycleOwner, {
            when (it) {
                NEW_USER -> findNavController().navigate(SplashScreenDirections.actionSplashScreenToOnBoardingScreen())
                ON_BOARDING_DONE -> findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginSignupScreen())
                LOGIN_DONE -> {
                    when (userRoleID) {
                        1 -> {
                            findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeAdmin())
                            requireActivity().finish()
                        }
                        2 -> {
                            findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeCustomer())
                            requireActivity().finish()
                        }
                        3 -> {
                            findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeExecutive())
                            requireActivity().finish()
                        }
                        else -> findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginSignupScreen())
                    }


                }

            }

        })
    }

    override fun subscribeObservers() {

    }
}