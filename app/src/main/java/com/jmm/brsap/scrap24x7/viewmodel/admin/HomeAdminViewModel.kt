package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.repository.DashboardRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAdminViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val userPreferencesRepository: UserPreferencesRepository
):ViewModel() {

    private val _systemStatistics = MutableLiveData<Resource<ApiResponse>>()
    val systemStatistics: LiveData<Resource<ApiResponse>> = _systemStatistics
    fun getSystemStatistics() {

        viewModelScope.launch {

            dashboardRepository
                .getSystemStatistics()
                .onStart {
                    _systemStatistics.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _systemStatistics.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _systemStatistics.postValue(Resource.Success(it))
                    } else {
                        _systemStatistics.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _pickupRequestSummary = MutableLiveData<Resource<ApiResponse>>()
    val pickupRequestSummary: LiveData<Resource<ApiResponse>> = _pickupRequestSummary

    fun getPickupRequestSummary(pickupDate:String) {

        viewModelScope.launch {
            dashboardRepository
                .getPickupRequestsCount(pickupDate)
                .onStart {
                    _pickupRequestSummary.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _pickupRequestSummary.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupRequestSummary.postValue(Resource.Success(it))
                    } else {
                        _pickupRequestSummary.postValue(Resource.Error(it.message))
                    }
                }
        }

    }
    fun clearUserInfo() = viewModelScope.launch {
        userPreferencesRepository.clearUserInfo()
    }

}