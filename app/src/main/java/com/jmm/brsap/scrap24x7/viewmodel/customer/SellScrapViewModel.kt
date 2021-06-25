package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.repository.CustomerAddressRepository
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
class SellScrapViewModel @Inject constructor(
        private val manageScrapRepository: ManageScrapRepository,
        private val pickupRequestRepository: PickupRequestRepository,
        private val customerAddressRepository: CustomerAddressRepository,
        private val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()

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
                            Log.e("ERROR", exception.toString())
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

    private val _customerAddressList = MutableLiveData<Resource<List<CustomerAddress>>>()
    val customerAddressList: LiveData<Resource<List<CustomerAddress>>> = _customerAddressList

    fun getCustomerAddressList(customerId: Int) {
        viewModelScope.launch {
            customerAddressRepository
                    .getAddressByCustomerId(customerId)
                    .onStart {
                        _customerAddressList.postValue(Resource.Loading(true))
                    }
                    .catch { exception ->
                        exception.message?.let {
                            Log.e("ERROR", exception.toString())
                            _customerAddressList.postValue(Resource.Error("Something went wrong !!!"))
                        }
                    }
                    .collect {
                        if (it.data != null) {
                            _customerAddressList.postValue(Resource.Success(it.data.customer_addresses))
                        } else {
                            _customerAddressList.postValue(Resource.Error(it.message))
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
                        Log.e("ERROR", exception.toString())
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


    fun setSelectedCategories(categoryList: MutableList<Category>) {
        pickupRequestRepository.setSelectedCategories(categoryList)
    }

    fun setSelectedPickupAddress(customerAddress: CustomerAddress){
        pickupRequestRepository.setSelectedPickupAddress(customerAddress)
    }
}