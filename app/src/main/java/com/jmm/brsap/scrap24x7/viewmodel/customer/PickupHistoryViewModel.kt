package com.jmm.brsap.scrap24x7.viewmodel.customer

import android.util.Log
import androidx.lifecycle.*
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
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
class PickupHistoryViewModel @Inject constructor(
    private val pickupRequestRepository: PickupRequestRepository,
    private val userPreferencesRepository: UserPreferencesRepository
):ViewModel(){

    val userId = userPreferencesRepository.userId.asLiveData()

    private val _pickupHistoryByCID = MutableLiveData<Resource<List<PickupRequest>>>()
    val pickupHistoryByCID: LiveData<Resource<List<PickupRequest>>> = _pickupHistoryByCID


    fun getPickupHistoryByCustomerId(customerId:Int) {

        viewModelScope.launch {

            pickupRequestRepository
                .getPickupRequestByCustomerId(customerId)
                .onStart {
                    _pickupHistoryByCID.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        Log.e("ERROR",exception.toString())
                        _pickupHistoryByCID.postValue(Resource.Error("Something went wrong !!!"))
                    }
                }
                .collect {
                    if (it.data != null) {
                        _pickupHistoryByCID.postValue(Resource.Success(it.data.pickup_requests))
                    } else {
                        _pickupHistoryByCID.postValue(Resource.Error(it.message))
                    }
                }
        }

    }

}