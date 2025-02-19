package com.alenniboris.nba_app.domain.usecase.impl.authentication

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.usecase.authentication.IRegisterUserUseCase
import kotlinx.coroutines.withContext

class RegisterUserUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IRegisterUserUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val registerResult =
                authenticationRepository.registerUser(email, password, passwordCheck)

            return@withContext when (registerResult) {
                is CustomResultModelDomain.Success -> authenticationRepository.loginUser(
                    email,
                    password
                )

                is CustomResultModelDomain.Error -> CustomResultModelDomain.Error(registerResult.exception)
            }
        }

}