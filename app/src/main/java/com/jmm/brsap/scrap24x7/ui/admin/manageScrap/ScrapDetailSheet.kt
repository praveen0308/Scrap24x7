package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentScrapDetailSheetBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentUserDetailSheetBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.OtherEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.util.convertISOTimeToDateTime
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageScrapActivityViewModel
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScrapDetailSheet : BaseBottomSheetDialogFragment<FragmentScrapDetailSheetBinding>(
    FragmentScrapDetailSheetBinding::inflate),
    MasterDetailAdapter.MasterDetailInterface {

    private lateinit var masterDetailAdapter: MasterDetailAdapter
    private val viewModel by viewModels<ManageScrapActivityViewModel>()
    private var componentId = 0
    private lateinit var type : AdminEnum
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvDetail()
        componentId = requireArguments().getInt("ID")
        type= requireArguments().getSerializable("TYPE") as AdminEnum

        when(type){

            AdminEnum.CATEGORY->{
                viewModel.getCategoryById(componentId)
            }
            AdminEnum.CATEGORY_ITEM->{
                viewModel.getCategoryItemById(componentId) }
            AdminEnum.UNIT->{
                viewModel.getUnitById(componentId) }
            else->{
                //nothing
            }

        }
    }
    override fun subscribeObservers() {
        viewModel.category.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(
                            HeadingValueModel("Category ID",it.id.toString(),
                                OtherEnum.HORIZONTAL)
                        )
                        details.add(HeadingValueModel("Category Name",it.type.toString()))
                        setSheetTitle(it.type.toString())
                        details.add(HeadingValueModel("Description",it.description.toString()))

                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Created On",
                                    formattedDate
                                )
                            }?.let { it2 -> details.add(it2) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)
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
                        val details = mutableListOf<HeadingValueModel>()
                        details.add(
                            HeadingValueModel("Item ID",it.id.toString(),
                                OtherEnum.HORIZONTAL)
                        )
                        details.add(HeadingValueModel("Category",it.type!!))
                        details.add(HeadingValueModel("Name",it.item_name!!))
                        setSheetTitle(it.item_name)
                        details.add(HeadingValueModel("Description",it.description!!))
                        details.add(HeadingValueModel("Unit",it.unit_name!!))
                        details.add(HeadingValueModel("Current rate","₹ ${it.unit_price.toString()}",OtherEnum.HORIZONTAL))

                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Created On",
                                    formattedDate
                                )
                            }?.let { model -> details.add(model) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)
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

        viewModel.unit.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {

                        val details = mutableListOf<HeadingValueModel>()

                        details.add(
                            HeadingValueModel("Unit ID",it.id.toString(),
                                OtherEnum.HORIZONTAL)
                        )
                        details.add(HeadingValueModel("Name",it.unit_name!!))
                        setSheetTitle(it.unit_name)
                        details.add(HeadingValueModel("Description",it.description!!))

                        it.created_at?.let { date ->
                            convertISOTimeToDateTime(date)?.let { formattedDate ->
                                HeadingValueModel("Created On",
                                    formattedDate
                                )
                            }?.let { model -> details.add(model) }
                        }
                        masterDetailAdapter.setHeadingValueModelList(details)
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

    private fun setSheetTitle(title:String){
        binding.layoutDetail.tvPageTitle.text = title
    }
    private fun setupRvDetail(){
        masterDetailAdapter = MasterDetailAdapter(this)
        binding.layoutDetail.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(requireActivity())
            val dividerItemDecoration = DividerItemDecoration(requireActivity(),
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = masterDetailAdapter
        }
    }


}