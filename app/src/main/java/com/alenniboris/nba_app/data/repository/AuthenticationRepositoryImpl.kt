package com.alenniboris.nba_app.data.repository

import android.util.Log
import com.alenniboris.nba_app.data.mappers.toCustomExceptionModelDomain
import com.alenniboris.nba_app.data.mappers.toUserDomainModel
import com.alenniboris.nba_app.domain.model.AppDispatchers
import com.alenniboris.nba_app.domain.model.CustomExceptionModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.repository.IAuthenticationRepository
import com.alenniboris.nba_app.domain.utils.CommonFunctions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val auth: FirebaseAuth,
    private val dispatchers: AppDispatchers
) : IAuthenticationRepository {

    private val _user = MutableStateFlow(auth.currentUser?.toUserDomainModel())
    override val user = _user.asStateFlow()

    override suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit> = withContext(dispatchers.IO) {

        return@withContext runCatching {
            if (!CommonFunctions.checkIfEmailIsValid(email)) {
                return@runCatching CustomResultModelDomain.Error(
                    CustomExceptionModelDomain.NotEmailTypeValueException
                )
            }

            auth.signInWithEmailAndPassword(email, password).await()
            _user.update { auth.currentUser?.toUserDomainModel() }

            CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("FirebaseRepositoryImpl", exception.stackTraceToString())
            CustomResultModelDomain.Error(
                exception.toCustomExceptionModelDomain()
            )
        }

    }

    override suspend fun registerUser(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit> = withContext(dispatchers.IO) {

        return@withContext runCatching {
            if (!CommonFunctions.checkIfEmailIsValid(email)) {
                return@runCatching CustomResultModelDomain.Error(
                    CustomExceptionModelDomain.NotEmailTypeValueException
                )
            }

            if (password != passwordCheck) {
                return@runCatching CustomResultModelDomain.Error(
                    CustomExceptionModelDomain.PasswordIsNotEqualWithItsCheckException
                )
            }

            auth.createUserWithEmailAndPassword(email, password).await()
            CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("FirebaseRepositoryImpl", exception.stackTraceToString())
            CustomResultModelDomain.Error(
                exception.toCustomExceptionModelDomain()
            )
        }

    }

    override suspend fun signOut(): CustomResultModelDomain<Unit> = withContext(dispatchers.IO) {
        return@withContext runCatching {
            auth.signOut()
            _user.update { auth.currentUser?.toUserDomainModel() }
            CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("FirebaseRepositoryImpl", exception.stackTraceToString())
            CustomResultModelDomain.Error(
                exception.toCustomExceptionModelDomain()
            )
        }
    }

}