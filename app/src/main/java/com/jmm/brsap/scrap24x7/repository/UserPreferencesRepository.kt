package com.jmm.brsap.scrap24x7.repository

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*

import androidx.datastore.preferences.preferencesDataStore
import com.jmm.brsap.scrap24x7.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/*

Author : Praveen A. Yadav
Created On : 05:18 27-05-2021

*/

class UserPreferencesRepository @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

    val welcomeStatus: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            val pref = preferences[WELCOME_STATUS]?: NEW_USER
            pref
        }

    val userId: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            val pref = preferences[USER_ID]?: 0
            pref
        }


    val loginUserName: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            val pref = preferences[LOGIN_USER_NAME]?:""
            pref
        }


    val userName: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            val pref = preferences[USER_NAME]?:""
            pref
        }

    val firstName: Flow<String> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                // No type safety.
                val pref = preferences[USER_FIRST_NAME]?:""
                pref
            }

    val lastName: Flow<String> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                // No type safety.
                val pref = preferences[USER_LAST_NAME]?:""
                pref
            }


    val userRoleId: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // No type safety.
            val pref = preferences[USER_ROLE_ID]?:0
            pref
        }

    suspend fun updateWelcomeStatus(status: Int) {
        context.dataStore.edit { preference ->
            preference[WELCOME_STATUS] = status
        }
    }

    suspend fun updateLoginUserName(loginUserName: String) {
        context.dataStore.edit { preference ->
            preference[LOGIN_USER_NAME] = loginUserName
        }
    }

    suspend fun updateUserId(userId: Int) {
        context.dataStore.edit { preference ->
            preference[USER_ID] = userId
        }
    }

    suspend fun updateUserName(userName: String) {
        context.dataStore.edit { preference ->
            preference[USER_NAME] = userName
        }
    }

    suspend fun updateUserFirstName(userName: String) {
        context.dataStore.edit { preference ->
            preference[USER_FIRST_NAME] = userName
        }
    }

    suspend fun updateUserLastName(userName: String) {
        context.dataStore.edit { preference ->
            preference[USER_LAST_NAME] = userName
        }
    }

    suspend fun updateUserRoleId(userRoleId: Int) {
        context.dataStore.edit { preference ->
            preference[USER_ROLE_ID] = userRoleId
        }
    }

    suspend fun clearUserInfo(){

//                context.dataStore.edit {
                    updateUserId(0)
                    updateLoginUserName("")
                    updateUserName("")
                    updateUserRoleId(0)
                    updateUserFirstName("")
                    updateUserLastName("")

//                    it.remove(USER_ID)
//                    it.remove(LOGIN_USER_NAME)
//                    it.remove(USER_NAME)
//                    it.remove(USER_ROLE_ID)
//                    it.remove(USER_FIRST_NAME)
//                    it.remove(USER_LAST_NAME)
                    updateWelcomeStatus(1)

                    kotlinx.coroutines.delay(1000)




    }

//        suspend fun clearUserInfo() :Flow<Boolean>{
//            return flow {
//                try {
//                    context.dataStore.edit {
//                        updateUserId(0)
//                        updateLoginUserName("")
//                        updateUserName("")
//                        updateUserRoleId(0)
//                        updateUserFirstName("")
//                        updateUserLastName("")
//
////                    it.remove(USER_ID)
////                    it.remove(LOGIN_USER_NAME)
////                    it.remove(USER_NAME)
////                    it.remove(USER_ROLE_ID)
////                    it.remove(USER_FIRST_NAME)
////                    it.remove(USER_LAST_NAME)
//                        updateWelcomeStatus(1)
//                        kotlinx.coroutines.delay(1000)
//                    }
//                }finally {
//                    emit(true)
//                }
//            }
//
//    }

    suspend fun storeUserLoginInfo(userModel: UserModel) {

        context.dataStore.edit { preference ->
            preference[LOGIN_USER_NAME] = userModel.LoginID!!
            preference[USER_ID] = userModel.UserID!!
            preference[USER_NAME] = userModel.UserName!!
            preference[USER_ROLE_ID] = userModel.UserRoleID!!

        }
    }



    companion object{
        const val USER_PREFERENCES_NAME = "Scrap24x7"

        const val NEW_USER = 0
        const val ON_BOARDING_DONE = 1
        const val LOGIN_DONE = 2

//        val IS_ON_BOARDING_DONE= booleanPreferencesKey("is_on_boarding_done")

        val WELCOME_STATUS = intPreferencesKey("welcome_status")

        val USER_ID = intPreferencesKey("user_id")
        val LOGIN_USER_NAME = stringPreferencesKey("login_user_name")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_ROLE_ID = intPreferencesKey("user_role_id")
        val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
        val USER_LAST_NAME = stringPreferencesKey("user_last_name")
    }
}


