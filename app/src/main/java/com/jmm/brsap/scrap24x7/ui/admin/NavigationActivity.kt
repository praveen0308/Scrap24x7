package com.jmm.brsap.scrap24x7.ui.admin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AdminUniversalUtilAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityNavigationBinding
import com.jmm.brsap.scrap24x7.model.ModelAdminUniversalUtil
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.MainActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.viewmodel.admin.HomeAdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : BaseActivity(), AdminUniversalUtilAdapter.AdminUniversalUtilInterface {
    private lateinit var adminUniversalUtilAdapter: AdminUniversalUtilAdapter
    private lateinit var binding: ActivityNavigationBinding

    private val viewModel by viewModels<HomeAdminViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRVNavigation()
        adminUniversalUtilAdapter.setItemList(getAdminNavigationItem())
        binding.ivCloseDrawer.setOnClickListener {
            finish()
        }
    }

    private fun setUpRVNavigation() {
        adminUniversalUtilAdapter = AdminUniversalUtilAdapter(this)
        binding.rvNavigationItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adminUniversalUtilAdapter
        }
    }

    override fun onItemClick(item: ModelAdminUniversalUtil) {
        if (item.id == AdminEnum.LOG_OUT) {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            viewModel.clearUserInfo()
                            val intent = Intent(this@NavigationActivity, MainActivity::class.java)
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

        } else {
            val intent = Intent(this@NavigationActivity, AdminNavigationActivity::class.java)
            intent.putExtra("SOURCE", item.id)
            startActivity(intent)
            finish()
        }
    }

    override fun onAddClick(item: ModelAdminUniversalUtil) {

    }


    private fun getAdminNavigationItem(): List<ModelAdminUniversalUtil> {
        val mList = mutableListOf<ModelAdminUniversalUtil>()

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.PICKUP_REQUEST,
                AdminEnum.NAV,
                "Pickup Requests",
                "",
                R.drawable.ic_baseline_announcement_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.SCRAP_REPORT,
                AdminEnum.NAV,
                "Scrap Report",
                "",
                R.drawable.ic_round_format_list_bulleted_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.MANAGE_SCRAP,
                AdminEnum.NAV,
                "Manage Scrap",
                "",
                R.drawable.ic_logo
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.LOCATION,
                AdminEnum.NAV,
                "Locations",
                "",
                R.drawable.ic_baseline_map_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.WAREHOUSE,
                AdminEnum.NAV,
                "Manage Warehouses",
                "",
                R.drawable.ic_baseline_room_preferences_24
            )
        )

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.VEHICLES,
                AdminEnum.NAV,
                "Vehicles",
                "",
                R.drawable.ic_round_local_shipping_24
            )
        )

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.MANAGE_USERS,
                AdminEnum.NAV,
                "Manage Users",
                "",
                R.drawable.ic_baseline_person_24
            )
        )
//        mList.add(
//            ModelAdminUniversalUtil(
//                AdminEnum.SETTINGS,
//                AdminEnum.NAV,
//                "Settings",
//                "",
//                R.drawable.ic_round_settings_24
//            )
//        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.LOG_OUT,
                AdminEnum.NAV,
                "Log Out",
                "",
                R.drawable.ic_round_logout_24
            )
        )

        return mList

    }

}