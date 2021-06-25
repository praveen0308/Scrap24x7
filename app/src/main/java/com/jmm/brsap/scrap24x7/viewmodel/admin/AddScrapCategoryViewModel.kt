package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.repository.ManageScrapRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 07:42 19-06-2021

*/

@HiltViewModel
class AddScrapCategoryViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository,
    private val manageScrapRepository:ManageScrapRepository
    ): ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()

    val selectedScrapCategory = MutableLiveData(5)

    fun setActiveScrapCategory(scrapCategoryId:Int){
        selectedScrapCategory.postValue(scrapCategoryId)
    }

    val selectedScrapItems = MutableLiveData(listOf<CategoryItem>())

    fun setSelectedScrapItemList(mList : List<CategoryItem>){
        selectedScrapItems.postValue(mList)
    }
    //------
    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    fun getCategories() {

        viewModelScope.launch {

            manageScrapRepository
                .getCategories()
                .onStart {
                    _categories.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _categories.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _categories.postValue(Resource.Success(it.data.categories))
                    } else {
                        _categories.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _addedCategory = MutableLiveData<Resource<Category>>()
    val addedCategory: LiveData<Resource<Category>> = _addedCategory

    fun addNewCategory(userId:Int,category: Category) {

        viewModelScope.launch {

            manageScrapRepository
                .addNewScrapCategory(userId,category)
                .onStart {
                    _addedCategory.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _addedCategory.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _addedCategory.postValue(Resource.Success(it.data.category))
                    } else {
                        _addedCategory.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _category = MutableLiveData<Resource<Category>>()
    val category: LiveData<Resource<Category>> = _category

    fun getCategory(categoryId:Int) {

        viewModelScope.launch {

            manageScrapRepository
                .getCategoryById(categoryId)
                .onStart {
                    _category.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _category.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _category.postValue(Resource.Success(it.data.category))
                    } else {
                        _category.postValue(Resource.Error(it.message))
                    }
                }
        }

    }
    private val _scrapItems = MutableLiveData<Resource<List<CategoryItem>>>()
    val scrapItems: LiveData<Resource<List<CategoryItem>>> = _scrapItems

    fun getScrapItems() {

        viewModelScope.launch {

            manageScrapRepository
                .getCategoryItemList()
                .onStart {
                    _scrapItems.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _scrapItems.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _scrapItems.postValue(Resource.Success(it.data.category_items))
                    } else {
                        _scrapItems.postValue(Resource.Error(it.message))
                    }
                }
        }

    }
}