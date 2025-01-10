package com.alenniboris.nba_app.presentation.mappers

import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain


fun AuthenticationExceptionModelDomain.toUiMessageString(): Int = when (this) {
    AuthenticationExceptionModelDomain.InvalidCredentialsException ->
        R.string.invalid_credentials_exception

    AuthenticationExceptionModelDomain.InvalidUserException ->
        R.string.invalid_user_exception

    AuthenticationExceptionModelDomain.NotEmailTypeValueException ->
        R.string.not_email_type_value_exception

    AuthenticationExceptionModelDomain.PasswordIsNotEqualWithItsCheckException ->
        R.string.password_is_not_equal_with_its_check_exception

    AuthenticationExceptionModelDomain.WeakPasswordException ->
        R.string.weak_password_exception

    AuthenticationExceptionModelDomain.WebException ->
        R.string.web_exception

    AuthenticationExceptionModelDomain.UserCollisionException ->
        R.string.user_collision_exception

    AuthenticationExceptionModelDomain.Other ->
        R.string.other_exception

}