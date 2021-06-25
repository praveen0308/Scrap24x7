package com.jmm.brsap.scrap24x7.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmm.brsap.scrap24x7.adapters.MasterDetailAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentComponentDetailBinding
import com.jmm.brsap.scrap24x7.model.HeadingValueModel

class ComponentDetail : BaseBottomSheetDialogFragment<FragmentComponentDetailBinding>(FragmentComponentDetailBinding::inflate),
    MasterDetailAdapter.MasterDetailInterface {

    private lateinit var masterDetailAdapter: MasterDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvDetails()
//        val details = requireArguments().getParcelableArrayList<HeadingValueModel>("Details")

//        if (details != null) {
//            masterDetailAdapter.setHeadingValueModelList(details)
//        }
    }

    private fun setupRvDetails(){
        masterDetailAdapter = MasterDetailAdapter(this)
        binding.rvData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = masterDetailAdapter
        }
    }
    override fun subscribeObservers() {

    }

}