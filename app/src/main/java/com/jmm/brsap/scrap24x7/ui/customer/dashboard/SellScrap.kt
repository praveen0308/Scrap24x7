package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.CustomerAddressListAdapter
import com.jmm.brsap.scrap24x7.adapters.SellCategoryItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentSellScrapBinding
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.RaisePickupRequest
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.SellScrapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SellScrap : BaseFragment<FragmentSellScrapBinding>(FragmentSellScrapBinding::inflate),
    SellCategoryItemAdapter.SellCategoryItemAdapterListener,
    CustomerAddressListAdapter.CustomerAddressListInterface {


    //UI

    //ViewModels
    private val viewModel by viewModels<SellScrapViewModel>()

    // Adapters
    private lateinit var sellCategoryItemAdapter: SellCategoryItemAdapter
    private lateinit var customerAddressListAdapter: CustomerAddressListAdapter

    // Variable
    private var categoryList: MutableList<Category> = ArrayList()
    private var userId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRVSellCategory()
        setUpRVCustomerAddress()

        viewModel.getCategoriesItem()

        binding.layoutSelectNAddAddress.btnAddNewAddress.setOnClickListener {
            startActivity(Intent(requireActivity(), AddNewAddress::class.java))
        }

        binding.layoutSellScrapItem.btnRaisePickupRequest.setOnClickListener {
            val intent = Intent(requireActivity(), RaisePickupRequest::class.java)
            startActivity(intent)
        }

        binding.apply {
            layoutNotAvailable.setOnClickListener {
                motionLayout.transitionToStart()
            }
            layoutSellScrapItem.root.setOnClickListener {
                motionLayout.transitionToStart()
            }
        }
    }

    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            userId = it
            viewModel.getCustomerAddressList(userId)
        })

        viewModel.categoryItemList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {

                    _result._data?.let {

                        val fList = it.groupBy { categoryItem ->
                            Category(id = categoryItem.category_id, type = categoryItem.type)
                        }
                        fList.forEach { entry ->
                            entry.key.categoryItemList = entry.value.toMutableList()
                            categoryList.add(entry.key)
                        }

                    }

                    sellCategoryItemAdapter.setCategoryList(categoryList)
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


        viewModel.selectedCategories.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        if (it.isNotEmpty()) binding.layoutSellScrapItem.btnRaisePickupRequest.isEnabled =
                            it.any { category ->
                                category.isSelected
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

        viewModel.customerAddressList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        if (it.isEmpty()) {
                            binding.layoutSelectNAddAddress.tvNoAddressAdded.visibility = VISIBLE
                            binding.layoutSelectNAddAddress.layoutOr.root.visibility = GONE
                            binding.lblPickupLocation.visibility = GONE
                            binding.tvPickupLocationAddress.text = "No Address Added"
                        }
                        else {
                            it[0].isSelected = true
                            viewModel.setSelectedPickupAddress(it[0])
                            binding.layoutSelectNAddAddress.layoutOr.root.visibility = VISIBLE
                            binding.lblPickupLocation.visibility = VISIBLE
                            checkAvailability(it[0].location_id)
                            setToolbarData(it[0])
                            customerAddressListAdapter.setAddressList(it)
                            binding.layoutSelectNAddAddress.tvNoAddressAdded.visibility = GONE

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

    private fun setUpRVCustomerAddress() {
        customerAddressListAdapter = CustomerAddressListAdapter(OtherEnum.NON_EDITABLE)
        binding.layoutSelectNAddAddress.rvCustomerAddress.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = customerAddressListAdapter
        }
        customerAddressListAdapter.setListener(this)
    }

    private fun checkAvailability(id: Int?) {
        if (id == null) {
            binding.motionLayout.getConstraintSet(R.id.start)
                .getConstraint(binding.layoutNotAvailable.getId()).propertySet.mVisibilityMode =
                1 // 1 - ignore or 0 - normal
            binding.layoutNotAvailable.setVisibility(VISIBLE);
            binding.motionLayout.getConstraintSet(R.id.start)
                .getConstraint(binding.layoutSellScrapItem.root.id).propertySet.mVisibilityMode =
                1 // 1 - ignore or 0 - normal
            binding.layoutSellScrapItem.root.setVisibility(GONE);
        } else {
            binding.motionLayout.getConstraintSet(R.id.start)
                .getConstraint(binding.layoutNotAvailable.getId()).propertySet.mVisibilityMode =
                1; // 1 - ignore or 0 - normal
            binding.layoutNotAvailable.setVisibility(GONE);
            binding.motionLayout.getConstraintSet(R.id.start)
                .getConstraint(binding.layoutSellScrapItem.root.id).propertySet.mVisibilityMode =
                1; // 1 - ignore or 0 - normal
            binding.layoutSellScrapItem.root.setVisibility(VISIBLE);
        }
    }

//    private fun prepareScrapCategoryList(mList: List<CategoryItem>) {
//        val icons = arrayListOf<Int>()
//        icons.add(R.drawable.ic_unselect_papers)
//        icons.add(R.drawable.ic_unselect_plastic)
//        icons.add(R.drawable.ic_unselect_metals)
//        icons.add(R.drawable.ic_unselect_e_waste)
//        icons.add(R.drawable.ic_unselect_others)
//
//        var indexForIcon = 0
//        for (category in categoryList) {
//
//            category.drawableUrl = icons[indexForIcon]
//            indexForIcon++
//            val categoryItemList = mutableListOf<CategoryItem>()
//
//
//            for (categoryItem in mList) {
//                if (categoryItem.category_id == category.id) {
//                    categoryItemList.add(categoryItem)
//                }
//            }
//            category.categoryItemList = categoryItemList
//        }
//
//        sellCategoryItemAdapter.setCategoryList(categoryList)
//    }

    private fun setUpRVSellCategory() {
        sellCategoryItemAdapter = SellCategoryItemAdapter(this)
        binding.layoutSellScrapItem.rvSellItem.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = sellCategoryItemAdapter
        }
    }

    private fun setToolbarData(customerAddress: CustomerAddress) {
        binding.tvPickupLocationType.text = customerAddress.address_type
        binding.tvPickupLocationAddress.text = customerAddress.address1.toString()
    }

    override fun onCategorySelected(categoryList: MutableList<Category>) {
        viewModel.setSelectedCategories(categoryList)
        viewModel.getSelectedCategories()
    }

    override fun onAddressClick(customerAddress: CustomerAddress) {
        checkAvailability(customerAddress.location_id)
        setToolbarData(customerAddress)
        binding.motionLayout.transitionToStart();
        viewModel.setSelectedPickupAddress(customerAddress)
    }

    override fun onAddressDelete(customerAddress: CustomerAddress) {

    }

}