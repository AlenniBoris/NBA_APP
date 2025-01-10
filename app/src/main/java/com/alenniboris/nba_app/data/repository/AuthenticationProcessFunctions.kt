package com.alenniboris.nba_app.data.repository

import android.util.Patterns

object AuthenticationProcessFunctions {
    fun checkIfEmailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}