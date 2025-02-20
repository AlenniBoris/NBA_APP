package com.alenniboris.nba_app.domain.usecase.impl.authentication

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.usecase.authentication.ISignOutUserUseCase
import kotlinx.coroutines.withContext

class SignOutUserUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : ISignOutUserUseCase {

    override suspend fun invoke(): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authenticationRepository.signOut()
        }

}