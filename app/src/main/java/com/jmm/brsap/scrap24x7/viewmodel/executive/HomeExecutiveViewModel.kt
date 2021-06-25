package com.jmm.brsap.scrap24x7.viewmodel.executive

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.AddressType
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.repository.CustomerAddressRepository
import com.jmm.brsap.scrap24x7.repository.DashboardRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.MenuEnum
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeExecutiveViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status: Int) = viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }
    fun clearUserInfo() = viewModelScope.launch {
        userPreferencesRepository.clearUserInfo()
    }

    val activePage = MutableLiveData(MenuEnum.EXECUTIVE_DASHBOARD)

    fun setCurrentActivePage(page:MenuEnum){
        activePage.postValue(page)
    }

    private val _pickupSummary = MutableLiveData<Resource<ApiResponse>>()
    val pickupSummary: LiveData<Resource<ApiResponse>> = _pickupSummary

    fun getExecutivePickupSummary(from:String,to:String,executive_id:Int) {
        viewModelScope.launch {
            dashboardRepository
                .getExecutivePickupSummary(from, to, executive_id)
                .onStart {
                    _pickupSummary.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _pickupSummary.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupSummary.postValue(Resource.Success(it))
                    } else {
                        _pickupSummary.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


}