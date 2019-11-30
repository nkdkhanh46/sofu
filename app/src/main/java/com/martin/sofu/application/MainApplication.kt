package com.martin.sofu.application

import android.app.Application
import com.martin.sofu.injection.networkModule
import com.martin.sofu.injection.repositoryModule
import com.martin.sofu.injection.storageModule
import com.martin.sofu.injection.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin(
            this,
            listOf(
                viewModelModule,
                storageModule,
                repositoryModule,
                networkModule
            )
        )
    }
}