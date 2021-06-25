package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
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
class ManageAddressViewModel @Inject constructor(
        private val customerAddressRepository: CustomerAddressRepository,
        private val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()

    private val _customerAddressList = MutableLiveData<Resource<List<CustomerAddress>>>()
    val customerAddressList: LiveData<Resource<List<CustomerAddress>>> = _customerAddressList

    fun getCustomerAddressList(customerId: Int) {
        viewModelScope.launch {
            customerAddressRepository
                    .getAddressByCustomerId(customerId)
                    .onStart {
                        _customerAddressList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            Log.e("ERROR", exception.toString())
                            _customerAddressList.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }
                    .collect {
                        if (it.data != null) {
                            _customerAddressList.postValue(Resource.Success(it.data.customer_addresses))
                        } else {
                            _customerAddressList.postValue(Resource.Error(it.message))
                        }
                    }
        }

    }

    private val _deletedAddress = MutableLiveData<Resource<CustomerAddress>>()
    val deletedAddress: LiveData<Resource<CustomerAddress>> = _deletedAddress

    fun deleteCustomerAddress(customerAddressId: Int) {
        viewModelScope.launch {
            customerAddressRepository
                .deleteCustomerAddress(customerAddressId)
                .onStart {
                    _deletedAddress.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _deletedAddress.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedAddress.postValue(Resource.Success(it.data.customer_address))
                    } else {
                        _deletedAddress.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

}