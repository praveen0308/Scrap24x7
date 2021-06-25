package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.repository.AuthRepository
import com.jmm.brsap.scrap24x7.repository.CustomerRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerProfileViewModel @Inject constructor(
        private val userPreferencesRepository: UserPreferencesRepository,
        private val authRepository: AuthRepository,
        private val customerRepository: CustomerRepository
) : ViewModel() {

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status: Int) = viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }

    fun updateUserName(userName:String)=viewModelScope.launch {
        userPreferencesRepository.updateUserName(userName)
    }

    fun updateFirstName(firstName:String)=viewModelScope.launch {
        userPreferencesRepository.updateUserFirstName(firstName)
    }

    fun updateLastName(lastName:String)=viewModelScope.launch {
        userPreferencesRepository.updateUserLastName(lastName)
    }

    private val _customerInfo = MutableLiveData<Resource<Customer>>()
    val customerInfo: LiveData<Resource<Customer>> = _customerInfo

    fun getCustomerInfo(userId:String) {

        viewModelScope.launch {

            authRepository
                    .getCustomer(userId)
                    .onStart {
                        _customerInfo.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _customerInfo.postValue(Resource.Error("Something went wrong !!"))
                            Log.e("ERROR",exception.message,exception)
                        }


                    }

                    .collect {
                        if (it.data != null) {
                            _customerInfo.postValue(Resource.Success(it.data.customer))
                        } else {
                            _customerInfo.postValue(Resource.Error(it.message))
                        }
                    }

        }
    }


    private val _updateResponse = MutableLiveData<Resource<Customer>>()
    val updateResponse: LiveData<Resource<Customer>> = _updateResponse

    fun updateCustomerInfo(customer: Customer) {

        viewModelScope.launch {

            customerRepository
                    .updateCustomerInfo(customer)
                    .onStart {
                        _updateResponse.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _updateResponse.postValue(Resource.Error("Something went wrong !!"))
                            Log.e("ERROR",exception.message,exception)
                        }


                    }

                    .collect {
                        if (it.data != null) {
                            _updateResponse.postValue(Resource.Success(it.data.customer))
                        } else {
                            _updateResponse.postValue(Resource.Error(it.message))
                        }
                    }

        }
    }

}