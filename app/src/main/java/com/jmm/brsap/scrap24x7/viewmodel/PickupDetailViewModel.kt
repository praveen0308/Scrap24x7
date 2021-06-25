package com.jmm.brsap.scrap24x7.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Data
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
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
class PickupDetailViewModel @Inject constructor(
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

    private val _pickupDetail = MutableLiveData<Resource<Data>>()
    val pickupDetail: LiveData<Resource<Data>> = _pickupDetail


    fun getPickupRequestAllDetail(pickupId:Int) {

        viewModelScope.launch {

            pickupRequestRepository
                .getPickupRequestAllDetail(pickupId)
                .onStart {
                    _pickupDetail.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _pickupDetail.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupDetail.postValue(Resource.Success(it.data))
                    } else {
                        _pickupDetail.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


}