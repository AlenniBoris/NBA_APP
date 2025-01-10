package com.alenniboris.nba_app.data.repository

import android.util.Log
import com.alenniboris.nba_app.data.mappers.toAuthenticationExceptionModelDomain
import com.alenniboris.nba_app.data.mappers.toUserDomainModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.IAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val auth: FirebaseAuth,
    private val dispatchers: IAppDispatchers
) : IAuthenticationRepository {

    private val _user = MutableStateFlow(auth.currentUser?.toUserDomainModel())
    override val user = _user.asStateFlow()

    override suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {

            return@withContext runCatching {
                if (!AuthenticationProcessFunctions.checkIfEmailIsValid(email)) {
                    return@runCatching CustomResultModelDomain.Error(
                        AuthenticationExceptionModelDomain.NotEmailTypeValueException
                    )
                }

                auth.signInWithEmailAndPassword(email, password).await()
                _user.update { auth.currentUser?.toUserDomainModel() }

                CustomResultModelDomain.Success<Unit, AuthenticationExceptionModelDomain>(Unit)
            }.getOrElse { exception ->
                Log.e("AuthenticationRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(
                    exception.toAuthenticationExceptionModelDomain()
                )
            }

        }

    override suspend fun registerUser(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {

            return@withContext runCatching {
                if (!AuthenticationProcessFunctions.checkIfEmailIsValid(email)) {
                    return@runCatching CustomResultModelDomain.Error(
                        AuthenticationExceptionModelDomain.NotEmailTypeValueException
                    )
                }

                if (password != passwordCheck) {
                    return@runCatching CustomResultModelDomain.Error(
                        AuthenticationExceptionModelDomain.PasswordIsNotEqualWithItsCheckException
                    )
                }

                auth.createUserWithEmailAndPassword(email, password).await()
                CustomResultModelDomain.Success<Unit, AuthenticationExceptionModelDomain>(Unit)
            }.getOrElse { exception ->
                Log.e("AuthenticationRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(
                    exception.toAuthenticationExceptionModelDomain()
                )
            }

        }

    override suspend fun signOut(): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext runCatching {
                auth.signOut()
                _user.update { auth.currentUser?.toUserDomainModel() }
                CustomResultModelDomain.Success<Unit, AuthenticationExceptionModelDomain>(Unit)
            }.getOrElse { exception ->
                Log.e("AuthenticationRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(
                    exception.toAuthenticationExceptionModelDomain()
                )
            }
        }

}