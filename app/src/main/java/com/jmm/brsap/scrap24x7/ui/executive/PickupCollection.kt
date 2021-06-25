package com.jmm.brsap.scrap24x7.ui.executive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.PickupCollectionAdapter
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityPickupCollectionBinding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequestItem
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.executive.PickupCollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickupCollection : BaseActivity(), PickupCollectionAdapter.PickupCollectionInterface,
    ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding: ActivityPickupCollectionBinding
    private val viewModel by viewModels<PickupCollectionViewModel>()

    private lateinit var pickupCollectionAdapter:PickupCollectionAdapter

    private val pickupItems = mutableListOf<PickupRequestItem>()

    private var pickupId=0
    private var totalAmount = 0.0
    private lateinit var pickupRequestId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRVScrapItems()
        subscribeObservers()

        pickupId = intent.getIntExtra("ID",0)
        viewModel.getPickupRequestDetail(pickupId)
        binding.toolbarPickupCollection.setApplicationToolbarListener(this)

        binding.btnCalculateTotal.setOnClickListener {
            totalAmount = 0.0
            for (item in pickupItems){
                totalAmount += item.price!! * item.quantity!!
                item.total_price = item.price * item.quantity!!
            }

            binding.groupScrapTotal.isVisible = true
            binding.tvTotalScrapAmount.text = totalAmount.toString()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.collectPickupItems(pickupRequestId,pickupItems)
        }
    }

    private fun subscribeObservers() {
        viewModel.pickupRequest.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        pickupRequestId = it.pickup_id!!
                        pickupItems.clear()
                        pickupItems.addAll(it.pickup_request_items!!)
                        val categoryItems = pickupItems.map { item->
                            item.category_item!!
                        }
                        pickupCollectionAdapter.setCategoryItemList(categoryItems)

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
        viewModel.collectedPickupRequest.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        showToast("Collected Successfully !!!")
                        finish()
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

    private fun setUpRVScrapItems() {
        pickupCollectionAdapter = PickupCollectionAdapter(this)
        binding.rvPickupItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = pickupCollectionAdapter
        }
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

    override fun onQuantityChange(itemId: Int, quantity: Double) {
        val pickupItem = pickupItems.find {
            it.item_id == itemId
        }

        pickupItem?.quantity = quantity
    }
}