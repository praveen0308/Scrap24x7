package com.jmm.brsap.scrap24x7.ui.executive

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityHomeExucutiveBinding
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.MainActivity
import com.jmm.brsap.scrap24x7.ui.UserNotifications
import com.jmm.brsap.scrap24x7.ui.customer.HomeCustomer
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.executive.HomeExecutiveViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExecutive : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding:ActivityHomeExucutiveBinding

    private lateinit var navController:NavController

    private val viewModel by viewModels<HomeExecutiveViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeExucutiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_executive)
        binding.toolbarHomeExecutive.setApplicationToolbarListener(this)
        binding.executiveNavigationDrawer.setNavigationItemSelectedListener(this)
        binding.executiveNavigationDrawer.bringToFront()
        binding.executiveNavigationDrawer.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayoutExecutive)
    }
    override fun onBackPressed() {
        if (binding.drawerLayoutExecutive.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutExecutive.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onToolbarNavClick() {
        binding.drawerLayoutExecutive.openDrawer(GravityCompat.START)
    }

    override fun onMenuClick() {
        val intent = Intent(this@HomeExecutive, UserNotifications::class.java)
        intent.putExtra("USER_ID","dummy")
        startActivity(intent)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.executiveDashboard->{
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            viewModel.clearUserInfo()
                            val intent = Intent(this@HomeExecutive, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                            dialog.dismiss()

                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()
                        }
                    }
                }
            showAlertDialog("Do you really want to log out?", dialogClickListener)
            }

        }

        binding.drawerLayoutExecutive.closeDrawer(GravityCompat.START)
        return true
    }
}