package com.alenniboris.nba_app.domain.repository

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.UserDomainModel
import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationRepository {

    suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit>

    suspend fun registerUser(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit>

    suspend fun signOut(): CustomResultModelDomain<Unit>

    val user: StateFlow<UserDomainModel?>
}