package com.jmm.brsap.scrap24x7.ui.customer.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.CustomerAddressListAdapter
import com.jmm.brsap.scrap24x7.adapters.HowItWorksCategoryItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentHowItWorksBinding
import com.jmm.brsap.scrap24x7.model.ModelHowItWorksCategoryItem
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.OtherEnum

class HowItWorks : BaseFragment<FragmentHowItWorksBinding>(FragmentHowItWorksBinding::inflate) {

    private lateinit var howItWorksCategoryItemAdapter: HowItWorksCategoryItemAdapter
    override fun subscribeObservers() {
setUpRVCustomerAddress()
    }

    private fun setUpRVCustomerAddress() {
        howItWorksCategoryItemAdapter = HowItWorksCategoryItemAdapter(prepareWorkCategoryItemList())
        binding.rvHowItWorksList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = howItWorksCategoryItemAdapter
        }
    }
    private fun prepareWorkCategoryItemList():MutableList<ModelHowItWorksCategoryItem>{

        val categoryItemList = ArrayList<ModelHowItWorksCategoryItem>()

        categoryItemList.add(ModelHowItWorksCategoryItem("1.","Select scrap items for selling",getString(R.string.msg_onboarding_1),R.drawable.ic_unselect_others))

        categoryItemList.add(ModelHowItWorksCategoryItem("2.","Choose a date for scrap pickup",getString(R.string.msg_onboarding_2),R.drawable.ic_unselect_e_waste))

        categoryItemList.add(ModelHowItWorksCategoryItem("3.","Pickup boys will arrive at your home",getString(R.string.msg_onboarding_3),R.drawable.ic_unselect_others))

        categoryItemList.add(ModelHowItWorksCategoryItem("4.","Scrap sold :-)",getString(R.string.msg_onboarding_4),R.drawable.ic_unselect_e_waste))

        return categoryItemList
    }



}