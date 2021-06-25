package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getLocations(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getLocationMasters()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addNewLocation(locationMaster: LocationMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewLocationMaster(locationMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLocationById(locationId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getLocationMasterById(locationId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateLocation(locationMaster: LocationMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateLocationMaster(locationMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteLocation(locationId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteLocationMaster(locationId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }



}