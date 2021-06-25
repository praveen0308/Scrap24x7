package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster
import com.jmm.brsap.scrap24x7.repository.DriverRepository
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewDriverViewModel @Inject constructor(
    private val driverRepository: DriverRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    

    private val _driverMaster = MutableLiveData<Resource<DriverMaster>>()
    val driverMaster: LiveData<Resource<DriverMaster>> = _driverMaster

    fun addNewDriver(driverMaster: DriverMaster) {
        viewModelScope.launch {

            driverRepository
                .addNewDriver(driverMaster)
                .onStart {
                    _driverMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _driverMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _driverMaster.postValue(Resource.Success(it.data.driver_master))
                    } else {
                        _driverMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    fun updateDriverInfo(driverId:Int,driverMaster: DriverMaster) {
        viewModelScope.launch {

            driverRepository
                .updateDriver(driverId, driverMaster)
                .onStart {
                    _driverMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _driverMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _driverMaster.postValue(Resource.Success(it.data.driver_master))
                    } else {
                        _driverMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _driverMasterDetail = MutableLiveData<Resource<DriverMaster>>()
    val driverMasterDetail: LiveData<Resource<DriverMaster>> = _driverMasterDetail

    fun getDriverById(driverId: Int) {

        viewModelScope.launch {

            driverRepository
                .getDriverById(driverId)
                .onStart {
                    _driverMasterDetail.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _driverMasterDetail.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _driverMasterDetail.postValue(Resource.Success(it.data.driver_master))
                    } else {
                        _driverMasterDetail.postValue(Resource.Error(it.message))
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


}