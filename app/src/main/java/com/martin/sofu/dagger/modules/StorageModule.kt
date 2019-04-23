package com.martin.sofu.dagger.modules

import com.martin.sofu.storage.AppSharedPreferences
import com.martin.sofu.storage.impl.SharedPreferencesImpl
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideAppSharedPreferences(impl: SharedPreferencesImpl): AppSharedPreferences = impl
}