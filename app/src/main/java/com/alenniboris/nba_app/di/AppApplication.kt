package com.alenniboris.nba_app.di

import android.app.Application
import com.alenniboris.nba_app.data.source.local.database.test.TestModule
import com.alenniboris.nba_app.presentation.test_pagination.di.testModule
import com.alenniboris.nba_app.data.source.local.database.test.TestModule
import com.alenniboris.nba_app.presentation.test.di.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(
                TestModule,
                ManagerModule,
                ViewModels,
                AuthModule,
                DispatchersModule,
                NetworkModule,
                DatabaseModule,
                testModule
            )
        }
    }
}