package com.jmm.brsap.scrap24x7.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.pagingDataSource.DriverMasterPagingSource
import com.jmm.brsap.scrap24x7.pagingDataSource.GenericPagingSource
import com.jmm.brsap.scrap24x7.util.PagingEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 12:25 04-06-2021

*/

class DriverRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getDriversList() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { GenericPagingSource(apiService,PagingEnum.DRIVER_MASTER) }
        ).flow

    suspend fun addNewDriver(driverMaster: DriverMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewDriverMaster(driverMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDriverById(driverId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getDriverMasterById(driverId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateDriver(driverId:Int,driverMaster: DriverMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateDriverMaster(driverId,driverMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteDriver(driverId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteDriverMaster(driverId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDriversByLocationId(locationId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getDriverMastersByLocationId(locationId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}