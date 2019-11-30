package com.martin.sofu.injection

import android.preference.PreferenceManager
import com.martin.sofu.storage.AppSharedPreferences
import com.martin.sofu.storage.impl.SharedPreferencesImpl
import org.koin.dsl.module.module

val storageModule = module {
    single<AppSharedPreferences> { SharedPreferencesImpl(get()) }
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
}