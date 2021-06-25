package com.jmm.brsap.scrap24x7.ui.admin.location

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.SingleTitleRowAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageLocationsBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.UserDetailSheet
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageLocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageLocations : BaseFragment<FragmentManageLocationsBinding>(FragmentManageLocationsBinding::inflate),
    SingleTitleRowAdapter.SingleTitleRowInterface {

    private val viewModel by viewModels<ManageLocationViewModel>()
    private lateinit var singleTitleRowAdapter: SingleTitleRowAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getLocations()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvLocations()
        viewModel.getLocations()
        binding.floatingActionButton.setOnClickListener {
            val bottomSheet = AddNewLocation()

            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    override fun subscribeObservers() {
        viewModel.locations.observe(viewLifecycleOwner, { _result ->
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

    private fun setRvLocations() {
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
        val location = item as LocationMaster
        val bottomSheet = LocationDetailSheet()
        val bundle = Bundle()
        bundle.putInt("ID",location.id!!)
        bundle.putSerializable("TYPE", AdminEnum.LOCATION)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

    override fun onEditClick(item: Any) {
        val location = item as LocationMaster
        val bottomSheet = AddNewLocation()
        val bundle = Bundle()
        bundle.putSerializable("LOCATION",location)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    override fun onDeleteClick(item: Any) {
        val location = item as LocationMaster
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        location.id?.let { viewModel.deleteLocation(it)
                            dialog.dismiss()
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
        showAlertDialog("Do you really want to delete this location?",dialogClickListener)
    }
}