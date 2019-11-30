package com.martin.sofu.injection

import com.martin.sofu.features.home.MainViewModel
import com.martin.sofu.features.reputationhistory.ReputationViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ReputationViewModel(get(), get()) }
}