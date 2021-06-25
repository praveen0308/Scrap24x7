package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.AddressType
import com.jmm.brsap.scrap24x7.model.network_models.Customer
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
class CustomerAccountViewModel @Inject constructor(
        private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val firstName = userPreferencesRepository.firstName.asLiveData()
    val lastName = userPreferencesRepository.lastName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status: Int) = viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }

    //    private val _isCleared = MutableLiveData<Resource<Boolean>>()
//    val isCleared: LiveData<Resource<Boolean>> = _isCleared
//
//    fun clearUserInfo() {
//        viewModelScope.launch {
//            userPreferencesRepository
//                    .clearUserInfo()
//                    .onStart {
//                        _isCleared.postValue(Resource.Loading(true))
//                    }
//                    .catch { exception ->
//                        exception.message?.let {
//                            _isCleared.postValue(Resource.Error("Something went wrong !!"))
//                            Log.e("ERROR", exception.message, exception)
//                        }
//
//
//                    }
//                    .collect {
//                        _isCleared.postValue(Resource.Success(it))
//                    }
//        }
//
//    }
    fun clearUserInfo() = viewModelScope.launch {
        userPreferencesRepository.clearUserInfo()
    }


}