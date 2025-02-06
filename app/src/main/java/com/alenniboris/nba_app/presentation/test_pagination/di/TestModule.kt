package com.alenniboris.nba_app.presentation.test_pagination.di

import com.alenniboris.nba_app.presentation.test_pagination.domain.TestManager
import com.alenniboris.nba_app.presentation.test_pagination.presentation.TestPaginationVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testPaginationModule = module {

    single<TestManager> { TestManager() }

    viewModel<TestPaginationVM> { TestPaginationVM(testManager = get<TestManager>()) }

}