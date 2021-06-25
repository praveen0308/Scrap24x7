package com.jmm.brsap.scrap24x7.ui.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityHomeCustomerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeCustomer : AppCompatActivity() {

    private lateinit var binding: ActivityHomeCustomerBinding
    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationUI.setupWithNavController(binding.bottomNavMainDashboard, findNavController(R.id.nav_host_main_dashboard))
    }
}