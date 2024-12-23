package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val AppDispatchersModule = module {
    single<AppDispatchers> {
        object : AppDispatchers {
            override val Main = Dispatchers.Main
            override val IO = Dispatchers.IO
            override val Default = Dispatchers.Default
        }
    }
}
