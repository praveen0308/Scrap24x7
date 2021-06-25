package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.os.Bundle
import com.jmm.brsap.scrap24x7.databinding.ActivityManageUserBinding
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManageUserActivity : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener{

    private lateinit var binding: ActivityManageUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ToolbarListener
        binding.toolbarManageUser.setApplicationToolbarListener(this)

        val source = intent.getSerializableExtra("SOURCE") as AdminEnum

        setOperationAcToSource(source)

    }



    private fun setOperationAcToSource(source:AdminEnum) {
        when (source) {
            AdminEnum.CUSTOMER -> {
                setToolbarTitle("Customers")
                showFragment(ManageCustomer())
            }
            AdminEnum.EXECUTIVE -> {
                setToolbarTitle("Executive")
                showFragment(ManageExecutive())
            }
            AdminEnum.DRIVER -> {
                setToolbarTitle("Drivers")
                showFragment(ManageDriver())
            }
            else -> {
                //nothing
            }
        }


    }


    private fun setToolbarTitle(title: String) {

        binding.toolbarManageUser.setToolbarTitle(title)
    }


    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }




}
