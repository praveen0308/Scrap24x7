package com.jmm.brsap.scrap24x7.ui.customer.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.CustomerPaymentMethodAdapter
import com.jmm.brsap.scrap24x7.adapters.SelectedScarpItemAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentCustomerPaymentMethodBinding
import com.jmm.brsap.scrap24x7.model.ModelCustomerPaymentMethod
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.MenuEnum

class CustomerPaymentMethod :
    BaseFragment<FragmentCustomerPaymentMethodBinding>(FragmentCustomerPaymentMethodBinding::inflate) {

    private lateinit var moneyAcceptanceAdapter: CustomerPaymentMethodAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvPaymentModes()
    }

    private fun prepareMoneyAcceptanceList(paytmNumber: String?): MutableList<ModelCustomerPaymentMethod> {

        val mList = ArrayList<ModelCustomerPaymentMethod>()

        mList.add(ModelCustomerPaymentMethod(MenuEnum.PAYTM,"Paytm",paytmNumber ,R.drawable.ic_paytm))
        mList.add(ModelCustomerPaymentMethod(MenuEnum.CASH,"Cash", null, R.drawable.ic_cash_method))

        return mList
    }

    override fun subscribeObservers() {
        moneyAcceptanceAdapter = CustomerPaymentMethodAdapter()
        moneyAcceptanceAdapter.setItemList(prepareMoneyAcceptanceList(null))
    }

    private fun setupRvPaymentModes() {

        binding.rvMoneyAcceptance.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = moneyAcceptanceAdapter

        }

    }

}