package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.AuthenticationRepositoryImpl
import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.manager.impl.AuthenticationManagerImpl
import com.alenniboris.nba_app.domain.model.AppDispatchers
import com.alenniboris.nba_app.domain.repository.IAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val AuthModule = module {

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<IAuthenticationRepository> {
        AuthenticationRepositoryImpl(
            auth = get<FirebaseAuth>(),
            dispatchers = get<AppDispatchers>()
        )
    }

    single<IAuthenticationManager> {
        AuthenticationManagerImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            dispatchers = get<AppDispatchers>()
        )
    }

}