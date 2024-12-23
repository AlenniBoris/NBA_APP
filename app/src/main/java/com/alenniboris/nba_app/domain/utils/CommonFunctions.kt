package com.alenniboris.nba_app.domain.utils

import android.util.Patterns

object CommonFunctions {

    fun checkIfEmailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}