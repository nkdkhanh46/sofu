package com.martin.sofu.application

import android.app.Application
import com.martin.sofu.dagger.AppComponent
import com.martin.sofu.dagger.DaggerAppComponent
import com.martin.sofu.dagger.modules.AppModule

class MainApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    @Suppress("DEPRECATION")
    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}