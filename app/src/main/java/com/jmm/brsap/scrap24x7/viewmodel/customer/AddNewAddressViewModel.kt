package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.AddressType
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.repository.CustomerAddressRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAddressViewModel @Inject constructor(
        private val customerAddressRepository: CustomerAddressRepository,
        private val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()


    private val _addressTypeList = MutableLiveData<Resource<List<AddressType>>>()
    val addressTypeList: LiveData<Resource<List<AddressType>>> = _addressTypeList
    fun getAddressTypes() {

        viewModelScope.launch {

            customerAddressRepository
                    .getAddressTypes()
                    .onStart {
                        _addressTypeList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            Log.e("ERROR",exception.toString())
                            _addressTypeList.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }
                    .collect {
                        if (it.data != null) {
                            _addressTypeList.postValue(Resource.Success(it.data.address_types))
                        } else {
                            _addressTypeList.postValue(Resource.Error(it.message))
                        }
                    }
        }

    }


    private val _customerAddress = MutableLiveData<Resource<CustomerAddress>>()
    val customerAddress: LiveData<Resource<CustomerAddress>> = _customerAddress

    fun addNewCustomerAddress(customerAddress: CustomerAddress) {

        viewModelScope.launch {

            customerAddressRepository
                    .addNewCustomerAddress(customerAddress)
                    .onStart {
                        _customerAddress.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            Log.e("ERROR",exception.toString())
                            _customerAddress.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }
                    .collect {
                        if (it.data != null) {
                            _customerAddress.postValue(Resource.Success(it.data.customer_address))
                        } else {
                            _customerAddress.postValue(Resource.Error(it.message))
                        }
                    }
        }

    }

}