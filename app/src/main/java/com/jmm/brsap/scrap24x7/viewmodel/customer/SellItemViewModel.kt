package com.jmm.brsap.scrap24x7.viewmodel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.repository.ManageScrapRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellItemViewModel @Inject constructor(
        private val manageScrapRepository: ManageScrapRepository
) : ViewModel() {

    private val _categoryList = MutableLiveData<Resource<List<Category>>>()
    val categoryList: LiveData<Resource<List<Category>>> = _categoryList


    fun getScrapCategories() {

        viewModelScope.launch {

            manageScrapRepository
                    .getScrapCategory()
                    .onStart {
                        _categoryList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _categoryList.postValue(Resource.Error(it))
                        }
                    }
                    .collect {
                        _categoryList.postValue(Resource.Success(it))
                    }

        }

    }

    private val _categoryItemList = MutableLiveData<Resource<List<CategoryItem>>>()
    val categoryItemList: LiveData<Resource<List<CategoryItem>>> = _categoryItemList

    fun getCategoriesItem(categoryId:Int) {

        viewModelScope.launch {

            manageScrapRepository
                    .getScrapCategoryItems(categoryId)
                    .onStart {
                        _categoryItemList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _categoryItemList.postValue(Resource.Error(it))
                        }
                    }

                    .collect {
                        _categoryItemList.postValue(Resource.Success(it))
                    }
        }

    }


    private val _allCategoryItemList = MutableLiveData<Resource<List<CategoryItem>>>()
    val allCategoryItemList: LiveData<Resource<List<CategoryItem>>> = _allCategoryItemList

    fun getAllCategoriesItem() {

        viewModelScope.launch {

            manageScrapRepository
                    .getAllScrapCategoryItemList()
                    .onStart {
                        _allCategoryItemList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            _allCategoryItemList.postValue(Resource.Error(it))
                        }
                    }

                    .collect {
                        _allCategoryItemList.postValue(Resource.Success(it))
                    }
        }

    }



}