package com.alenniboris.nba_app.domain.usecase.authentication

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain

interface ISignOutUserUseCase {

    suspend fun invoke(): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

}