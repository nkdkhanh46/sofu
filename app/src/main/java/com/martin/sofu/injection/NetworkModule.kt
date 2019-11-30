package com.martin.sofu.injection

import com.martin.sofu.networking.RetrofitClient
import org.koin.dsl.module.module

val networkModule = module {
    single { RetrofitClient() }
}