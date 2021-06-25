package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ChooseScrapItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewScrapItemBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentChooseScrapItemBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AddScrapCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseScrapItem : BaseBottomSheetDialogFragment<FragmentChooseScrapItemBinding>(FragmentChooseScrapItemBinding::inflate),
    ChooseScrapItemAdapter.ScrapItemInterface {

    private val viewModel by activityViewModels<AddScrapCategoryViewModel>()

    private var selectedCategoryID = 5
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY

    private lateinit var chooseScrapItemAdapter: ChooseScrapItemAdapter

    private var categories = mutableListOf<Category>()
    private var categoriesItems = mutableListOf<CategoryItem>()
    private var selectedItems = mutableListOf<CategoryItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        viewModel.getCategories()
        binding.btnSubmit.setOnClickListener {
            val selectedItems = categoriesItems.filter { it.isSelected }
            viewModel.setSelectedScrapItemList(selectedItems)
            dismiss()
        }


    }
    override fun subscribeObservers() {
        viewModel.selectedScrapItems.observe(this,{
            selectedItems.clear()
            selectedItems.addAll(it)
        })
        viewModel.selectedScrapCategory.observe(this,{
            selectedCategoryID = it
            populateScrapList()
        })

        viewModel.categories.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        categories.clear()
                        categories.addAll(it)
                        populateCategoryDropdown(categories)
                        viewModel.getScrapItems()
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
        viewModel.scrapItems.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        categoriesItems.clear()
                        categoriesItems.addAll(it)
                        for (item in categoriesItems){
                            for (selectedItem in selectedItems){
                                if (item.id == selectedItem.id){
                                    item.isSelected = true
                                }
                            }
                        }
                        populateScrapList()
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

    private fun populateScrapList(){
        val filteredList = categoriesItems.filter { it.category_id==selectedCategoryID }
        chooseScrapItemAdapter.setCategoryItemList(filteredList)
    }

    private fun populateCategoryDropdown(mList: List<Category>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvCategory.threshold = 100 //start searching for values after typing first character
        binding.actvCategory.setAdapter(arrayAdapter)

        binding.actvCategory.setOnItemClickListener { parent, view, position, id ->
            viewModel.setActiveScrapCategory((parent.getItemAtPosition(position) as Category).id!!)

        }
    }

    private fun setupRecyclerview(){
        chooseScrapItemAdapter =  ChooseScrapItemAdapter(this)
        binding.rvScrapItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = chooseScrapItemAdapter
        }
    }

    override fun onItemClick(item: CategoryItem) {

    }

}