package com.alenniboris.nba_app.domain.repository

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationRepository {

    suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    suspend fun registerUser(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    suspend fun signOut(): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    val user: StateFlow<UserModelDomain?>

}