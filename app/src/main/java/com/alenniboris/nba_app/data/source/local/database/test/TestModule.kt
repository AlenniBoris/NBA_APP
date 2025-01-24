package com.alenniboris.nba_app.data.source.local.database.test

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val TestModule = module {

    single<TestDatabase> {
        TestDatabase.get(apl = androidApplication())
    }

    single<ITestDao> {
        get<TestDatabase>().testDao
    }


    single<ITestRepository> {
        TestRepository(
            dao = get<ITestDao>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<ITestManager> {
        TestManager(
            repos = get<ITestRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<TestVM> {
        TestVM(
            testManager = get<ITestManager>()
        )
    }
}