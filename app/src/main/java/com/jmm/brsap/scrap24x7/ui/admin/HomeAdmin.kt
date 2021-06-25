package com.jmm.brsap.scrap24x7.ui.admin

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AdminUniversalUtilAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityHomeAdminBinding
import com.jmm.brsap.scrap24x7.model.ModelAdminUniversalUtil
import com.jmm.brsap.scrap24x7.model.network_models.Data
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.UserNotifications
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.ManageUserActivity
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.admin.HomeAdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAdmin : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener,
    AdminUniversalUtilAdapter.AdminUniversalUtilInterface {

    private val viewModel by viewModels<HomeAdminViewModel>()
    private lateinit var binding: ActivityHomeAdminBinding

    private lateinit var adminUniversalUtilAdapter: AdminUniversalUtilAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialUiState()
        subscribeObservers()
        generateFCMToken()
        binding.toolbarAdminDashboard.setApplicationToolbarListener(this)
        val first = getColoredSpanned("Scrap", ContextCompat.getColor(this, R.color.colorPrimary))
        val second =
            getColoredSpanned("24x7", ContextCompat.getColor(this, R.color.colorTextSecondary))
        binding.toolbarAdminDashboard.setToolbarTitle(Html.fromHtml("$first $second"))

        setUpRVStatistics()

        viewModel.getPickupRequestSummary(getTodayDate())
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getPickupRequestSummary(getTodayDate())
            binding.refreshLayout.isRefreshing = false
        }
        binding.pickupRequestSummary.root.setOnClickListener {
            val intent = Intent(this@HomeAdmin, AdminNavigationActivity::class.java)
            intent.putExtra("SOURCE", AdminEnum.PICKUP_REQUEST)
            startActivity(intent)
        }
    }
    private fun generateFCMToken(){

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCMToken", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.d("FCMToken", msg)
            showToast(msg)
        })
    }

    private fun setUpRVStatistics() {
        adminUniversalUtilAdapter = AdminUniversalUtilAdapter(this)
        binding.rvStatistics.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = adminUniversalUtilAdapter
        }
    }

    private fun initialUiState(){
        binding.pickupRequestSummary.root.isVisible = false
    }

    private fun subscribeObservers() {
        viewModel.pickupRequestSummary.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        setPickupSummary(it.data)
                        viewModel.getSystemStatistics()
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

        viewModel.systemStatistics.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        adminUniversalUtilAdapter.setItemList(getStatistics(it.data))
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

    private fun setPickupSummary(data: Data) {
        binding.apply {
            pickupRequestSummary.apply {
                this.root.isVisible =true
                layoutRequestCount.tvData.setText(data.raised_requests_count.toString())

                progressRequested.tvTitle.text = "Requested"
                progressRequested.tvCount.text = data.requested_pickups.toString()
                progressRequested.pbData.progress = data.requested_pickups
                progressRequested.pbData.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this@HomeAdmin, R.color.Orange))
                progressRequested.pbData.max = data.raised_requests_count

                progressAccepted.tvTitle.text = "Accepted"
                progressAccepted.tvCount.text = data.accepted_pickups.toString()
                progressAccepted.pbData.progress = data.accepted_pickups
                progressAccepted.pbData.max = data.raised_requests_count

                progressRejected.tvTitle.text = "Rejected"
                progressRejected.tvCount.text = data.rejected_pickups.toString()
                progressRejected.pbData.progress = data.rejected_pickups
                progressRejected.pbData.progressTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this@HomeAdmin, R.color.Red))
                progressRejected.pbData.max = data.raised_requests_count
            }
        }

    }

    private fun getStatistics(data: Data): List<ModelAdminUniversalUtil> {
        val mList = mutableListOf<ModelAdminUniversalUtil>()

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.VEHICLES,
                AdminEnum.STAT,
                "Vehicles",
                data.vehicle_count.toString(),
                R.drawable.ic_round_local_shipping_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.CUSTOMER,
                AdminEnum.STAT,
                "Customers",
                data.customer_count.toString(),
                R.drawable.ic_baseline_person_24
            )
        )

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.WAREHOUSE,
                AdminEnum.STAT,
                "Warehouse",
                data.warehouse_count.toString(),
                R.drawable.ic_baseline_room_preferences_24
            )
        )

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.LOCATION,
                AdminEnum.STAT,
                "Locations",
                data.location_count.toString(),
                R.drawable.ic_baseline_map_24
            )
        )
        return mList

    }

    override fun onToolbarNavClick() {

        startActivity(Intent(this, NavigationActivity::class.java))
    }

    override fun onMenuClick() {
        val intent = Intent(this@HomeAdmin, UserNotifications::class.java)
        intent.putExtra("USER_ID","dummy")
        startActivity(intent)
    }

    override fun onItemClick(item: ModelAdminUniversalUtil) {
        when (item.id) {
            AdminEnum.CUSTOMER -> {
                val intent = Intent(this@HomeAdmin, ManageUserActivity::class.java)
                intent.putExtra("SOURCE",item.id)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(this@HomeAdmin, AdminNavigationActivity::class.java)
                intent.putExtra("SOURCE", item.id)
                startActivity(intent)
            }
        }

    }

    override fun onAddClick(item: ModelAdminUniversalUtil) {

    }

}