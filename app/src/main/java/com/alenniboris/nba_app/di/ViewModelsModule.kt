package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.presentation.activity.MainActivityVM
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModels = module {
    viewModel<EnterScreenVM> {
        EnterScreenVM(
            authenticationManager = get<IAuthenticationManager>(),
        )
    }

    viewModel<MainActivityVM> {
        MainActivityVM(
            authenticationManager = get<IAuthenticationManager>()
        )
    }

    viewModel<ShowingScreenVM> {
        ShowingScreenVM(
            authenticationManager = get<IAuthenticationManager>()
        )
    }
}