package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.manager.impl.NbaApiManagerImpl
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import org.koin.dsl.module

val ManagerModule = module {

    single<INbaApiManager> {
        NbaApiManagerImpl(
            nbaApiGamesNetworkRepository = get<INbaApiGamesNetworkRepository>(),
            nbaApiTeamsNetworkRepository = get<INbaApiTeamsNetworkRepository>(),
            nbaApiPlayersNetworkRepository = get<INbaApiPlayersNetworkRepository>(),
            nbaApiCountriesNetworkRepository = get<INbaApiCountriesNetworkRepository>(),
            nbaApiLeaguesNetworkRepository = get<INbaApiLeaguesNetworkRepository>(),
            nbaApiSeasonsNetworkRepository = get<INbaApiSeasonsNetworkRepository>(),
            nbaApiGamesDatabaseRepository = get<INbaApiGamesDatabaseRepository>(),
            nbaApiTeamsDatabaseRepository = get<INbaApiTeamsDatabaseRepository>(),
            nbaApiPlayersDatabaseRepository = get<INbaApiPlayersDatabaseRepository>(),
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}