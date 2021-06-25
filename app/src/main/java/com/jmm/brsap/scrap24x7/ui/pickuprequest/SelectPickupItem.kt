package com.jmm.brsap.scrap24x7.ui.pickuprequest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentSelectPickupItemBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.PickupRequestViewModel
import com.jmm.brsap.scrap_24_x_7.adapters.ScrapCategoryAdapter
import com.jmm.brsap.scrap_24_x_7.adapters.ScrapCategoryItemAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectPickupItem :
    BaseFragment<FragmentSelectPickupItemBinding>(FragmentSelectPickupItemBinding::inflate),
    ScrapCategoryItemAdapter.ScrapCategoryItemAdapterListener {

    //ViewModels
    private val viewModel by activityViewModels<PickupRequestViewModel>()

    // Adapters
    private lateinit var scrapCategoryAdapter: ScrapCategoryAdapter

    // Variable
    private lateinit var categoryList: List<Category>
    private var selectedScrapItems: MutableList<CategoryItem> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRVScrapCategory()

        viewModel.getSelectedScrapItems()
        viewModel.getSelectedCategories()

        viewModel.setActiveStep(0)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_selectPickupItem_to_selectPickupDate)
        }
    }


    override fun subscribeObservers() {
        viewModel.selectedCategories.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        categoryList = it.filter { category -> category.isSelected }

//                        for (category in categoryList){
//                            category.categoryItemList= (category.categoryItemList!!.associateBy(CategoryItem::id) + selectedScrapItems.associateBy(CategoryItem::id)).values.toMutableList()
//                        }

                        for (category in categoryList){

                            for(categoryItem in category.categoryItemList!!){
                                if (selectedScrapItems.contains(categoryItem)){
                                    categoryItem.isSelected=true
                                }
                            }
                        }

                        scrapCategoryAdapter.setCategoryList(categoryList)

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
                else -> {
                    //nothing
                }
            }
        })

        viewModel.selectedItems.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        selectedScrapItems = it.toMutableList()
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
                else->{
                    //nothisng
                }
            }
        })


    }

    private fun setUpRVScrapCategory() {
        scrapCategoryAdapter = ScrapCategoryAdapter(this)
        binding.layoutScrapItems.apply {
            templateSectionedRvTitle.text = getString(R.string.select_item_for_pickup)
            templateSectionedRvRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = scrapCategoryAdapter
            }
        }

    }


    override fun onSelected(item: CategoryItem) {

        viewModel.addSelectedScrapItem(item)
    }

    override fun onDeselected(item: CategoryItem) {
        viewModel.removeSelectedScrapItem(item)
    }
}