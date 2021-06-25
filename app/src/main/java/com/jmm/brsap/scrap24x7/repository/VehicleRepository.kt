package com.jmm.brsap.scrap24x7.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.pagingDataSource.CustomerDataSource
import com.jmm.brsap.scrap24x7.pagingDataSource.GenericPagingSource
import com.jmm.brsap.scrap24x7.pagingDataSource.VehicleMasterPagingSource
import com.jmm.brsap.scrap24x7.util.PagingEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val apiService: ApiService
){

//    suspend fun getVehicles(): Flow<ApiResponse> {
//        return flow {
//            val response = apiService.getVehicleMasters()
//
//            emit(response)
//        }.flowOn(Dispatchers.IO)
//    }


//    fun getVehicleList() =
//        Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                maxSize = 50,
//                enablePlaceholders = false,
//                prefetchDistance = 5,
//                initialLoadSize = 10
//            ),
//            pagingSourceFactory = { VehicleMasterPagingSource(apiService) }
//        ).flow

    fun getVehicleList() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { GenericPagingSource(apiService,PagingEnum.VEHICLE_MASTER) }
        ).flow


    suspend fun addNewVehicle(vehicleMaster: VehicleMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewVehicleMaster(vehicleMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVehicleById(vehicleId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getVehicleMasterById(vehicleId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateVehicle(vehicleId:Int,vehicleMaster: VehicleMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateVehicleMaster(vehicleId,vehicleMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteVehicle(vehicleId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteVehicleMaster(vehicleId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getVehiclesByLocationId(locationId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getVehicleMastersByLocationId(locationId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}