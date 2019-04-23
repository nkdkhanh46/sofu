package com.martin.sofu.dagger

import com.martin.sofu.base.BaseActivity
import com.martin.sofu.dagger.modules.*
import com.martin.sofu.features.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RepositoryModule::class,
    ViewModelModule::class, NetworkModule::class, StorageModule::class])
@Singleton
interface AppComponent {
    fun inject(target: BaseActivity)
    fun inject(target: MainActivity)
}