package com.jmm.brsap.scrap24x7.ui.admin

import android.os.Bundle
import com.jmm.brsap.scrap24x7.databinding.ActivityAdminNavigationBinding
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.admin.location.ManageLocations
import com.jmm.brsap.scrap24x7.ui.admin.location.ManageWareHouse
import com.jmm.brsap.scrap24x7.ui.admin.manageScrap.ManageScrap
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.ManageUsers
import com.jmm.brsap.scrap24x7.ui.admin.pickupRequest.ManagePickupRequest
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminNavigationActivity : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityAdminNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdminNavigation.setApplicationToolbarListener(this)
        val source: AdminEnum = intent.getSerializableExtra("SOURCE") as AdminEnum

        when (source) {
            AdminEnum.PICKUP_REQUEST -> {
                setToolbarTitle("Pickup Requests")
                showFragment(ManagePickupRequest())
            }
            AdminEnum.SCRAP_REPORT -> {
                setToolbarTitle("Scrap Report")
                showFragment(ScrapReport())
            }

            AdminEnum.MANAGE_SCRAP -> {
                setToolbarTitle("Manage Scrap")
                showFragment(ManageScrap())
            }

            AdminEnum.LOCATION -> {
                setToolbarTitle("Locations")
                showFragment(ManageLocations())
            }
            AdminEnum.WAREHOUSE -> {
                setToolbarTitle("Warehouses")
                showFragment(ManageWareHouse())
            }
            AdminEnum.VEHICLES -> {
                setToolbarTitle("Vehicles")
                showFragment(ManageVehicles())
            }

            AdminEnum.MANAGE_USERS -> {
                setToolbarTitle("Manage Users")
                showFragment(ManageUsers())
            }
            AdminEnum.SETTINGS -> {
                setToolbarTitle("Settings")
                showFragment(AdminSettings())
            }

            else -> {
                //nothing
            }

        }

    }

    private fun setToolbarTitle(title: String) {

        binding.toolbarAdminNavigation.setToolbarTitle(title)
    }
//
//    private fun showFragment(fragment: Fragment) {
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fl_container_admin_navigation, fragment)
//        fragmentTransaction.commit()
//    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }
}