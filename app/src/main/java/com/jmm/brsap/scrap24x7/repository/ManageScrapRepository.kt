package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ManageScrapRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun getScrapCategory(): Flow<List<Category>> {
        return flow {
            val response = apiService.getScrapCategoryList()

            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun addNewScrapCategory(userId:Int,category: Category): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewCategory(userId,category)

            emit(response)
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getAllScrapCategoryItemList(): Flow<List<CategoryItem>> {
        return flow {
            val response = apiService.getAllScrapCategoryItemList()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getScrapCategoryItems(categoryId:Int): Flow<List<CategoryItem>> {
        return flow {
            val response = apiService.getScrapCategoryItems(categoryId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

// Category
    suspend fun getCategories(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCategories()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addNewCategory(category: Category): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewCategory(category)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategoryById(categoryId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCategoryById(categoryId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateCategory(category: Category): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateCategory(category)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteCategory(categoryId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteCategory(categoryId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }



//Category Item

    suspend fun getCategoryItemList(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCategoryItems()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addNewCategoryItem(categoryItem: CategoryItem): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewCategoryItem(categoryItem)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategoryItemById(categoryItemId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getCategoryItemById(categoryItemId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateCategoryItem(categoryItemId: Int,categoryItem: CategoryItem): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateCategoryItem(categoryItemId,categoryItem)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteCategoryItem(categoryItemId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteCategoryItem(categoryItemId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

// Units
    suspend fun getUnits(): Flow<ApiResponse> {
        return flow {
            val response = apiService.getUnits()

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addNewUnit(unit: UnitModel): Flow<ApiResponse> {
        return flow {
            val response = apiService.addNewUnit(unit)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUnitById(unitId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.getUnitById(unitId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun updateUnit(unitId: Int,unit: UnitModel): Flow<ApiResponse> {
        return flow {
            val response = apiService.updateUnit(unitId,unit)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteUnit(unitId: Int): Flow<ApiResponse> {
        return flow {
            val response = apiService.deleteUnit(unitId)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}