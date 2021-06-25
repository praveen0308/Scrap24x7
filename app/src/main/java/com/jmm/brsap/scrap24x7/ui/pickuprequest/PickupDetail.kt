package com.jmm.brsap.scrap24x7.ui.pickuprequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentPickupDetailBinding
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.customer.account.FAQ
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.PickupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class PickupDetail : BaseFragment<FragmentPickupDetailBinding>(FragmentPickupDetailBinding::inflate) {

    private val viewModel by viewModels<PickupDetailViewModel>()

    // Adapters
    private lateinit var selectedScarpItemAdapter: SelectedScarpItemAdapter

    // Variable
    private var pickupId: Int? = 0
    private lateinit var selectedPickupRequestItems:List<CategoryItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pickupId = it.getInt(ARG_PARAM1)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialViewState()
        setUpRVScrapItems()
        viewModel.getPickupRequestDetail(pickupId!!)


    }
    override fun subscribeObservers() {

        viewModel.pickupRequest.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        selectedPickupRequestItems = it.pickup_request_items!!.map {item->
                            item.category_item!!
                             }
                        selectedScarpItemAdapter.setItemList(selectedPickupRequestItems)
                        setPickupAddress(it.customer_address!!)
                        setPickupDateLayout(it.pickup_requested_date as String)

                        binding.tvPickupId.text = it.pickup_id.toString()
                        binding.tvPickupStatus.text = it.status_name
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
        selectedScarpItemAdapter = SelectedScarpItemAdapter()

        binding.layoutScrapItems.apply {
            tvHeading.text = "Scrap items"
            rvData.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = selectedScarpItemAdapter
            }
        }

    }

    private fun setPickupDateLayout(date:String){
        binding.layoutPickupDate.apply {
            tvHeading.text = "Pickup"
            tvTitle.text =date
            tvSubtitle.text = "10 AM - 6 PM"

        }
    }

    private fun setPickupAddress(customerAddress: CustomerAddress){
        binding.layoutSelectedAddress.apply {
            tvHeading.text = "Pickup Address"
            tvTitle.text = customerAddress.address1.toString()
        }
    }

    private fun initialViewState(){
        binding.apply {
            layoutScrapItems.btnAction.visibility = View.GONE
            layoutPickupDate.btnAction.visibility = View.GONE
            layoutSelectedAddress.btnAction.visibility = View.GONE
            layoutPaymentAcceptanceMode.btnAction.visibility = View.GONE

        }
    }
    companion object {

        @JvmStatic
        fun newInstance(pickupId: Int) =
            PickupDetail().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, pickupId)
                }
            }
    }
}