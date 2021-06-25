package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.Warehouse
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WarehouseRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getWarehouses(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getWarehouses()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addNewWarehouse(warehouse: Warehouse): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewWarehouse(warehouse)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getWarehouseById(warehouseId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getWarehouseById(warehouseId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateWarehouse(warehouseId: Int,warehouse: Warehouse): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateWarehouse(warehouseId,warehouse)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteWarehouse(warehouseId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteWarehouse(warehouseId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}