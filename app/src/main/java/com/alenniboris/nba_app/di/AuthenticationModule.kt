package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.authentication.AuthenticationRepositoryImpl
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.usecase.authentication.IGetCurrentUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.ILoginUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.IRegisterUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.ISignOutUserUseCase
import com.alenniboris.nba_app.domain.usecase.impl.authentication.GetCurrentUserUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.authentication.LoginUserUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.authentication.RegisterUserUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.impl.authentication.SignOutUserUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val AuthModule = module {

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<IAuthenticationRepository> {
        AuthenticationRepositoryImpl(
            auth = get<FirebaseAuth>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IGetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>()
        )
    }

    single<ILoginUserUseCase> {
        LoginUserUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IRegisterUserUseCase> {
        RegisterUserUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<ISignOutUserUseCase> {
        SignOutUserUseCaseImpl(
            authenticationRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}