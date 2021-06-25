package com.jmm.brsap.scrap24x7.ui.admin.manageScrap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewUnitBinding
import com.jmm.brsap.scrap24x7.model.network_models.UnitModel
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.admin.ManageScrapActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewUnit : BaseBottomSheetDialogFragment<FragmentAddNewUnitBinding>(FragmentAddNewUnitBinding::inflate) {

    private val viewModel by viewModels<ManageScrapActivityViewModel>()
    private var source = AdminEnum.MANAGE_SCRAP_CATEGORY
    private var unitId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        source = requireArguments().getSerializable("ACTION") as AdminEnum
        viewModel.getUnits()
        if (source == AdminEnum.EDIT) {
            binding.btnSubmit.text = "Update"
            unitId = requireArguments().getInt("UnitModelID",0)
            viewModel.getUnitById(unitId)
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val unitModel = UnitModel(
                    unit_name = etName.text.toString(),
                    description = etDescription.text.toString()
                )
                if (source== AdminEnum.ADD){
                    viewModel.addNewUnit(unitModel)
                }
                else{
                    viewModel.updateUnit(unitId,unitModel)
                }

            }
        }
    }

    override fun subscribeObservers() {

        viewModel.addedUnit.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Added Successfully!!!")
                        dismiss()
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

        viewModel.updatedUnit.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Updated Successfully!!!")
                        dismiss()
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
        viewModel.unit.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateUnitModelInfo(it)
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

    private fun populateUnitModelInfo(unitModel: UnitModel) {
        unitId = unitModel.id!!
        binding.apply {
            etName.setText(unitModel.unit_name)
            etDescription.setText(unitModel.description)

        }
    }


}