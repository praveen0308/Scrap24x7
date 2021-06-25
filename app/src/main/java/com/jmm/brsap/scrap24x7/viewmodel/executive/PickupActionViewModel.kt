package com.jmm.brsap.scrap24x7.viewmodel.executive

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
class PickupActionViewModel @Inject constructor(
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

    private val _pickupRequest = MutableLiveData<Resource<PickupRequest>>()
    val pickupRequest: LiveData<Resource<PickupRequest>> = _pickupRequest

    fun getPickupRequestDetail(pickupId:Int) {

        viewModelScope.launch {

            pickupRequestRepository
                .getPickupRequestDetails(pickupId)
                .onStart {
                    _pickupRequest.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _pickupRequest.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupRequest.postValue(Resource.Success(it.data.pickup_request))
                    } else {
                        _pickupRequest.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _pickupStatus = MutableLiveData<Resource<PickupRequest>>()
    val pickupStatus: LiveData<Resource<PickupRequest>> = _pickupStatus

    fun updatePickupStatus(pickupId:Int,status: Int) {

        viewModelScope.launch {

            pickupRequestRepository
                .updatePickupStatus(pickupId, status)
                .onStart {
                    _pickupStatus.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _pickupStatus.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupStatus.postValue(Resource.Success(it.data.pickup_request))
                    } else {
                        _pickupStatus.postValue(Resource.Error(it.message))
                    }
                }
        }
    }

}