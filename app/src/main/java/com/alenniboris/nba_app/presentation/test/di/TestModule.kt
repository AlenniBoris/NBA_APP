package com.alenniboris.nba_app.presentation.test.di

import com.alenniboris.nba_app.presentation.test.presentation.TestVM
import com.alenniboris.nba_app.presentation.test.domain.TestManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {

    single<TestManager> { TestManager() }

    viewModel<TestVM> { TestVM(testManager = get<TestManager>()) }

}