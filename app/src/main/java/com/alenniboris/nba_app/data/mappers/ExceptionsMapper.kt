package com.alenniboris.nba_app.data.mappers

import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toAuthenticationExceptionModelDomain(): AuthenticationExceptionModelDomain =
    when (this) {
        is AuthenticationExceptionModelDomain -> this
        is FirebaseAuthWeakPasswordException -> AuthenticationExceptionModelDomain.WeakPasswordException
        is FirebaseAuthInvalidCredentialsException -> AuthenticationExceptionModelDomain.InvalidCredentialsException
        is FirebaseAuthInvalidUserException -> AuthenticationExceptionModelDomain.InvalidUserException
        is FirebaseAuthWebException -> AuthenticationExceptionModelDomain.WebException
        is FirebaseAuthUserCollisionException -> AuthenticationExceptionModelDomain.UserCollisionException
        else -> AuthenticationExceptionModelDomain.Other
    }

fun Throwable.toNbaApiExceptionModelDomain(): NbaApiExceptionModelDomain =
    when (this) {
        is NbaApiExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> NbaApiExceptionModelDomain.NoInternetException
        else -> NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
    }