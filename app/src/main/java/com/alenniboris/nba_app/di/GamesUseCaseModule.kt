package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGameDataAndStatisticsByIdUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesByDateUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.impl.game.GetFollowedGamesUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.game.GetGameDataAndStatisticsByIdUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.game.GetGamesByDateUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.game.GetGamesBySeasonAndLeagueUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.game.UpdateGameIsFollowedUseCaseImpl
import org.koin.dsl.module

val GamesUseCaseModule = module {
    single<IGetFollowedGamesUseCase> {
        GetFollowedGamesUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiGamesDatabaseRepository = get<INbaApiGamesDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IGetGamesByDateUseCase> {
        GetGamesByDateUseCaseImpl(
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            nbaApiGamesNetworkRepository = get<INbaApiGamesNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IGetGamesBySeasonAndLeagueUseCase> {
        GetGamesBySeasonAndLeagueUseCaseImpl(
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            nbaApiGamesNetworkRepository = get<INbaApiGamesNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IUpdateGameIsFollowedUseCase> {
        UpdateGameIsFollowedUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiGamesDatabaseRepository = get<INbaApiGamesDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IGetGameDataAndStatisticsByIdUseCase> {
        GetGameDataAndStatisticsByIdUseCaseImpl(
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            nbaApiGamesNetworkRepository = get<INbaApiGamesNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}