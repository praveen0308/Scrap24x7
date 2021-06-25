package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.repository.VehicleRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewVehicleViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val vehicleRepository: VehicleRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status: Int) = viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }

    private val _vehicleMaster = MutableLiveData<Resource<VehicleMaster>>()
    val vehicleMaster: LiveData<Resource<VehicleMaster>> = _vehicleMaster

    fun addNewVehicle(vehicleMaster: VehicleMaster) {
        viewModelScope.launch {

            vehicleRepository
                .addNewVehicle(vehicleMaster)
                .onStart {
                    _vehicleMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _vehicleMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _vehicleMaster.postValue(Resource.Success(it.data.vehicle_master))
                    } else {
                        _vehicleMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    fun updateVehicleInfo(vehicleId:Int,vehicleMaster: VehicleMaster) {
        viewModelScope.launch {

            vehicleRepository
                .updateVehicle(vehicleId, vehicleMaster)
                .onStart {
                    _vehicleMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _vehicleMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _vehicleMaster.postValue(Resource.Success(it.data.vehicle_master))
                    } else {
                        _vehicleMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _vehicleMasterDetail = MutableLiveData<Resource<VehicleMaster>>()
    val vehicleMasterDetail: LiveData<Resource<VehicleMaster>> = _vehicleMasterDetail

    fun getVehicleById(vehicleId: Int) {

        viewModelScope.launch {

            vehicleRepository
                .getVehicleById(vehicleId)
                .onStart {
                    _vehicleMasterDetail.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _vehicleMasterDetail.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _vehicleMasterDetail.postValue(Resource.Success(it.data.vehicle_master))
                    } else {
                        _vehicleMasterDetail.postValue(Resource.Error(it.message))
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