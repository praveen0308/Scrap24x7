package com.jmm.brsap.scrap24x7.pagingDataSource

import androidx.paging.PagingSource
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.util.PagingEnum
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


private const val STARTING_PAGE_INDEX = 1
class GenericPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val targetObject : PagingEnum
) : PagingSource<Int, Any>() {

    private lateinit var response:ApiResponse
    private lateinit var dataList:List<Any>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        val position = params.key ?: STARTING_PAGE_INDEX


        return try {
            when(targetObject){
                PagingEnum.CUSTOMER ->{
                    response = apiService.getCustomerList(params.loadSize,position)
                    dataList = response.data.customers
                }
                PagingEnum.DRIVER_MASTER->{
                    response = apiService.getDriverMasters(params.loadSize,position)
                    dataList = response.data.driver_masters
                }
                PagingEnum.EXECUTIVE_MASTER->{
                    response = apiService.getExecutiveMasters(params.loadSize,position)
                    dataList = response.data.executive_masters
                }
                PagingEnum.VEHICLE_MASTER->{
                    response = apiService.getVehicleMasters(params.loadSize,position)
                    dataList = response.data.vehicle_masters
                }
            }

            LoadResult.Page(
                data = dataList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (dataList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}