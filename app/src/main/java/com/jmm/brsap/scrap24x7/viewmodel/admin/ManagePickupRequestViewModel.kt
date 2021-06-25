package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.FilterModel
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.repository.FilterRepository
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.repository.PickupRequestRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.FilterEnum
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagePickupRequestViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val pickupRequestRepository: PickupRequestRepository,
    private val locationRepository: LocationRepository,
    private val filterRepository: FilterRepository
) : ViewModel() {


    val selectedTimeFilter = MutableLiveData(FilterEnum.TODAY)
    val selectedLocationIds = MutableLiveData(listOf<Int>())
    val selectedPickupRequestType = MutableLiveData(FilterEnum.ALL)

    fun assignSelectedTimeFilter(enum: FilterEnum){
        selectedTimeFilter.postValue(enum)
    }

    fun setSelectedLocationIds(locationIds: List<Int>){
        selectedLocationIds.postValue(locationIds)
    }

    fun setSelectedPRType(type: FilterEnum){
        selectedPickupRequestType.postValue(type)
    }

    private val _pickupRequestList = MutableLiveData<Resource<List<PickupRequest>>>()
    val pickupRequestList: LiveData<Resource<List<PickupRequest>>> = _pickupRequestList

    fun getPickupHistoryList(from: String,to:String,locationIds:List<Int>) {

        viewModelScope.launch {
            pickupRequestRepository
                .getPickupRequestList(from, to, locationIds)
                .onStart {
                    _pickupRequestList.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _pickupRequestList.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupRequestList.postValue(Resource.Success(it.data.pickup_requests))
                    } else {
                        _pickupRequestList.postValue(Resource.Error(it.message))
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

    private val _locations = MutableLiveData<Resource<List<LocationMaster>>>()
    val locations: LiveData<Resource<List<LocationMaster>>> = _locations

    fun getLocations() {
        viewModelScope.launch {
            locationRepository
                .getLocations()
                .onStart {
                    _locations.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _locations.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {

                        _locations.postValue(Resource.Success(it.data.location_masters))
                    } else {
                        _locations.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _timeFilters = MutableLiveData<List<FilterModel>>()
    val timeFilters: LiveData<List<FilterModel>> = _timeFilters

    fun getTimeFilters(){
        val filteredList =filterRepository.getTimeFilters()
        filteredList.forEach {
            if(it.filterId == selectedTimeFilter.value) it.isSelected = true
        }
        _timeFilters.postValue(filteredList)
    }


}