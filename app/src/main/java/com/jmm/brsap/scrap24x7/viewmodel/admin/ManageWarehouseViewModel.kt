package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.Warehouse
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.repository.WarehouseRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageWarehouseViewModel @Inject constructor(
    private val warehouseRepository: WarehouseRepository,
    private val locationRepository: LocationRepository
):ViewModel(){


    //------
    private val _warehouses = MutableLiveData<Resource<List<Warehouse>>>()
    val warehouses: LiveData<Resource<List<Warehouse>>> = _warehouses

    fun getWarehouses() {

        viewModelScope.launch {

            warehouseRepository
                .getWarehouses()
                .onStart {
                    _warehouses.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _warehouses.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _warehouses.postValue(Resource.Success(it.data.warehouses))
                    } else {
                        _warehouses.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    //------
    private val _addedWarehouse = MutableLiveData<Resource<Warehouse>>()
    val addedWarehouse: LiveData<Resource<Warehouse>> = _addedWarehouse

    fun addNewWarehouse(warehouse: Warehouse) {

        viewModelScope.launch {

            warehouseRepository
                .addNewWarehouse(warehouse)
                .onStart {
                    _addedWarehouse.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _addedWarehouse.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _addedWarehouse.postValue(Resource.Success(it.data.warehouse))
                    } else {
                        _addedWarehouse.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    //------
    private val _warehouse = MutableLiveData<Resource<Warehouse>>()
    val warehouse: LiveData<Resource<Warehouse>> = _warehouse

    fun getWarehouseById(warehouseId: Int) {

        viewModelScope.launch {

            warehouseRepository
                .getWarehouseById(warehouseId)
                .onStart {
                    _warehouse.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _warehouse.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _warehouse.postValue(Resource.Success(it.data.warehouse))
                    } else {
                        _warehouse.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    //------
    private val _updatedWarehouse = MutableLiveData<Resource<Warehouse>>()
    val updatedWarehouse: LiveData<Resource<Warehouse>> = _updatedWarehouse

    fun updateWarehouse(warehouseId: Int,warehouse: Warehouse) {

        viewModelScope.launch {

            warehouseRepository
                .updateWarehouse(warehouseId, warehouse)
                .onStart {
                    _updatedWarehouse.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _updatedWarehouse.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _updatedWarehouse.postValue(Resource.Success(it.data.warehouse))
                    } else {
                        _updatedWarehouse.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    //------
    private val _deletedWarehouse = MutableLiveData<Resource<Warehouse>>()
    val deletedWarehouse: LiveData<Resource<Warehouse>> = _deletedWarehouse

    fun deleteWarehouse(warehouseId: Int) {

        viewModelScope.launch {

            warehouseRepository
                .deleteWarehouse(warehouseId)
                .onStart {
                    _deletedWarehouse.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _deletedWarehouse.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedWarehouse.postValue(Resource.Success(it.data.warehouse))
                    } else {
                        _deletedWarehouse.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    //------
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


}