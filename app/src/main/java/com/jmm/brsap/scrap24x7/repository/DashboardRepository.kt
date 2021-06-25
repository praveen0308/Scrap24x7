package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.Date
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 07:33 03-06-2021

*/

class DashboardRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getSystemStatistics(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getSystemComponentsStatistics()
            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getPickupRequestsCount(pickupDate:String): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestsCount(Date.valueOf(pickupDate))
            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getExecutivePickupSummary(from:String,to:String,executive_id:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getExecutivePickupSummary(from,to,executive_id)
            emit(response)
        }.flowOn(Dispatchers.IO)

    }

}