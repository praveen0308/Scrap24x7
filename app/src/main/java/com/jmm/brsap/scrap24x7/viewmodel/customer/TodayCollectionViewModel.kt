package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.AddressType
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.repository.CustomerAddressRepository
import com.jmm.brsap.scrap24x7.repository.PickupRequestRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayCollectionViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val pickupRequestRepository: PickupRequestRepository
) : ViewModel() {

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status: Int) = viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }

    private val _pickupRequests = MutableLiveData<Resource<List<PickupRequest>>>()
    val pickupRequests: LiveData<Resource<List<PickupRequest>>> = _pickupRequests


    fun getPickupRequests(from: String,to:String,executiveId: Int) {

        viewModelScope.launch {

            pickupRequestRepository
                .getPickupRequestAcExecutive(from, to, executiveId)
                .onStart {
                    _pickupRequests.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _pickupRequests.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupRequests.postValue(Resource.Success(it.data.pickup_requests))
                    } else {
                        _pickupRequests.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


}