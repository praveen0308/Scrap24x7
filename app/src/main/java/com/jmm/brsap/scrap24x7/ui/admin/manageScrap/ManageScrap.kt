package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.AdminUniversalUtilAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentManageScrapBinding
import com.jmm.brsap.scrap24x7.model.ModelAdminUniversalUtil
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum

class ManageScrap : BaseFragment<FragmentManageScrapBinding>(FragmentManageScrapBinding::inflate),
    AdminUniversalUtilAdapter.AdminUniversalUtilInterface {

    private lateinit var adminUniversalUtilAdapter: AdminUniversalUtilAdapter
    override fun subscribeObservers() {
        setUpRVNavigation()
        adminUniversalUtilAdapter.setItemList(getAdminNavigationItem())
    }

    private fun setUpRVNavigation() {
        adminUniversalUtilAdapter = AdminUniversalUtilAdapter(this)
        binding.rvManageScrap.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(
                context,
                layoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)

            this.layoutManager = layoutManager
            adapter = adminUniversalUtilAdapter
        }
    }

    private fun getAdminNavigationItem(): List<ModelAdminUniversalUtil> {
        val mList = mutableListOf<ModelAdminUniversalUtil>()

        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.MANAGE_SCRAP_CATEGORY,
                AdminEnum.MANAGE_CATEGORIES,
                "Manage Category",
                "",
                R.drawable.ic_round_format_list_bulleted_24
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.MANAGE_SCRAP_ITEMS,
                AdminEnum.MANAGE_CATEGORIES,
                "Scrap Items",
                "",
                R.drawable.ic_unselect_plastic
            )
        )
        mList.add(
            ModelAdminUniversalUtil(
                AdminEnum.MANAGE_UNIT,
                AdminEnum.MANAGE_CATEGORIES,
                "Units",
                "",
                R.drawable.ic_baseline_design_services_24
            )
        )

        return mList

    }

    override fun onItemClick(item: ModelAdminUniversalUtil) {
        val intent = Intent(requireActivity(), ManageScrapActivity::class.java)
        intent.putExtra("SOURCE", item.id)
        startActivity(intent)
    }

    override fun onAddClick(item: ModelAdminUniversalUtil) {
        when (item.id) {
            AdminEnum.MANAGE_SCRAP_CATEGORY -> {
                val intent = Intent(requireActivity(), AddScrapCategory::class.java)
                intent.putExtra("ACTION", AdminEnum.ADD)
                startActivity(intent)
            }
            AdminEnum.MANAGE_SCRAP_ITEMS -> {
                // open bottom sheet
                val bottomSheet = AddNewScrapItem()
                val bundle = Bundle()
                bundle.putSerializable("ACTION", AdminEnum.ADD)
                bottomSheet.arguments = bundle
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            AdminEnum.MANAGE_UNIT -> {
                // open bottom sheet
                val bottomSheet = AddNewUnit()
                val bundle = Bundle()
                bundle.putSerializable("ACTION", AdminEnum.ADD)
                bottomSheet.arguments = bundle
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }
    }
}

