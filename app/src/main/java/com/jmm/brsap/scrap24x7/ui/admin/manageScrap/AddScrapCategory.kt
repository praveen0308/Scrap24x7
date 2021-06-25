package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SelectedScrapItemsAdapter
import com.jmm.brsap.scrap24x7.databinding.ActivityAddScrapCategoryBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.ui.BaseActivity1
import com.jmm.brsap.scrap24x7.ui.admin.ADD_VEHICLE
import com.jmm.brsap.scrap24x7.ui.admin.UPDATE_VEHICLE
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.AddScrapCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScrapCategory :
    BaseActivity1<ActivityAddScrapCategoryBinding>(ActivityAddScrapCategoryBinding::inflate),
    ApplicationToolbar.ApplicationToolbarListener,
    SelectedScrapItemsAdapter.SelectedScrapItemsInterface {

    private val viewModel by viewModels<AddScrapCategoryViewModel>()
    private lateinit var selectedScrapItemsAdapter: SelectedScrapItemsAdapter
    private val selectedScrapItems = mutableListOf<CategoryItem>()
    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpRvScrapItems()
        binding.toolbarAddScrapCategory.setApplicationToolbarListener(this)

        binding.btnChooseScrapItems.setOnClickListener {
            val bottomSheet = ChooseScrapItem()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        binding.btnSubmit.setOnClickListener {
            val categoryName = binding.etCategoryName.text.toString()
            val description = binding.etDescription.text.toString()
            val category = Category(
                type = categoryName,
                description = description,
                categoryItemList = selectedScrapItems
            )
            viewModel.addNewCategory(userId, category)
            binding.btnSubmit.isEnabled = false
        }
    }

    private fun setUpRvScrapItems() {
        selectedScrapItemsAdapter = SelectedScrapItemsAdapter(this)
        binding.rvSelectedScrapItems.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = selectedScrapItemsAdapter
        }
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

    override fun subscribeObservers() {
        viewModel.userId.observe(this, {
            userId = it
        })
        viewModel.selectedScrapItems.observe(this, {
            selectedScrapItems.clear()
            selectedScrapItems.addAll(it)

            selectedScrapItemsAdapter.setCategoryItemList(selectedScrapItems)

        })

        viewModel.addedCategory.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val intent = Intent()
                        intent.putExtra("Message", "Added successfully !!!")
                        setResult(RESULT_OK, intent)
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
}