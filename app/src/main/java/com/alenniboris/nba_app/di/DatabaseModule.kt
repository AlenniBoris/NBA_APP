package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.database.api.nba.NbaApiGamesDatabaseRepositoryImpl
import com.alenniboris.nba_app.data.repository.database.api.nba.NbaApiPlayersDatabaseRepositoryImpl
import com.alenniboris.nba_app.data.repository.database.api.nba.NbaApiTeamsDatabaseRepositoryImpl
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaGamesDao
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaPlayersDao
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaTeamsDao
import com.alenniboris.nba_app.data.source.local.database.api.nba.NbaApiDatabase
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val DatabaseModule = module {

    single<NbaApiDatabase> {
        NbaApiDatabase.get(
            apl = androidApplication()
        )
    }

    single<INbaApiGamesDatabaseRepository> {
        NbaApiGamesDatabaseRepositoryImpl(
            nbaApiGamesDao = get<INbaGamesDao>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiTeamsDatabaseRepository> {
        NbaApiTeamsDatabaseRepositoryImpl(
            nbaApiTeamsDao = get<INbaTeamsDao>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiPlayersDatabaseRepository> {
        NbaApiPlayersDatabaseRepositoryImpl(
            nbaApiPlayersDao = get<INbaPlayersDao>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaGamesDao> {
        get<NbaApiDatabase>().nbaApiGamesDao
    }

    single<INbaTeamsDao> {
        get<NbaApiDatabase>().nbaApiTeamsDao
    }

    single<INbaPlayersDao> {
        get<NbaApiDatabase>().nbaApiPlayersDao
    }

}