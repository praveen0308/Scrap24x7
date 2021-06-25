package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.model.LatLng
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentAddNewAddressSheetBinding
import com.jmm.brsap.scrap24x7.model.network_models.AddressType
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.ui.BaseBottomSheetDialogFragment
import com.jmm.brsap.scrap24x7.util.Status
import com.jmm.brsap.scrap24x7.viewmodel.customer.AddNewAddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddNewAddressSheet : BaseBottomSheetDialogFragment<FragmentAddNewAddressSheetBinding>(FragmentAddNewAddressSheetBinding::inflate) {

    private val viewModel by viewModels<AddNewAddressViewModel>()
    private var selectedAddressTypeId : Int = 0
    private var userId : Int = 0
    private lateinit var selectedAddress:Address
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<LatLng>("LAT_LNG")?.let { getAddressDetail(it) }
        viewModel.getAddressTypes()

        binding.btnSubmit.setOnClickListener {
            if (selectedAddressTypeId!=0){
                val customerAddress= CustomerAddress(
                        address1 = binding.etAddressLine.text.toString(),
                        customer_id = userId,
                        country = selectedAddress.countryName,
                        state = selectedAddress.adminArea,
                        city = selectedAddress.locality,
                        address_type_id = selectedAddressTypeId,
                        locality = selectedAddress.subLocality,
                        street = selectedAddress.premises,
                        latitude = selectedAddress.latitude,
                        longitude = selectedAddress.longitude,
                        address2 = selectedAddress.postalCode

                )

                viewModel.addNewCustomerAddress(customerAddress)
            }
            else{
                showToast("Select address type !!")
            }

        }
    }

    override fun subscribeObservers() {
        viewModel.userId.observe(viewLifecycleOwner, {
            userId = it
        })
        viewModel.addressTypeList.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        populateAddressTypeList(it.toMutableList())
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

        viewModel.customerAddress.observe(viewLifecycleOwner, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        showToast("Added Successfully !!!")
                        requireActivity().finish()
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

    private fun getAddressDetail(latLng: LatLng){
        val gcd = Geocoder(
            requireContext(),
            Locale.getDefault()
        )
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(
                latLng.latitude,
                latLng.longitude, 1
            )
            if (addresses.isNotEmpty()) {
                selectedAddress = addresses[0]
                populateFields(addresses[0])

            }
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(
                requireContext(),
                e.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun populateFields(address:Address){
        binding.apply {
            etLocality.setText(address.locality.toString())
            if (address.getSubLocality()!=null){
                etSubLocality.setText(address.getSubLocality().toString())
            }

            etPincode.setText(address.postalCode.toString())
            etAddressLine.setText(address.getAddressLine(0).toString())

        }
    }

    private fun populateAddressTypeList(mList: MutableList<AddressType>){
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.my_custom_dropdown_list_item, mList)
        binding.actvAddressType.threshold = 1
        binding.actvAddressType.setAdapter(arrayAdapter)

        binding.actvAddressType.setOnItemClickListener { parent, view, position, id ->
            selectedAddressTypeId = (parent.getItemAtPosition(position) as AddressType).type_id!!

        }
    }
}