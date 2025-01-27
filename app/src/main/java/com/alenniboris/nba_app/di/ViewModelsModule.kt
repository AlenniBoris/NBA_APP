package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.presentation.activity.MainActivityVM
import com.alenniboris.nba_app.presentation.screens.details.game.GameDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import com.alenniboris.nba_app.presentation.screens.followed.FollowedScreenVM
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
            authenticationManager = get<IAuthenticationManager>(),
            nbaApiManager = get<INbaApiManager>()
        )
    }

    viewModel<FollowedScreenVM> {
        FollowedScreenVM(
            nbaApiManager = get<INbaApiManager>()
        )
    }

    viewModel<GameDetailsScreenVM> { (game: GameModelDomain, isReloadingDataNeeded: Boolean) ->
        GameDetailsScreenVM(
            nbaApiManager = get<INbaApiManager>(),
            game = game,
            isReloadingDataNeeded = isReloadingDataNeeded
        )
    }
}