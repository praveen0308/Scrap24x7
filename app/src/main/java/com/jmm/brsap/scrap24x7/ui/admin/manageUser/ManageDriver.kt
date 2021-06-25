package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MyLoadStateAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageDriverBinding
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.pagingDataAdapter.DriverPagingAdapter
import com.jmm.brsap.scrap24x7.pagingDataAdapter.ManageUserInterface
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageDriver :
    BaseFragment<FragmentManageDriverBinding>(FragmentManageDriverBinding::inflate),
    ManageUserInterface {

    private val viewModel by viewModels<ManageUserActivityViewModel>()

    private lateinit var driverPagingAdapter: DriverPagingAdapter

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var callingPermission: ActivityResultLauncher<String>

    private lateinit var currentlyActiveNumber:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callingPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                // Do something if permission granted
                if (isGranted) {
                    callANumber(currentlyActiveNumber)
                } else {
                    showToast("Please allow permission to use calling feature!!")
                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvData()

        //Init ResultLauncher
        initResultLauncher()

        lifecycleScope.launch {
            viewModel.driverMasters.collectLatest { pagedData ->
                driverPagingAdapter.submitData(pagedData as PagingData<DriverMaster>)
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.driverMasters.collectLatest { pagedData ->
                    driverPagingAdapter.submitData(pagedData as PagingData<DriverMaster>)
                }
            }
            binding.refreshLayout.isRefreshing = false
        }
        binding.fabAdd.setOnClickListener {
            openActivityForAddDriver()
        }
    }

    override fun subscribeObservers() {

    }

    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode == RESULT_OK) {
                    showToast(data!!.getStringExtra("Message")!!)
                } else {
                    showToast("Cancelled !!")
                }

            }
    }


    private fun setupRvData() {
        driverPagingAdapter = DriverPagingAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(
                context,
                layoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = driverPagingAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { driverPagingAdapter::retry },
                footer = MyLoadStateAdapter { driverPagingAdapter::retry }
            )
        }
    }

    private fun openActivityForAddDriver() {
        val intent = Intent(requireActivity(), AddNewDriver::class.java)
        intent.putExtra("ACTION", AdminEnum.ADD)
        resultLauncher.launch(intent)
    }

    private fun openActivityForUpdateDriver(driverId: Int) {
        val intent = Intent(requireActivity(), AddNewDriver::class.java)
        intent.putExtra("ACTION", AdminEnum.EDIT)
        intent.putExtra("DriverID", driverId)
        resultLauncher.launch(intent)
    }

    companion object {
        const val ADD_DRIVER = 101
        const val UPDATE_DRIVER = 102
    }

    private fun callANumber(mNumber: String) {
        val phoneCallIntent = Intent(Intent.ACTION_CALL)
        phoneCallIntent.data = Uri.parse("tel:$mNumber") //Uri.parse("tel:your number")
        startActivity(phoneCallIntent)
    }

    override fun onCallClick(item: Any) {
        val driverMaster = item as DriverMaster
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            currentlyActiveNumber = driverMaster.driver_mobile_number1!!
            callingPermission.launch(Manifest.permission.CALL_PHONE)
        }else{
            callANumber(driverMaster.driver_mobile_number1!!)
        }


    }

    override fun onBlockClick(item: Any) {

    }

    override fun onItemClick(item: Any) {
        val driver = item as DriverMaster
        val bottomSheet = UserDetailSheet()
        val bundle = Bundle()
        bundle.putInt("USER_ID",driver.id!!)
        bundle.putSerializable("USER_TYPE",AdminEnum.DRIVER)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

}