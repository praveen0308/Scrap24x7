package com.jmm.brsap.scrap24x7.pagingDataSource

import androidx.paging.PagingSource
import com.jmm.brsap.scrap24x7.model.network_models.Customer
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster
import com.jmm.brsap.scrap24x7.network.ApiService
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1
class ExecutiveMasterPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ExecutiveMaster>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExecutiveMaster> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

//            val nextPageNumber = params.key ?: 0
            val response = apiService.getExecutiveMasters(params.loadSize,position)
            val customers = response.data.executive_masters
            LoadResult.Page(
                data = customers,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (customers.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}