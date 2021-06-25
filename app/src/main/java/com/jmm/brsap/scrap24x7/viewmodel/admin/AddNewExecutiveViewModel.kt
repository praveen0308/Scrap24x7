package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.repository.ExecutiveRepository
import com.jmm.brsap.scrap24x7.repository.LocationRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewExecutiveViewModel @Inject constructor(
    private val executiveRepository: ExecutiveRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    

    private val _executiveMaster = MutableLiveData<Resource<ExecutiveMaster>>()
    val executiveMaster: LiveData<Resource<ExecutiveMaster>> = _executiveMaster

    fun addNewExecutive(executiveMaster: ExecutiveMaster) {
        viewModelScope.launch {

            executiveRepository
                .addNewExecutive(executiveMaster)
                .onStart {
                    _executiveMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _executiveMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _executiveMaster.postValue(Resource.Success(it.data.executive_master))
                    } else {
                        _executiveMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    fun updateExecutiveInfo(executiveId:Int,executiveMaster: ExecutiveMaster) {
        viewModelScope.launch {

            executiveRepository
                .updateExecutive(executiveId, executiveMaster)
                .onStart {
                    _executiveMaster.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _executiveMaster.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _executiveMaster.postValue(Resource.Success(it.data.executive_master))
                    } else {
                        _executiveMaster.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _executiveMasterDetail = MutableLiveData<Resource<ExecutiveMaster>>()
    val executiveMasterDetail: LiveData<Resource<ExecutiveMaster>> = _executiveMasterDetail

    fun getExecutiveById(executiveId: Int) {

        viewModelScope.launch {

            executiveRepository
                .getExecutiveById(executiveId)
                .onStart {
                    _executiveMasterDetail.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _executiveMasterDetail.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _executiveMasterDetail.postValue(Resource.Success(it.data.executive_master))
                    } else {
                        _executiveMasterDetail.postValue(Resource.Error(it.message))
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