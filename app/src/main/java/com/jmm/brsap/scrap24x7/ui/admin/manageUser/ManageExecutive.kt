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
import com.jmm.brsap.scrap24x7.databinding.FragmentManageExecutiveBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.pagingDataAdapter.ExecutivePagingAdapter
import com.jmm.brsap.scrap24x7.pagingDataAdapter.ManageUserInterface
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManageExecutive :
    BaseFragment<FragmentManageExecutiveBinding>(FragmentManageExecutiveBinding::inflate),
    ManageUserInterface {

    private val viewModel by viewModels<ManageUserActivityViewModel>()

    private lateinit var executivePagingAdapter: ExecutivePagingAdapter

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
            viewModel.executiveMasters.collectLatest { pagedData ->
                executivePagingAdapter.submitData(pagedData as PagingData<ExecutiveMaster>)
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.executiveMasters.collectLatest { pagedData ->
                    executivePagingAdapter.submitData(pagedData as PagingData<ExecutiveMaster>)
                }
            }
            binding.refreshLayout.isRefreshing = false
        }
        binding.fabAdd.setOnClickListener {
            openActivityForAddExecutive()
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
//if (result.resultCode == ADD_EXECUTIVE || result.resultCode == UPDATE_EXECUTIVE) {
//                    showToast(data!!.getStringExtra("Message")!!)
//                } else {
//                    showToast("Cancelled !!")
//                }

            }
    }


    private fun setupRvData() {
        executivePagingAdapter = ExecutivePagingAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(
                context,
                layoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = executivePagingAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { executivePagingAdapter::retry },
                footer = MyLoadStateAdapter { executivePagingAdapter::retry }
            )
        }
    }

    private fun openActivityForAddExecutive() {
        val intent = Intent(requireActivity(), AddNewExecutive::class.java)
        intent.putExtra("ACTION", AdminEnum.ADD)
        resultLauncher.launch(intent)
    }

    private fun openActivityForUpdateExecutive(executiveId: Int) {
        val intent = Intent(requireActivity(), AddNewExecutive::class.java)
        intent.putExtra("ACTION", AdminEnum.EDIT)
        intent.putExtra("ExecutiveID", executiveId)
        resultLauncher.launch(intent)
    }

    companion object {
        const val ADD_EXECUTIVE = 101
        const val UPDATE_EXECUTIVE = 102
    }

    private fun callANumber(mNumber: String) {
        val phoneCallIntent = Intent(Intent.ACTION_CALL)
        phoneCallIntent.data = Uri.parse("tel:$mNumber") //Uri.parse("tel:your number")
        startActivity(phoneCallIntent)
    }

    override fun onCallClick(item: Any) {
        val executiveMaster = item as ExecutiveMaster
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            currentlyActiveNumber = executiveMaster.mobile_no!!
            callingPermission.launch(Manifest.permission.CALL_PHONE)
        }else{
            callANumber(executiveMaster.mobile_no!!)
        }



    }


    override fun onBlockClick(item: Any) {

    }

    override fun onItemClick(item: Any) {
        val executive = item as ExecutiveMaster
        val bottomSheet = UserDetailSheet()
        val bundle = Bundle()
        bundle.putInt("USER_ID",executive.id!!)
        bundle.putSerializable("USER_TYPE",AdminEnum.EXECUTIVE)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

}