package io.unlokk.onboarding

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

val appId ="application-0-dvodf" // Enter your own App Id here
lateinit var app: App

@HiltAndroidApp
class LoanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "Application started")
        Realm.init(this)
        app = App(AppConfiguration.Builder(appId).build())
        Log.d("TAG", "Initialized the Realm App configuration for: ${app.configuration.appId}")

    }
}