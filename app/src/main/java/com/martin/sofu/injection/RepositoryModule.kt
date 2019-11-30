package com.martin.sofu.injection

import com.martin.sofu.repositories.UserRepository
import com.martin.sofu.repositories.impl.UserRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}