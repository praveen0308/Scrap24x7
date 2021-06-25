package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.Manifest
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
import com.jmm.brsap.scrap24x7.databinding.FragmentManageCustomerBinding
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.pagingDataAdapter.CustomerPagingAdapter
import com.jmm.brsap.scrap24x7.pagingDataAdapter.ManageUserInterface
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageUserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ManageCustomer :
    BaseFragment<FragmentManageCustomerBinding>(FragmentManageCustomerBinding::inflate),
    ManageUserInterface {

    private val viewModel by viewModels<ManageUserActivityViewModel>()

    private lateinit var customerPagingAdapter: CustomerPagingAdapter

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
            viewModel.customers.collectLatest { pagedData ->
                customerPagingAdapter.submitData(pagedData as PagingData<Customer>)
            }
        }

    }

    override fun subscribeObservers() {

    }

    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode == ADD_CUSTOMER || result.resultCode == UPDATE_CUSTOMER) {
                    showToast(data!!.getStringExtra("Message")!!)
                } else {
                    showToast("Cancelled !!")
                }

            }
    }


    private fun setupRvData() {
        customerPagingAdapter = CustomerPagingAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(
                context,
                layoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = customerPagingAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { customerPagingAdapter::retry },
                footer = MyLoadStateAdapter { customerPagingAdapter::retry }
            )
        }
    }


    companion object {
        const val ADD_CUSTOMER = 101
        const val UPDATE_CUSTOMER = 102

    }

    private fun callANumber(mNumber: String) {
        val phoneCallIntent = Intent(Intent.ACTION_CALL)
        phoneCallIntent.data = Uri.parse("tel:$mNumber") //Uri.parse("tel:your number")
        startActivity(phoneCallIntent)
    }

    override fun onCallClick(item: Any) {
        val customer = item as Customer
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            currentlyActiveNumber = customer.mobile_number
            callingPermission.launch(Manifest.permission.CALL_PHONE)
        }else{
            callANumber(customer.mobile_number)
        }
    }

    override fun onBlockClick(item: Any) {

    }

    override fun onItemClick(item: Any) {
        val customer = item as Customer
        val bottomSheet = UserDetailSheet()
        val bundle = Bundle()
        bundle.putInt("USER_ID",customer.customer_id!!)
        bundle.putSerializable("USER_TYPE",AdminEnum.CUSTOMER)
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager,tag)
    }

}