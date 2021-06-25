package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.ModelAssignmentComponent
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.repository.DriverRepository
import com.jmm.brsap.scrap24x7.repository.ExecutiveRepository
import com.jmm.brsap.scrap24x7.repository.PickupRequestRepository
import com.jmm.brsap.scrap24x7.repository.VehicleRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignPickupRequestViewModel @Inject constructor(
    private val pickupRequestRepository: PickupRequestRepository,
    private val driverRepository: DriverRepository,
    private val executiveRepository: ExecutiveRepository,
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun updateMessage(msg: String){
        _message.postValue(msg)
    }

    private val _selectedVehicle = MutableLiveData<ModelAssignmentComponent>()
    val selectedVehicle: LiveData<ModelAssignmentComponent> = _selectedVehicle

    fun setSelectedVehicle(vehicleMaster: ModelAssignmentComponent){
        _selectedVehicle.postValue(vehicleMaster)
    }

    private val _selectedDriver = MutableLiveData<ModelAssignmentComponent>()
    val selectedDriver: LiveData<ModelAssignmentComponent> = _selectedDriver

    fun setSelectedDriver(driverMaster: ModelAssignmentComponent){
        _selectedDriver.postValue(driverMaster)
    }

    private val _selectedExecutive = MutableLiveData<ModelAssignmentComponent>()
    val selectedExecutive: LiveData<ModelAssignmentComponent> = _selectedExecutive

    fun setSelectedExecutive(executiveMaster: ModelAssignmentComponent){
        _selectedExecutive.postValue(executiveMaster)
    }


    private val _pickupRequest = MutableLiveData<Resource<PickupRequest>>()
    val pickupRequest: LiveData<Resource<PickupRequest>> = _pickupRequest

    fun getPickupRequestById(pickupId:Int) {
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

    private val _acceptedPickupRequest = MutableLiveData<Resource<PickupRequest>>()
    val acceptedPickupRequest: LiveData<Resource<PickupRequest>> = _acceptedPickupRequest


    fun acceptPickupRequest(pickupId: String,driverId:Int,executiveId: Int,vehicleId:Int) {
        viewModelScope.launch {
            pickupRequestRepository
                .acceptPickupRequest(pickupId, driverId, executiveId, vehicleId)
                .onStart {
                    _acceptedPickupRequest.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _acceptedPickupRequest.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _acceptedPickupRequest.postValue(Resource.Success(it.data.pickup_request))
                    } else {
                        _acceptedPickupRequest.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _drivers = MutableLiveData<Resource<List<DriverMaster>>>()
    val drivers: LiveData<Resource<List<DriverMaster>>> = _drivers

    fun getDriversByLocationId(locationId:Int) {
        viewModelScope.launch {
            driverRepository
                .getDriversByLocationId(locationId)
                .onStart {
                    _drivers.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _drivers.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _drivers.postValue(Resource.Success(it.data.driver_masters))
                    } else {
                        _drivers.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _executives = MutableLiveData<Resource<List<ExecutiveMaster>>>()
    val executives: LiveData<Resource<List<ExecutiveMaster>>> = _executives


    fun getExecutivesByLocationId(locationId:Int) {

        viewModelScope.launch {

            executiveRepository
                .getExecutivesByLocationId(locationId)
                .onStart {
                    _executives.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _executives.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _executives.postValue(Resource.Success(it.data.executive_masters))
                    } else {
                        _executives.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _vehicles = MutableLiveData<Resource<List<VehicleMaster>>>()
    val vehicles: LiveData<Resource<List<VehicleMaster>>> = _vehicles

    fun getVehiclesByLocationId(locationId:Int) {
        viewModelScope.launch {
            vehicleRepository
                .getVehiclesByLocationId(locationId)
                .onStart {
                    _vehicles.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _vehicles.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _vehicles.postValue(Resource.Success(it.data.vehicle_masters))
                    } else {
                        _vehicles.postValue(Resource.Error(it.message))
                    }
                }
        }

    }



}