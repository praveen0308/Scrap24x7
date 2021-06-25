package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.FilterModel
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.repository.FilterRepository
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagePickupRequestFilterViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {



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


    private val _timeFilters = MutableLiveData<Resource<List<FilterModel>>>()
    val timeFilters: LiveData<Resource<List<FilterModel>>> = _timeFilters

    fun getTimeFilters() {
        viewModelScope.launch {
            filterRepository
                .getTimeFilter()
                .onStart {
                    _timeFilters.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _timeFilters.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {

                    _timeFilters.postValue(Resource.Success(it))

                }
        }

    }


    fun setTimeFilter(timeFilters: List<FilterModel>) {
        filterRepository.timeFilter.clear()
        filterRepository.timeFilter.addAll(timeFilters)
    }

    fun getTimeFilter() = filterRepository.timeFilter

    fun populateTimeFilter() {
        filterRepository.populateTimeFilter()
    }

    fun setSelectedTimeFilter(id: Int) {
        filterRepository.setSelectedTimeFilter(id)
    }


    fun addLocationFilter(locationId: Int) {
        if (!filterRepository.selectedLocationsID.contains(locationId)) filterRepository.selectedLocationsID.add(
            locationId
        )
    }

    fun removeLocationFilter(locationId: Int) {
        filterRepository.selectedLocationsID.remove(locationId)
    }

    fun getLocationFilters() = filterRepository.selectedLocationsID

}