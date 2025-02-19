package com.alenniboris.nba_app.domain.usecase.impl.authentication

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.usecase.authentication.ILoginUserUseCase
import kotlinx.coroutines.withContext

class LoginUserUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : ILoginUserUseCase {

    override suspend fun invoke(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authenticationRepository.loginUser(email, password)
        }

}