package com.alenniboris.nba_app.data.mappers

import com.alenniboris.nba_app.domain.model.CustomExceptionModelDomain
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException

fun Throwable.toCustomExceptionModelDomain(): CustomExceptionModelDomain = when (this) {
    is CustomExceptionModelDomain -> this
    is FirebaseAuthWeakPasswordException -> CustomExceptionModelDomain.WeakPasswordException
    is FirebaseAuthInvalidCredentialsException -> CustomExceptionModelDomain.InvalidCredentialsException
    is FirebaseAuthInvalidUserException -> CustomExceptionModelDomain.InvalidUserException
    is FirebaseAuthWebException -> CustomExceptionModelDomain.WebException
    is FirebaseAuthUserCollisionException -> CustomExceptionModelDomain.UserCollisionException
    else -> CustomExceptionModelDomain.Other
}