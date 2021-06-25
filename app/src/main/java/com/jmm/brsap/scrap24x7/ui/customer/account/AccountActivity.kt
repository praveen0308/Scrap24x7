package com.jmm.brsap.scrap24x7.ui.customer.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAccountBinding
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.MenuEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : AppCompatActivity(), ApplicationToolbar.ApplicationToolbarListener {
    private lateinit var binding: ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarHelpActivity.setApplicationToolbarListener(this)
        val source: MenuEnum = intent.getSerializableExtra("SOURCE") as MenuEnum

        when (source) {
            MenuEnum.PROFILE -> {
                setToolbarTitle("Profile")
                showFragment(CustomerProfile())
            }

            MenuEnum.NOTIFICATION -> {
                setToolbarTitle("Notification Settings")
                showFragment(NotificationSettings())
            }

            MenuEnum.LANGUAGE -> {
                setToolbarTitle("Choose language")
                showFragment(ChooseLanguage())
            }
            MenuEnum.ADDRESS_SETTINGS -> {
                setToolbarTitle("Manage Address")
                showFragment(ManageAddress())
            }
            MenuEnum.PAYMENT_METHOD -> {
                setToolbarTitle("Payment Methods")
                showFragment(CustomerPaymentMethod())
            }

            MenuEnum.FAQ -> {
                setToolbarTitle("FAQ")
                showFragment(FAQ())
            }

            MenuEnum.HOW_IT_WORKS -> {
                setToolbarTitle("How it works?")
                showFragment(HowItWorks())
            }
            MenuEnum.HELP_CENTRE -> {
                setToolbarTitle("Help Centre")
                showFragment(HelpCentre())
            }
            MenuEnum.ABOUT -> {
                setToolbarTitle("About Us")
                showFragment(AboutUs())
            }
            MenuEnum.SHARE -> {
                setToolbarTitle("Share Us")
                showFragment(ShareUs())
            }
            else -> {
                //nothing
            }

        }

    }

    private fun setToolbarTitle(title: String) {

        binding.toolbarHelpActivity.setToolbarTitle(title)
    }

    private fun showFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_activity_help, fragment)
        fragmentTransaction.commit()
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

}