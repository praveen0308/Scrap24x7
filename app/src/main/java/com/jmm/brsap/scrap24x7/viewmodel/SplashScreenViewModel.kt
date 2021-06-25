package com.jmm.brsap.scrap24x7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 06:01 27-05-2021

*/

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) :ViewModel(){

    val welcomeStatus = userPreferencesRepository.welcomeStatus.asLiveData()
    val userId = userPreferencesRepository.userId.asLiveData()
    val loginUserName = userPreferencesRepository.loginUserName.asLiveData()
    val userName = userPreferencesRepository.userName.asLiveData()
    val userRoleID = userPreferencesRepository.userRoleId.asLiveData()

    fun updateWelcomeStatus(status:Int)=viewModelScope.launch {
        userPreferencesRepository.updateWelcomeStatus(status)
    }



}