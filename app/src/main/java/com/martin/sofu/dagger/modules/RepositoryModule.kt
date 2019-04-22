package com.martin.sofu.dagger.modules

import com.martin.sofu.repositories.UserRepository
import com.martin.sofu.repositories.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}