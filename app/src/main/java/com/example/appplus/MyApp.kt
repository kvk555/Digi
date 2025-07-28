package com.example.appplus

import android.app.Application
import com.example.appplus.di.AppModule
import com.example.appplus.di.AppModuleImpl

class MyApp : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}
