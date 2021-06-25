package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewScrapItemBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.UnitModel
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageScrapActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewScrapItem : BaseBottomSheetDialogFragment<FragmentAddNewScrapItemBinding>(FragmentAddNewScrapItemBinding::inflate) {

    private val viewModel by viewModels<ManageScrapActivityViewModel>()

    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY

    private var selectedUnitID = 0
    private var selectedCategoryId = 0
    private var categoryItemID = 0
    private var units= mutableListOf<UnitModel>()
    private var categories = mutableListOf<Category>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        source = requireArguments().getSerializable("ACTION") as AdminEnum
        viewModel.getUnits()
        viewModel.getCategories()
        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            categoryItemID = requireArguments().getInt("CategoryItemID",0)
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val categoryItem = CategoryItem(
                    unit_id = selectedUnitID,
                    category_id = selectedCategoryId,
                    item_name = etName.text.toString(),
                    unit_price = etItemRate.text.toString().toDouble(),
                    description = etDescription.text.toString()
                )
                if (source== AdminEnum.ADD){
                    viewModel.addNewCategoryItem(categoryItem)
                }
                else{
                    viewModel.updateCategoryItem(categoryItemID,categoryItem)
                }

            }
        }
    }

    override fun subscribeObservers() {

        viewModel.addedCategoryItem.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Added Successfully!!!")
                        dismiss()
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

        viewModel.updatedCategoryItem.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated Successfully!!!")
                        dismiss()
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
        viewModel.categoryItem.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateCategoryItemInfo(it)
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

        viewModel.categories.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        categories.clear()
                        categories.addAll(it)
                        populateCategoryDropdown(categories)
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


        viewModel.units.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        units.clear()
                        units.addAll(it)
                        populateUnitsDropdown(units)
                        if (source== AdminEnum.EDIT) viewModel.getCategoryItemById(categoryItemID)

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

    private fun populateCategoryItemInfo(categoryItem: CategoryItem) {
        categoryItemID = categoryItem.id!!
        binding.apply {
            selectedUnitID = categoryItem.unit_id!!
            actvUnit.setText((units.find { it.id ==selectedUnitID })?.unit_name)

            selectedCategoryId = categoryItem.category_id!!
            actvCategory.setText((categories.find { it.id ==selectedCategoryId })?.type)

            etName.setText(categoryItem.item_name)
            etItemRate.setText(categoryItem.unit_price.toString())
            etDescription.setText(categoryItem.description)

        }
    }


    private fun populateCategoryDropdown(mList: List<Category>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvCategory.threshold = 100 //start searching for values after typing first character
        binding.actvCategory.setAdapter(arrayAdapter)

        binding.actvCategory.setOnItemClickListener { parent, view, position, id ->
            selectedCategoryId = (parent.getItemAtPosition(position) as Category).id!!
        }
    }



    private fun populateUnitsDropdown(mList: List<UnitModel>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        //actv is the AutoCompleteTextView from your layout file
        binding.actvUnit.threshold = 100 //start searching for values after typing first character
        binding.actvUnit.setAdapter(arrayAdapter)

        binding.actvUnit.setOnItemClickListener { parent, view, position, id ->
            selectedUnitID = (parent.getItemAtPosition(position) as UnitModel).id!!

        }
    }


}