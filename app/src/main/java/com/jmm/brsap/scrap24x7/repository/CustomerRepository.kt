package com.jmm.brsap.scrap24x7.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.pagingDataSource.CustomerDataSource
import com.jmm.brsap.scrap24x7.pagingDataSource.GenericPagingSource
import com.jmm.brsap.scrap24x7.util.PagingEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CustomerRepository @Inject constructor(
        private val apiService: ApiService
){
//    suspend fun getCustomerList(itemsPerPage:Int,pageNumber:Int): Flow<ApiResponse> {
//        return flow {
////            val response = apiService.getCustomerList(itemsPerPage, pageNumber)
//            val response = Pager(PagingConfig(pageSize = 10)) {
//                CustomerDataSource(apiService)
//            }.flow.cachedIn(Dispatchers.IO)
//            emit(response)
//        }.flowOn(Dispatchers.IO)
//
//    }

//    fun getCustomersList() =
//        Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { CustomerDataSource(apiService) }
//        ).flow

    fun getCustomersList() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GenericPagingSource(apiService,PagingEnum.CUSTOMER) }
        ).flow

//    suspend fun getCustomerList()=Pager(PagingConfig(pageSize = 10)) {
//        CustomerDataSource(apiService)
//    }.flow.cachedIn(Dispatchers.IO)
//


    suspend fun registerNewCustomer(customer: Customer): Flow<ApiResponse> {
        return flow {
            val response = apiService.registerNewCustomer(customer)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun updateCustomerInfo(customer: Customer): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateCustomerInfo(customer)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }


    suspend fun getCustomerDetailById(customerId:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCustomerById(customerId)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }




}