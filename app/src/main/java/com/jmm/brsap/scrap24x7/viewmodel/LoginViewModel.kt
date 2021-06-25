package com.jmm.brsap.scrap24x7.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.UserMaster
import com.jmm.brsap.scrap24x7.repository.AuthRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository

):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status:Int)=viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }

    fun updateUserId(status:Int)=viewModelScope.launch {
        userPreferencesRepository.updateUserId(status)
    }

    fun updateLoginUserName(loginUserName:String)=viewModelScope.launch {
        userPreferencesRepository.updateLoginUserName(loginUserName)
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

    fun updateUserRoleID(roleId:Int)=viewModelScope.launch {
        userPreferencesRepository.updateUserRoleId(roleId)
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

    private val _userMaster = MutableLiveData<Resource<UserMaster>>()
    val userMaster: LiveData<Resource<UserMaster>> = _userMaster

    fun checkStaffLogin(userId:String,password:String) {

        viewModelScope.launch {

            authRepository
                .checkStaffLogin(userId,password)
                .onStart {
                    _userMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _userMaster.postValue(Resource.Error("Something went wrong !!"))
                        Log.e("ERROR",exception.message,exception)
                    }


                }

                .collect {
                    if (it.data != null) {
                        _userMaster.postValue(Resource.Success(it.data.user_master))
                    } else {
                        _userMaster.postValue(Resource.Error(it.message))
                    }
                }

        }
    }

}