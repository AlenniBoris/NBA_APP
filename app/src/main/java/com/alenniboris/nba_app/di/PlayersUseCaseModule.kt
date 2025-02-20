package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.impl.player.GetFollowedPlayersUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayerDataByIdUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayerStatisticsInSeasonUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayersByQuerySeasonTeamUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayersByQueryUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayersBySeasonTeamUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.GetPlayersForTeamInSeasonUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.player.UpdatePlayerIsFollowedUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerDataByIdUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerStatisticsInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQuerySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersBySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersForTeamInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import org.koin.dsl.module

val PlayersUseCaseModule = module {

    single<IGetFollowedPlayersUseCase> {
        GetFollowedPlayersUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiPlayersDatabaseRepository = get<INbaApiPlayersDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayersByQueryUseCase> {
        GetPlayersByQueryUseCaseImpl(
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayersBySeasonTeamUseCase> {
        GetPlayersBySeasonTeamUseCaseImpl(
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayersByQuerySeasonTeamUseCase> {
        GetPlayersByQuerySeasonTeamUseCaseImpl(
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IUpdatePlayerIsFollowedUseCase> {
        UpdatePlayerIsFollowedUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiPlayersDatabaseRepository = get<INbaApiPlayersDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayerStatisticsInSeasonUseCase> {
        GetPlayerStatisticsInSeasonUseCaseImpl(
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayerDataByIdUseCase> {
        GetPlayerDataByIdUseCaseImpl(
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetPlayersForTeamInSeasonUseCase> {
        GetPlayersForTeamInSeasonUseCaseImpl(
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}