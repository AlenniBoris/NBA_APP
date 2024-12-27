package com.alenniboris.nba_app.presentation.mappers

import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.CustomExceptionModelDomain


fun CustomExceptionModelDomain.toUiMessageString(): Int = when (this) {
    CustomExceptionModelDomain.InvalidCredentialsException ->
        R.string.invalid_credentials_exception

    CustomExceptionModelDomain.InvalidUserException ->
        R.string.invalid_user_exception

    CustomExceptionModelDomain.NotEmailTypeValueException ->
        R.string.not_email_type_value_exception

    CustomExceptionModelDomain.PasswordIsNotEqualWithItsCheckException ->
        R.string.password_is_not_equal_with_its_check_exception

    CustomExceptionModelDomain.WeakPasswordException ->
        R.string.weak_password_exception

    CustomExceptionModelDomain.WebException ->
        R.string.web_exception

    CustomExceptionModelDomain.UserCollisionException ->
        R.string.user_collision_exception

    CustomExceptionModelDomain.Other ->
        R.string.other_exception
}