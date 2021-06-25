package com.jmm.brsap.scrap24x7

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
//import com.google.firebase.FirebaseApp
//import com.google.firebase.FirebaseOptions
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        val options = FirebaseOptions.Builder()
            .setApplicationId("1:399091812981:android:f02a5ac8b67b7938da063c") // Required for Analytics.
            .setProjectId("scrap24x7-6fcb8") // Required for Firebase Installations.
            .setApiKey("AIzaSyDCJsYVBuD2d049yRZKfr7Jm67Bfl1ZDZs") // Required for Auth.
            .build()
        FirebaseApp.initializeApp(this, options, "Scrap24x7")
    }
}