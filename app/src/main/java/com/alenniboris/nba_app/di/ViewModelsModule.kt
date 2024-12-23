package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModels = module {
    viewModel<EnterScreenVM> { EnterScreenVM() }
}