package com.alenniboris.nba_app.di

import android.app.Application
import com.alenniboris.nba_app.presentation.test_livedata.TestLivedataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

val myModules = listOf(
    ViewModels,
    AuthModule,
    DispatchersModule,
    NetworkModule,
    DatabaseModule,
    GamesUseCaseModule,
    TeamsUseCaseModule,
    PlayersUseCaseModule,
    SeasonsUseCaseModule,
    CountriesUseCaseModule,
    LeaguesUseCaseModule,
    TestLivedataModule
)

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(myModules)
        }
    }
}