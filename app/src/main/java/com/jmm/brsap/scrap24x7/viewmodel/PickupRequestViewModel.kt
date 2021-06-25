package com.jmm.brsap.scrap24x7.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.repository.ManageScrapRepository
import com.jmm.brsap.scrap24x7.repository.PickupRequestRepository
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickupRequestViewModel @Inject constructor(
    private val pickupRequestRepository: PickupRequestRepository,
    private val manageScrapRepository: ManageScrapRepository,
    private val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()

    private val _activeStep = MutableLiveData<Int>()
    val activeStep: LiveData<Int> = _activeStep

    fun setActiveStep(step:Int){
        _activeStep.postValue(step)
    }

    private val _selectedCategories = MutableLiveData<Resource<List<Category>>>()
    val selectedCategories: LiveData<Resource<List<Category>>> = _selectedCategories

    fun getSelectedCategories() {

        viewModelScope.launch {

            pickupRequestRepository
                .getSelectedCategories()
                .onStart {
                    _selectedCategories.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _selectedCategories.postValue(Resource.Error(it))
                    }
                }

                .collect {
                    _selectedCategories.postValue(Resource.Success(it))
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

    private val _selectedScrapItems = MutableLiveData<Resource<List<CategoryItem>>>()
    val selectedItems: LiveData<Resource<List<CategoryItem>>> = _selectedScrapItems

    fun getSelectedScrapItems() {

        viewModelScope.launch {

            pickupRequestRepository
                .getSelectedScrapItems()
                .onStart {
                    _selectedScrapItems.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _selectedScrapItems.postValue(Resource.Error(it))
                    }
                }

                .collect {
                    _selectedScrapItems.postValue(Resource.Success(it))
                }
        }

    }

    fun addSelectedScrapItem(item: CategoryItem){
        pickupRequestRepository.addSelectedScrapItem(item)
    }

    fun removeSelectedScrapItem(item: CategoryItem){
        pickupRequestRepository.removeSelectedScrapItem(item)
    }


    private val _selectedPickupDate = MutableLiveData<Long>()
    val selectedPickupDate: LiveData<Long> = _selectedPickupDate

fun getSelectedPickupDate() = selectedPickupDate.value

    fun setSelectedPickupDate(time:Long){
        _selectedPickupDate.postValue(time)
//        pickupRequestRepository.setSelectedPickupDate(time)
    }

    fun getSelectedPickupAddress() = pickupRequestRepository.getSelectedPickupAddress()


    private val _raisedPickupRequest = MutableLiveData<Resource<PickupRequest>>()
    val raisedPickupRequest: LiveData<Resource<PickupRequest>> = _raisedPickupRequest

    fun raiseNewPickupRequest(pickupRequest: PickupRequest) {

        viewModelScope.launch {
            pickupRequestRepository
                .raisePickupRequest(pickupRequest)
                .onStart {
                    _raisedPickupRequest.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    Log.e("ERROR",exception.toString())
                    exception.message?.let {
                        _raisedPickupRequest.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }

                .collect {
                    if (it.data != null) {
                        _raisedPickupRequest.postValue(Resource.Success(it.data.pickup_request))
                    } else {
                        _raisedPickupRequest.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

}