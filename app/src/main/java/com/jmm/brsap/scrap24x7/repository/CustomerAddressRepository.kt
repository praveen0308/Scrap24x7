package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CustomerAddressRepository @Inject constructor(
        private val apiService: ApiService
){
    suspend fun addNewCustomerAddress(customerAddress: CustomerAddress): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewCustomerAddress(customerAddress)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAddressByCustomerId(customerId:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getAddressByCustomerId(customerId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getAddressTypes(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getAddressType()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteCustomerAddress(customerAddressId:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteCustomerAddress(customerAddressId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


}