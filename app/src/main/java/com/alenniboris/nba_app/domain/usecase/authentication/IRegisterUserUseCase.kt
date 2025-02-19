package com.alenniboris.nba_app.domain.usecase.authentication

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain

interface IRegisterUserUseCase {

    suspend fun invoke(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

}