package com.jmm.brsap.scrap24x7.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jmm.brsap.scrap24x7.model.network_models.ApiResponse
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.pagingDataSource.ExecutiveMasterPagingSource
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

class ExecutiveRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getExecutivesList() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { GenericPagingSource(apiService,PagingEnum.EXECUTIVE_MASTER) }
        ).flow

    suspend fun addNewExecutive(executiveMaster: ExecutiveMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewExecutiveMaster(executiveMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getExecutiveById(executiveId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getExecutiveMasterById(executiveId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateExecutive(executiveId:Int,executiveMaster: ExecutiveMaster): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateExecutiveMaster(executiveId,executiveMaster)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteExecutive(executiveId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteExecutiveMaster(executiveId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getExecutivesByLocationId(locationId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getExecutiveMastersByLocationId(locationId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}