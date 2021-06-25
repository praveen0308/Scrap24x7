package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
        private val apiService: ApiService
) {

    suspend fun checkStaffLogin(userId:String,password:String): Flow<ApiResponse> {
        return flow {
            val response = apiService.checkStaffLogin(userId,password)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }


    suspend fun getCustomer(userId:String): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCustomerByUserId(userId)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }

}