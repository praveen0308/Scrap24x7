package com.jmm.brsap.scrap24x7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.repository.CustomerRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _registrationResponse = MutableLiveData<Resource<Customer>>()
    val registrationResponse: LiveData<Resource<Customer>> = _registrationResponse


    fun registerNewCustomer(customer: Customer) {

        viewModelScope.launch {

            customerRepository
                .registerNewCustomer(customer)
                .onStart {
                    _registrationResponse.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _registrationResponse.postValue(Resource.Error(it))
                    }


                }

                .collect {
                    if (it.data != null) {
                        _registrationResponse.postValue(Resource.Success(it.data.customer))
                    } else {
                        _registrationResponse.postValue(Resource.Error(it.message))
                    }
                }

        }
    }

}


