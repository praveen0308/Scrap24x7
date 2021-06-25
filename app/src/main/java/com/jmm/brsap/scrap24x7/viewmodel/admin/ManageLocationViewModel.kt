package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
):ViewModel(){

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
                        Log.e("ERROR",exception.toString())
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


    private val _addedLocationMaster = MutableLiveData<Resource<LocationMaster>>()
    val addedLocationMaster: LiveData<Resource<LocationMaster>> = _addedLocationMaster

    fun addNewLocation(locationMaster: LocationMaster) {

        viewModelScope.launch {

            locationRepository
                .addNewLocation(locationMaster)
                .onStart {
                    _addedLocationMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _addedLocationMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _addedLocationMaster.postValue(Resource.Success(it.data.location_master))
                    } else {
                        _addedLocationMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _locationMaster = MutableLiveData<Resource<LocationMaster>>()
    val locationMaster: LiveData<Resource<LocationMaster>> = _locationMaster

    fun getLocationById(locationId: Int) {

        viewModelScope.launch {

            locationRepository
                .getLocationById(locationId)
                .onStart {
                    _locationMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _locationMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _locationMaster.postValue(Resource.Success(it.data.location_master))
                    } else {
                        _locationMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _updatedLocationMaster = MutableLiveData<Resource<LocationMaster>>()
    val updatedLocationMaster: LiveData<Resource<LocationMaster>> = _updatedLocationMaster

    fun updateLocation(locationMaster: LocationMaster) {

        viewModelScope.launch {

            locationRepository
                .updateLocation(locationMaster)
                .onStart {
                    _updatedLocationMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _updatedLocationMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _updatedLocationMaster.postValue(Resource.Success(it.data.location_master))
                    } else {
                        _updatedLocationMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _deletedLocationMaster = MutableLiveData<Resource<LocationMaster>>()
    val deletedLocationMaster: LiveData<Resource<LocationMaster>> = _deletedLocationMaster

    fun deleteLocation(locationId: Int) {

        viewModelScope.launch {

            locationRepository
                .deleteLocation(locationId)
                .onStart {
                    _deletedLocationMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _deletedLocationMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedLocationMaster.postValue(Resource.Success(it.data.location_master))
                    } else {
                        _deletedLocationMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }



}