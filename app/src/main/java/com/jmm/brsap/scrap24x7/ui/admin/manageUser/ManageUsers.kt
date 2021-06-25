package com.jmm.brsap.scrap24x7.ui.admin.manageUser

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AdminUniversalUtilAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageUsersBinding
import com.jmm.brsap.scrap24x7.model.ModelAdminUniversalUtil
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum

class ManageUsers : BaseFragment<FragmentManageUsersBinding>(FragmentManageUsersBinding::inflate),
    AdminUniversalUtilAdapter.AdminUniversalUtilInterface {

    private lateinit var adminUniversalUtilAdapter: AdminUniversalUtilAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResultLauncher()
        setUpRVNavigation()
        adminUniversalUtilAdapter.setItemList(getAdminNavigationItem())
    }
    override fun subscribeObservers() {

    }
    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (result.resultCode == RESULT_OK ) {
                    showToast(data!!.getStringExtra("Message")!!)
                } else {
                    showToast("Cancelled !!")
                }
//                if (result.resultCode == ManageDriver.ADD_DRIVER || result.resultCode == ManageDriver.UPDATE_DRIVER) {
//                    showToast(data!!.getStringExtra("Message")!!)
//                } else {
//                    showToast("Cancelled !!")
//                }

            }
    }

    private fun openActivityForAddDriver() {
        val intent = Intent(requireActivity(), AddNewDriver::class.java)
        intent.putExtra("ACTION", AdminEnum.ADD)
        resultLauncher.launch(intent)
    }

    private fun openActivityForAddExecutive() {
        val intent = Intent(requireActivity(), AddNewExecutive::class.java)
        intent.putExtra("ACTION", AdminEnum.ADD)
        resultLauncher.launch(intent)
    }
    private fun setUpRVNavigation() {
        adminUniversalUtilAdapter = AdminUniversalUtilAdapter(this)
        binding.rvManageUsers.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = adminUniversalUtilAdapter
        }
    }

    private fun getAdminNavigationItem(): List<ModelAdminUniversalUtil> {
        val mList = mutableListOf<ModelAdminUniversalUtil>()

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.CUSTOMER,
                AdminEnum.MANAGE_CATEGORIES,
                "Customers",
                "",
                R.drawable.ic_baseline_person_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.EXECUTIVE,
                AdminEnum.MANAGE_CATEGORIES,
                "Executives",
                "",
                R.drawable.ic_baseline_engineering_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.DRIVER,
                AdminEnum.MANAGE_CATEGORIES,
                "Drivers",
                "",
                R.drawable.ic_steering_wheel
            )
        )

        return mList

    }

   override fun onItemClick(item: ModelAdminUniversalUtil) {
       val intent = Intent(requireActivity(), ManageUserActivity::class.java)
        intent.putExtra("SOURCE",item.id)
        startActivity(intent)
    }

    override fun onAddClick(item: ModelAdminUniversalUtil) {
        when(item.id){
            AdminEnum.DRIVER->openActivityForAddDriver()
            AdminEnum.EXECUTIVE->openActivityForAddExecutive()
            else -> {
                //nothing
            }
        }
    }

}