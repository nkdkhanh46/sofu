package com.martin.sofu.dagger.modules

import com.martin.sofu.networking.RetrofitClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitClient() = RetrofitClient()
}