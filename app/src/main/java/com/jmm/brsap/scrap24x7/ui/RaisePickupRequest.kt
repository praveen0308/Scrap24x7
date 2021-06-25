package com.jmm.brsap.scrap24x7.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.jmm.brsap.scrap24x7.databinding.ActivityRaisePickupRequestBinding
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.viewmodel.PickupRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaisePickupRequest : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {
    private lateinit var binding: ActivityRaisePickupRequestBinding

    private val viewModel by viewModels<PickupRequestViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaisePickupRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupStepView()
        subscribeObservers()
        binding.toolbarRaisePickupRequest.setApplicationToolbarListener(this)

    }

    private fun subscribeObservers() {
        viewModel.activeStep.observe(this, {
            binding.stepView.go(it,true)
        })
    }

    private fun setupStepView() {
        binding.stepView.state
                .steps(object : ArrayList<String?>() {
                    init {
                        add("Select items")
                        add("Pick date")
                        add("Confirm")
                    }
                })
                .stepsNumber(3)
                .animationDuration(resources.getInteger(android.R.integer.config_shortAnimTime)) // other state methods are equal to the corresponding xml attributes
                .commit()
    }



    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }


}