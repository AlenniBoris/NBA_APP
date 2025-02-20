package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.impl.team.GetFollowedTeamsUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamStatisticsInGameUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamStatisticsUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamsByCountryUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamsByQuerySeasonLeagueCountryUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamsByQuerySeasonLeagueUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamsByQueryUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.GetTeamsBySeasonAndLeagueUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.ReloadDataForTeamAndLoadLeaguesUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.team.UpdateTeamIsFollowedUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsInGameUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IReloadDataForTeamAndLoadLeaguesUseCase
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
import org.koin.dsl.module

val TeamsUseCaseModule = module {

    single<IGetFollowedTeamsUseCase> {
        GetFollowedTeamsUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiTeamsDatabaseRepository = get<INbaApiTeamsDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamsByCountryUseCase> {
        GetTeamsByCountryUseCaseImpl(
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamsByQueryUseCase> {
        GetTeamsByQueryUseCaseImpl(
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamsBySeasonAndLeagueUseCase> {
        GetTeamsBySeasonAndLeagueUseCaseImpl(
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamsByQuerySeasonLeagueUseCase> {
        GetTeamsByQuerySeasonLeagueUseCaseImpl(
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamsByQuerySeasonLeagueCountryUseCase> {
        GetTeamsByQuerySeasonLeagueCountryUseCaseImpl(
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IUpdateTeamIsFollowedUseCase> {
        UpdateTeamIsFollowedUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            nbaApiTeamsDatabaseRepository = get<INbaApiTeamsDatabaseRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IReloadDataForTeamAndLoadLeaguesUseCase> {
        ReloadDataForTeamAndLoadLeaguesUseCaseImpl(
            dispatchers = get<IAppDispatchers>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            nbaApiLeaguesNetworkRepository = get<INbaApiLeaguesNetworkRepository>(),
            getFollowedTeamsUseCaseImpl = get<IGetFollowedTeamsUseCase>()
        )
    }

    factory<IGetTeamStatisticsInGameUseCase> {
        GetTeamStatisticsInGameUseCaseImpl(
            nbaApiGamesNetworkRepository = get<INbaApiGamesNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetTeamStatisticsUseCase> {
        GetTeamStatisticsUseCaseImpl(
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}