package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.repository.VehicleRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
):ViewModel(){

//    private val _vehicles = MutableLiveData<Resource<List<VehicleMaster>>>()
//    val vehicles: LiveData<Resource<List<VehicleMaster>>> = _vehicles
//
//    fun getVehicles() {
//
//        viewModelScope.launch {
//
//            vehicleRepository
//                .getVehicles()
//                .onStart {
//                    _vehicles.postValue(Resource.Loading(true))
//                }
//                .catch { exception ->
//                    exception.message?.let {
//                        Log.e("ERROR",exception.toString())
//                        _vehicles.postValue(Resource.Error("Something went wrong !!!"))
//                    }
//                }
//                .collect {
//                    if (it.data != null) {
//                        _vehicles.postValue(Resource.Success(it.data.vehicle_masters))
//                    } else {
//                        _vehicles.postValue(Resource.Error(it.message))
//                    }
//                }
//        }
//
//    }
val vehicles =vehicleRepository.getVehicleList().cachedIn(viewModelScope)


    private val _vehicleMaster = MutableLiveData<Resource<VehicleMaster>>()
    val vehicleMaster: LiveData<Resource<VehicleMaster>> = _vehicleMaster

    fun getVehicleById(vehicleId: Int) {

        viewModelScope.launch {

            vehicleRepository
                .getVehicleById(vehicleId)
                .onStart {
                    _vehicleMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
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

    private val _deletedVehicleMaster = MutableLiveData<Resource<VehicleMaster>>()
    val deletedVehicleMaster: LiveData<Resource<VehicleMaster>> = _deletedVehicleMaster

    fun deleteVehicle(vehicleId: Int) {

        viewModelScope.launch {

            vehicleRepository
                .deleteVehicle(vehicleId)
                .onStart {
                    _deletedVehicleMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _deletedVehicleMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedVehicleMaster.postValue(Resource.Success(it.data.vehicle_master))
                    } else {
                        _deletedVehicleMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }



}