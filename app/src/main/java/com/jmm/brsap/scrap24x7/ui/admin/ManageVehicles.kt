package com.jmm.brsap.scrap24x7.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MyLoadStateAdapter
import com.jmm.brsap.scrap24x7.adapters.VehicleMasterListAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageVehiclesBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.pagingDataAdapter.VehicleMasterPagingAdapter
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.ui.ComponentDetail
import com.jmm.brsap.scrap24x7.ui.admin.manageUser.UserDetailSheet
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageVehicleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val ADD_VEHICLE = 101
const val UPDATE_VEHICLE = 102

@AndroidEntryPoint
class ManageVehicles :
    BaseFragment<FragmentManageVehiclesBinding>(FragmentManageVehiclesBinding::inflate),
    VehicleMasterListAdapter.VehicleMasterListInterface {

    private val viewModel by viewModels<ManageVehicleViewModel>()
    private lateinit var vehicleMasterPagingAdapter: VehicleMasterPagingAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvVehicleMasters()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode== ADD_VEHICLE || result.resultCode== UPDATE_VEHICLE){
                    showToast(data!!.getStringExtra("Message")!!)
                }
                else{
                    showToast("Cancelled !!")
                }

            }
        binding.floatingActionButton.setOnClickListener {
//            val intent= Intent(requireActivity(),AddNewVehicle::class.java)
//            intent.putExtra("ACTION",AdminEnum.ADD)
//            startActivity(intent)
            openActivityForAddVehicle()
        }
        lifecycleScope.launch {
            viewModel.vehicles.collectLatest { pagedData ->
                vehicleMasterPagingAdapter.submitData(pagedData as PagingData<VehicleMaster>)
            }
        }

        binding.refreshLayout.setOnRefreshListener {
//            viewModel.getVehicles()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun openActivityForAddVehicle() {
        val intent = Intent(requireActivity(), AddNewVehicle::class.java)
        intent.putExtra("ACTION", AdminEnum.ADD)
        resultLauncher.launch(intent)
    }

    private fun openActivityForUpdateVehicle(vehicleId: Int) {
        val intent = Intent(requireActivity(), AddNewVehicle::class.java)
        intent.putExtra("ACTION", AdminEnum.EDIT)
        intent.putExtra("VehicleID", vehicleId)
        resultLauncher.launch(intent)
    }


    override fun subscribeObservers() {
//        viewModel.vehicles.observe(viewLifecycleOwner, { _result ->
//            when (_result.status) {
//                Status.SUCCESS -> {
//                    _result._data?.let {
//                        vehicleMasterListAdapter.setVehicleMasterList(it)
//                        if (it.isEmpty()) {
//                            binding.myEmptyView.visibility = View.VISIBLE
//                        } else {
//                            binding.myEmptyView.visibility = View.GONE
//                        }
//                    }
//                    displayLoading(false)
//                }
//                Status.LOADING -> {
//                    displayLoading(true)
//                }
//                Status.ERROR -> {
//                    displayLoading(false)
//                    _result.message?.let {
//                        displayError(it)
//                    }
//                }
//            }
//        })

    }

    private fun setRvVehicleMasters() {

        vehicleMasterPagingAdapter = VehicleMasterPagingAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = vehicleMasterPagingAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { vehicleMasterPagingAdapter.retry() },
                footer = MyLoadStateAdapter { vehicleMasterPagingAdapter.retry() }
            )
        }
    }

    override fun onItemClick(item: VehicleMaster) {

        val bottomSheet = VehicleDetail()
        val bundle = Bundle()
        bundle.putInt("VEHICLE_ID",item.id!!)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

    override fun onUpdateClick(item: VehicleMaster) {
        openActivityForUpdateVehicle(item.id!!)
    }

}