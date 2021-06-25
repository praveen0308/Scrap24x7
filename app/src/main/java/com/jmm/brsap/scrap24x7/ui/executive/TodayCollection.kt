package com.jmm.brsap.scrap24x7.ui.executive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.ManagePickupRequestAdapter
import com.jmm.brsap.scrap24x7.adapters.TodayCollectionAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityTodayCollectionBinding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.customer.TodayCollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayCollection : BaseActivity(), ApplicationToolbar.ApplicationToolbarListener,
    TodayCollectionAdapter.TodayCollectionInterface {

    private lateinit var binding:ActivityTodayCollectionBinding
    private val viewModel by viewModels<TodayCollectionViewModel>()

    private lateinit var todayCollectionAdapter: TodayCollectionAdapter
    private var executiveId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  ActivityTodayCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRvPickupRequests()
        subscribeObservers()
        binding.toolbarTodayCollection.setApplicationToolbarListener(this)
    }

    private fun subscribeObservers(){
        viewModel.userId.observe(this,{
            executiveId = it
            viewModel.getPickupRequests(getDateRange(FilterEnum.TODAY), getTodayDate(),executiveId)
        })
        viewModel.pickupRequests.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        todayCollectionAdapter.setPickupRequestList(it)
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

    private fun setRvPickupRequests() {

        todayCollectionAdapter = TodayCollectionAdapter(this)
        binding.rvTodayPickupList.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager

            adapter = todayCollectionAdapter
        }
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

    override fun onItemClick(item: PickupRequest) {
        val intent = Intent(this,PickupAction::class.java)
        intent.putExtra("ID",item.id)
        startActivity(intent)
    }
}