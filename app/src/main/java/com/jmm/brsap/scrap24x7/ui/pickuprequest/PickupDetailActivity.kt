package com.jmm.brsap.scrap24x7.ui.pickuprequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAccountBinding
import com.jmm.brsap.scrap24x7.databinding.ActivityPickupDetailBinding
import com.jmm.brsap.scrap24x7.ui.customer.account.*
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.MenuEnum
import com.jmm.brsap.scrap24x7.util.UserEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickupDetailActivity : AppCompatActivity(), ApplicationToolbar.ApplicationToolbarListener {
    private lateinit var binding: ActivityPickupDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarPickupDetail.setApplicationToolbarListener(this)
        val source: MenuEnum = intent.getSerializableExtra("SOURCE") as MenuEnum
        val userType= intent.getSerializableExtra("USER_TYPE") as UserEnum
        val pickupId = intent.getIntExtra("ID",0)
        when (source) {
            MenuEnum.PICKUP_DETAIL -> {
                setToolbarTitle("Pickup Detail")
                showFragment(MasterPickupSummary.newInstance(pickupId,userType))
            }


            else -> {
                //nothing
            }

        }

    }

    private fun setToolbarTitle(title: String) {

        binding.toolbarPickupDetail.setToolbarTitle(title)
    }

    private fun showFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_pickup_detail, fragment)
        fragmentTransaction.commit()
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

}