package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.repository.ManageScrapRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCustomerViewModel @Inject constructor(
        private val manageScrapRepository: ManageScrapRepository,
        private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    private val _categoryList = MutableLiveData<Resource<List<Category>>>()
    val categoryList: LiveData<Resource<List<Category>>> = _categoryList
    fun getCategories() {

        viewModelScope.launch {

            manageScrapRepository
                    .getCategories()
                    .onStart {
                        _categoryList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            Log.e("ERROR",exception.toString())
                            _categoryList.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }
                    .collect {
                        if (it.data != null) {
                            _categoryList.postValue(Resource.Success(it.data.categories))
                        } else {
                            _categoryList.postValue(Resource.Error(it.message))
                        }
                    }

        }

    }



    private val _categoryItemList = MutableLiveData<Resource<List<CategoryItem>>>()
    val categoryItemList: LiveData<Resource<List<CategoryItem>>> = _categoryItemList

    fun getCategoriesItem() {

        viewModelScope.launch {

            manageScrapRepository
                    .getCategoryItemList()
                    .onStart {
                        _categoryItemList.postValue(Resource.Loading(true))

                    }
                    .catch { exception ->
                        Log.e("ERROR",exception.toString())
                        exception.message?.let {
                            _categoryItemList.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }

                    .collect {
                        if (it.data != null) {
                            _categoryItemList.postValue(Resource.Success(it.data.category_items))
                        } else {
                            _categoryItemList.postValue(Resource.Error(it.message))
                        }
                    }
        }

    }
}