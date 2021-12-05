package com.arenko.personlist

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PersonApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }
}