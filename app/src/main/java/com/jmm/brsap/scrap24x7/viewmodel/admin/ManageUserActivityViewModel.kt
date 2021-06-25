package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.pagingDataSource.CustomerDataSource
import com.jmm.brsap.scrap24x7.repository.CustomerRepository
import com.jmm.brsap.scrap24x7.repository.DriverRepository
import com.jmm.brsap.scrap24x7.repository.ExecutiveRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 09:32 03-06-2021

*/

@HiltViewModel
class ManageUserActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val executiveRepository: ExecutiveRepository,
    private val driverRepository: DriverRepository
):ViewModel() {
//
//    val customers = Pager(PagingConfig(pageSize = 15)) {
//        CustomerDataSource(apiService)
//    }.flow.cachedIn(viewModelScope)
    
    val customers =customerRepository.getCustomersList().cachedIn(viewModelScope)
    val executiveMasters =executiveRepository.getExecutivesList().cachedIn(viewModelScope)
    val driverMasters =driverRepository.getDriversList().cachedIn(viewModelScope)

    private val _customer = MutableLiveData<Resource<Customer>>()
    val customer: LiveData<Resource<Customer>> = _customer

    fun getCustomerDetailById(customerId: Int) {
        viewModelScope.launch {
            customerRepository
                .getCustomerDetailById(customerId)
                .onStart {
                    _customer.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _customer.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _customer.postValue(Resource.Success(it.data.customer))
                    } else {
                        _customer.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _executiveDetail = MutableLiveData<Resource<ExecutiveMaster>>()
    val executiveDetail: LiveData<Resource<ExecutiveMaster>> = _executiveDetail

    fun getExecutiveDetailById(executiveId: Int) {
        viewModelScope.launch {
            executiveRepository
                .getExecutiveById(executiveId)
                .onStart {
                    _executiveDetail.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _executiveDetail.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _executiveDetail.postValue(Resource.Success(it.data.executive_master))
                    } else {
                        _executiveDetail.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _driver = MutableLiveData<Resource<DriverMaster>>()
    val driver: LiveData<Resource<DriverMaster>> = _driver

    fun getDriverDetailById(driverId: Int) {
        viewModelScope.launch {
            driverRepository
                .getDriverById(driverId)
                .onStart {
                    _driver.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _driver.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _driver.postValue(Resource.Success(it.data.driver_master))
                    } else {
                        _driver.postValue(Resource.Error(it.message))
                    }
                }
        }

    }
}