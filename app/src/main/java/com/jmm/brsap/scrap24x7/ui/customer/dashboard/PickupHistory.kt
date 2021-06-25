package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.PickupRequestHistoryAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentPickupHistoryBinding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.pickuprequest.PickupDetailActivity
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.MenuEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.UserEnum
import com.jmm.brsap.scrap24x7.viewmodel.customer.PickupHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickupHistory :
    BaseFragment<FragmentPickupHistoryBinding>(FragmentPickupHistoryBinding::inflate), PickupRequestHistoryAdapter.PickupRequestHistoryInterface {

    private val viewModel by viewModels<PickupHistoryViewModel>()
    private lateinit var pickupRequestHistoryAdapter: PickupRequestHistoryAdapter
    private var userId = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRVScrapItems()

    }

    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            userId = it
            viewModel.getPickupHistoryByCustomerId(userId)
        })
        viewModel.pickupHistoryByCID.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        if (it.isEmpty()){
                            binding.myEmptyView.visibility = View.VISIBLE
                        }
                        else{
                            pickupRequestHistoryAdapter.setItemList(it)
                            binding.myEmptyView.visibility = View.GONE
                        }

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
        pickupRequestHistoryAdapter = PickupRequestHistoryAdapter(AdminEnum.CUSTOMER,this)

        binding.rvPickupHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = pickupRequestHistoryAdapter

        }

    }

    override fun onItemClick(pickupRequest: PickupRequest) {
        val intent = Intent(requireActivity(), PickupDetailActivity::class.java)
        intent.putExtra("SOURCE", MenuEnum.PICKUP_DETAIL)
        intent.putExtra("USER_TYPE", UserEnum.CUSTOMER)
        intent.putExtra("ID", pickupRequest.id)
        startActivity(intent)
    }


}