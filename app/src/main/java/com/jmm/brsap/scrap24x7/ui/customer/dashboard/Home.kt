package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.ScrapImpactAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentHomeBinding
import com.jmm.brsap.scrap24x7.model.ModelScrapImpact
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.UserNotifications
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.getColoredSpanned
import com.jmm.brsap.scrap24x7.util.getGreetings
import com.jmm.brsap.scrap24x7.viewmodel.customer.HomeCustomerViewModel
import com.jmm.brsap.scrap_24_x_7.adapters.ScrapCategoryAdapter
import com.jmm.brsap.scrap_24_x_7.adapters.ScrapCategoryItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), ScrapCategoryItemAdapter.ScrapCategoryItemAdapterListener,
        ApplicationToolbar.ApplicationToolbarListener {

    //UI

    //ViewModels
    private val viewModel by viewModels<HomeCustomerViewModel>()

    // Adapters
    private lateinit var scrapCategoryAdapter: ScrapCategoryAdapter
    private lateinit var scrapImpactAdapter: ScrapImpactAdapter

    // Variable
    private lateinit var categoryList: List<Category>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val first = getColoredSpanned(
                "Scrap", ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
        )
        )
        val second = getColoredSpanned(
                "24x7", ContextCompat.getColor(
                requireContext(),
                R.color.colorTextSecondary
        )
        )

        binding.toolbarHome.setToolbarTitle(Html.fromHtml("$first $second"))

        setUpRVScrapCategory()
        setUpRVScrapImpact()

        viewModel.getCategories()
        binding.toolbarHome.setApplicationToolbarListener(this)
    }

    override fun subscribeObservers() {
        viewModel.userName.observe(viewLifecycleOwner, {
            populateUserProfileInfo(it)
        })



        viewModel.categoryList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        categoryList = it
                        viewModel.getCategoriesItem()
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

        viewModel.categoryItemList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        prepareScrapCategoryList(it)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun setUpRVScrapCategory() {
        scrapCategoryAdapter = ScrapCategoryAdapter()

        binding.layoutRateList.apply {
            templateSectionedRvTitle.text = "Rate List"
            templateSectionedRvRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = scrapCategoryAdapter
            }
        }


    }

    private fun populateUserProfileInfo(userName: String) {


        binding.layoutProfileInfo.apply {
            "${getGreetings()}, $userName".also { tvProfileMessage.text = it }
        }
    }

    private fun prepareScrapCategoryList(mList: List<CategoryItem>) {
        for (category in categoryList) {
            val categoryItemList = mutableListOf<CategoryItem>()
            for (categoryItem in mList) {
                if (categoryItem.category_id == category.id) {
                    categoryItemList.add(categoryItem)
                }
            }
            category.categoryItemList = categoryItemList
        }

        scrapCategoryAdapter.setCategoryList(categoryList)
    }

    private fun setUpRVScrapImpact() {
        scrapImpactAdapter = ScrapImpactAdapter(prepareScrapImpactCategoryItemList())
        binding.layoutScrapImpact.apply {
            templateSectionedRvTitle.text = getString(R.string.scrap_impact)
            templateSectionedRvRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, 2)
                adapter = scrapImpactAdapter
            }
        }
    }

    private fun prepareScrapImpactCategoryItemList(): MutableList<ModelScrapImpact> {

        val categoryItemList = ArrayList<ModelScrapImpact>()

        categoryItemList.add(
                ModelScrapImpact(
                        "0",
                        "Tree saved",
                        R.drawable.ic_save_tree,
                        R.color.colorPrimary
                )
        )
        categoryItemList.add(
                ModelScrapImpact(
                        "0 kW hr",
                        "Energy saved",
                        R.drawable.ic_energy,
                        R.color.Orange
                )
        )
        categoryItemList.add(
                ModelScrapImpact(
                        "0 L",
                        "Oil saved",
                        R.drawable.ic_oil,
                        R.color.CreamyRed
                )
        )
        categoryItemList.add(
                ModelScrapImpact(
                        "0 Gallon",
                        "Water saved",
                        R.drawable.ic_water,
                        R.color.Blue
                )
        )


        return categoryItemList

    }

    override fun onSelected(item: CategoryItem) {

    }

    override fun onDeselected(item: CategoryItem) {

    }

    override fun onToolbarNavClick() {

    }

    override fun onMenuClick() {
        val intent = Intent(requireActivity(),UserNotifications::class.java)
        intent.putExtra("USER_ID","dummy")
        startActivity(intent)
    }
}