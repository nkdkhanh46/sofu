package com.martin.sofu.dagger.modules

import com.khanh.martinsamples.repositories.SampleRepository
import com.khanh.martinsamples.repositories.impl.SampleRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideSampleRepository(impl: SampleRepositoryImpl): SampleRepository = impl
}