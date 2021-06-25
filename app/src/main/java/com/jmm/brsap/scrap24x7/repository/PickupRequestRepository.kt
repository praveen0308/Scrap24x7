package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.ApiRequestModel
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PickupRequestRepository @Inject constructor(
    private val apiService: ApiService
)
{
    private val selectedCategories = mutableListOf<Category>()
    private val selectedScrapItems = mutableListOf<CategoryItem>()
    private var selectedPickupDate:Long = 0
    private lateinit var selectedPickupAddress:CustomerAddress

    fun getSelectedCategories():Flow<List<Category>>{
        return flow {
            emit(selectedCategories)
        }
    }


    fun setSelectedCategories(list: MutableList<Category>){
        selectedCategories.clear()
        selectedCategories.addAll(list)
    }

    fun getSelectedScrapItems():Flow<List<CategoryItem>>{
        return flow {
            emit(selectedScrapItems)
        }
    }

    fun addSelectedScrapItem(item: CategoryItem){
        selectedScrapItems.add(item)
    }

    fun removeSelectedScrapItem(item: CategoryItem){
        selectedScrapItems.remove(item)
    }

    fun setSelectedPickupDate(time:Long){
        selectedPickupDate = time
    }

    fun getSelectedPickupDate() = selectedPickupDate
//    fun getSelectedPickupDate():Flow<Long>{
//        return flow {
//            emit(selectedPickupDate)
//        }
//    }

    fun setSelectedPickupAddress(customerAddress: CustomerAddress){
        selectedPickupAddress = customerAddress
    }

    fun getSelectedPickupAddress()=selectedPickupAddress

    fun getSelectedCategoriesItems(categoryId: Int):Flow<List<CategoryItem>>{
        return flow {
            val response = apiService.getScrapCategoryItems(categoryId)
            emit(response)
        }
    }

    fun getSelectedCategoriesItems(): Flow<List<CategoryItem>?> {
        return flow {
            selectedCategories.forEach {
                val response = it.id?.let { it1 -> apiService.getScrapCategoryItems(it1) }
                emit(response)
            }
        }
    }

    suspend fun raisePickupRequest(pickupRequest: PickupRequest): Flow<ApiResponse> {
        return flow {
            val response = apiService.raisePickupRequest(pickupRequest)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPickupRequestByCustomerId(customerId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestByCustomerID(customerId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPickupRequestDetails(pickupId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestById(pickupId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPickupRequestAllDetail(pickupId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestDetailById(pickupId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPickupRequestList(from: String,to:String,locationIds:List<Int>): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestList(
                ApiRequestModel(
                from = from,
                to = to,
                location_ids = locationIds
                )
            )
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPickupRequestAcExecutive(from: String,to:String,executiveId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getPickupRequestAcExecutive(from,to, executiveId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updatePickupStatus(pickupId: Int,status:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.updatePickupStatus(pickupId,status)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun acceptPickupRequest(pickupId: String,driverId:Int,executiveId: Int,vehicleId:Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.acceptPickupRequest(pickupId, driverId, executiveId, vehicleId)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun collectPickupItems(pickupId: String,pickupItems:List<PickupRequestItem>): Flow<ApiResponse> {
        return flow {
            val response = apiService.collectPickupItems(pickupId, pickupItems)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}