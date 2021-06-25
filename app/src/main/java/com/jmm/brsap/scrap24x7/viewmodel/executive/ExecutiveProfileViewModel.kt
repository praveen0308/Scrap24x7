package com.jmm.brsap.scrap24x7.viewmodel.executive

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.repository.AuthRepository
import com.jmm.brsap.scrap24x7.repository.CustomerRepository
import com.jmm.brsap.scrap24x7.repository.ExecutiveRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecutiveProfileViewModel @Inject constructor(
        private val userPreferencesRepository: UserPreferencesRepository,
        private val authRepository: AuthRepository,
        private val executiveRepository: ExecutiveRepository
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

    private val _executiveInfo = MutableLiveData<Resource<ExecutiveMaster>>()
    val executiveInfo: LiveData<Resource<ExecutiveMaster>> = _executiveInfo

    fun getExecutiveInfo(id:Int) {

        viewModelScope.launch {

            executiveRepository
                    .getExecutiveById(id)
                    .onStart {
                        _executiveInfo.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _executiveInfo.postValue(Resource.Error("Something went wrong !!"))
                            Log.e("ERROR",exception.message,exception)
                        }


                    }

                    .collect {
                        if (it.data != null) {
                            _executiveInfo.postValue(Resource.Success(it.data.executive_master))
                        } else {
                            _executiveInfo.postValue(Resource.Error(it.message))
                        }
                    }

        }
    }


    private val _updateResponse = MutableLiveData<Resource<ExecutiveMaster>>()
    val updateResponse: LiveData<Resource<ExecutiveMaster>> = _updateResponse

    fun updateCustomerInfo(executiveId:Int,executiveMaster: ExecutiveMaster) {

        viewModelScope.launch {

            executiveRepository
                    .updateExecutive(executiveId,executiveMaster)
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
                            _updateResponse.postValue(Resource.Success(it.data.executive_master))
                        } else {
                            _updateResponse.postValue(Resource.Error(it.message))
                        }
                    }

        }
    }

}