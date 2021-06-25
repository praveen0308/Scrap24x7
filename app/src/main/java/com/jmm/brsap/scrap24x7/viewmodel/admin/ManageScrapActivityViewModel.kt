package com.jmm.brsap.scrap24x7.viewmodel.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.model.network_models.Category
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem
import com.jmm.brsap.scrap24x7.model.network_models.UnitModel
import com.jmm.brsap.scrap24x7.repository.ManageScrapRepository
import com.jmm.brsap.scrap24x7.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageScrapActivityViewModel @Inject constructor(
        private val manageScrapRepository: ManageScrapRepository
) : ViewModel() {

    /********************************* Category **********************************/


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
                        Log.e("ERROR", exception.toString())
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

    fun addNewCategory(category: Category) {

        viewModelScope.launch {

            manageScrapRepository
                .addNewCategory(category)
                .onStart {
                    _addedCategory.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
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

    fun getCategoryById(categoryId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .getCategoryById(categoryId)
                .onStart {
                    _category.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
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

    private val _updatedCategory = MutableLiveData<Resource<Category>>()
    val updatedCategory: LiveData<Resource<Category>> = _updatedCategory

    fun updateCategory(category: Category) {

        viewModelScope.launch {

            manageScrapRepository
                .updateCategory(category)
                .onStart {
                    _updatedCategory.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _updatedCategory.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _updatedCategory.postValue(Resource.Success(it.data.category))
                    } else {
                        _updatedCategory.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _deletedCategory = MutableLiveData<Resource<Category>>()
    val deletedCategory: LiveData<Resource<Category>> = _deletedCategory

    fun deleteCategory(categoryId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .deleteCategory(categoryId)
                .onStart {
                    _deletedCategory.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _deletedCategory.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedCategory.postValue(Resource.Success(it.data.category))
                    } else {
                        _deletedCategory.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    /********************************* Category Item **********************************/
    private val _categoryItems = MutableLiveData<Resource<List<CategoryItem>>>()
    val categoryItems: LiveData<Resource<List<CategoryItem>>> = _categoryItems


    fun getCategoryItems() {
        viewModelScope.launch {
            manageScrapRepository
                .getCategoryItemList()
                .onStart {
                    _categoryItems.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _categoryItems.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _categoryItems.postValue(Resource.Success(it.data.category_items))
                    } else {
                        _categoryItems.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _addedCategoryItem = MutableLiveData<Resource<CategoryItem>>()
    val addedCategoryItem: LiveData<Resource<CategoryItem>> = _addedCategoryItem

    fun addNewCategoryItem(categoryItem: CategoryItem) {

        viewModelScope.launch {

            manageScrapRepository
                .addNewCategoryItem(categoryItem)
                .onStart {
                    _addedCategoryItem.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _addedCategoryItem.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _addedCategoryItem.postValue(Resource.Success(it.data.category_item))
                    } else {
                        _addedCategoryItem.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _categoryItem = MutableLiveData<Resource<CategoryItem>>()
    val categoryItem: LiveData<Resource<CategoryItem>> = _categoryItem

    fun getCategoryItemById(categoryItemId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .getCategoryItemById(categoryItemId)
                .onStart {
                    _categoryItem.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _categoryItem.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _categoryItem.postValue(Resource.Success(it.data.category_item))
                    } else {
                        _categoryItem.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _updatedCategoryItem = MutableLiveData<Resource<CategoryItem>>()
    val updatedCategoryItem: LiveData<Resource<CategoryItem>> = _updatedCategoryItem

    fun updateCategoryItem(categoryItemId: Int,categoryItem: CategoryItem) {

        viewModelScope.launch {

            manageScrapRepository
                .updateCategoryItem(categoryItemId,categoryItem)
                .onStart {
                    _updatedCategoryItem.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _updatedCategoryItem.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _updatedCategoryItem.postValue(Resource.Success(it.data.category_item))
                    } else {
                        _updatedCategoryItem.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _deletedCategoryItem = MutableLiveData<Resource<CategoryItem>>()
    val deletedCategoryItem: LiveData<Resource<CategoryItem>> = _deletedCategoryItem

    fun deleteCategoryItem(categoryItemId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .deleteCategoryItem(categoryItemId)
                .onStart {
                    _deletedCategoryItem.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _deletedCategoryItem.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedCategoryItem.postValue(Resource.Success(it.data.category_item))
                    } else {
                        _deletedCategoryItem.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    /********************************* Unit *****************************************/

    private val _units = MutableLiveData<Resource<List<UnitModel>>>()
    val units: LiveData<Resource<List<UnitModel>>> = _units


    fun getUnits() {

        viewModelScope.launch {

            manageScrapRepository
                .getUnits()
                .onStart {
                    _units.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _units.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _units.postValue(Resource.Success(it.data.units))
                    } else {
                        _units.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _addedUnit = MutableLiveData<Resource<UnitModel>>()
    val addedUnit: LiveData<Resource<UnitModel>> = _addedUnit

    fun addNewUnit(unit: UnitModel) {

        viewModelScope.launch {

            manageScrapRepository
                .addNewUnit(unit)
                .onStart {
                    _addedUnit.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _addedUnit.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _addedUnit.postValue(Resource.Success(it.data.unit))
                    } else {
                        _addedUnit.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


    private val _unit = MutableLiveData<Resource<UnitModel>>()
    val unit: LiveData<Resource<UnitModel>> = _unit

    fun getUnitById(unitId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .getUnitById(unitId)
                .onStart {
                    _unit.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _unit.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _unit.postValue(Resource.Success(it.data.unit))
                    } else {
                        _unit.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _updatedUnit = MutableLiveData<Resource<UnitModel>>()
    val updatedUnit: LiveData<Resource<UnitModel>> = _updatedUnit

    fun updateUnit(unitId: Int,unit: UnitModel) {

        viewModelScope.launch {

            manageScrapRepository
                .updateUnit(unitId,unit)
                .onStart {
                    _updatedUnit.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _updatedUnit.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _updatedUnit.postValue(Resource.Success(it.data.unit))
                    } else {
                        _updatedUnit.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

    private val _deletedUnit = MutableLiveData<Resource<UnitModel>>()
    val deletedUnit: LiveData<Resource<UnitModel>> = _deletedUnit

    fun deleteUnit(unitId: Int) {

        viewModelScope.launch {

            manageScrapRepository
                .deleteUnit(unitId)
                .onStart {
                    _deletedUnit.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR", exception.toString())
                        _deletedUnit.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _deletedUnit.postValue(Resource.Success(it.data.unit))
                    } else {
                        _deletedUnit.postValue(Resource.Error(it.message))
                    }
                }
        }

    }


}