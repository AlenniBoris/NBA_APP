package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val IAppDispatchersModule = module {
    single<IAppDispatchers> {
        object : IAppDispatchers {
            override val Main = Dispatchers.Main
            override val IO = Dispatchers.IO
            override val Default = Dispatchers.Default
        }
    }
}
