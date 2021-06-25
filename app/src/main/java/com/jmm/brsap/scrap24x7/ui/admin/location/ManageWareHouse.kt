package com.jmm.brsap.scrap24x7.ui.admin.location

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SingleTitleRowAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageWareHouseBinding
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.Warehouse
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageWarehouseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageWareHouse : BaseFragment<FragmentManageWareHouseBinding>(FragmentManageWareHouseBinding::inflate),
    SingleTitleRowAdapter.SingleTitleRowInterface {

    private val viewModel by viewModels<ManageWarehouseViewModel>()
    private lateinit var singleTitleRowAdapter: SingleTitleRowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvWarehouses()
        viewModel.getWarehouses()
        binding.floatingActionButton.setOnClickListener {
            val bottomSheet = AddNewWarehouse()
            val bundle = Bundle()
            bundle.putSerializable("ACTION",AdminEnum.ADD)
            bottomSheet.arguments = bundle
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    override fun subscribeObservers() {
        viewModel.warehouses.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        singleTitleRowAdapter.setItemList(it)
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

    private fun setRvWarehouses() {
        singleTitleRowAdapter = SingleTitleRowAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = singleTitleRowAdapter
        }
    }

    override fun onItemClick(item: Any) {
        val warehouse = item as Warehouse
        val bottomSheet = LocationDetailSheet()
        val bundle = Bundle()
        bundle.putInt("ID",warehouse.id!!)
        bundle.putSerializable("TYPE", AdminEnum.WAREHOUSE)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

    override fun onEditClick(item: Any) {
        val warehouse = item as Warehouse
        val bottomSheet = AddNewWarehouse()
        val bundle = Bundle()
        bundle.putSerializable("ACTION",AdminEnum.EDIT)
        bundle.putInt("WarehouseID",warehouse.id!!)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    override fun onDeleteClick(item: Any) {
        val warehouse = item as Warehouse
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        warehouse.id?.let { viewModel.deleteWarehouse(it)
                            dialog.dismiss()
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
        showAlertDialog("Do you really want to delete this warehouse?",dialogClickListener)
    }
}