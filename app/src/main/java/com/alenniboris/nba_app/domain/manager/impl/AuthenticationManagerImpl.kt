package com.alenniboris.nba_app.domain.manager.impl

import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.IAuthenticationRepository
import kotlinx.coroutines.withContext

class AuthenticationManagerImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IAuthenticationManager {

    private val _user = authenticationRepository.user
    override val user = _user

    override suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authenticationRepository.loginUser(email, password)
        }

    override suspend fun registerUser(
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

    override suspend fun signOut(): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authenticationRepository.signOut()
        }

}