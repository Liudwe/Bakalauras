package io.unlokk.onboarding

import android.app.Application
import android.util.Log
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "Application started")
    }
}