package com.oma.beyondpayment.myapp

import android.app.Application
import com.google.android.datatransport.BuildConfig
import timber.log.Timber

class CameraXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}