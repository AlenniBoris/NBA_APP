package com.alenniboris.nba_app.presentation.screens.enter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alenniboris.nba_app.presentation.screens.enter.state.IEnterState
import com.alenniboris.nba_app.presentation.screens.enter.state.LoginState
import com.alenniboris.nba_app.presentation.screens.enter.state.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EnterScreenVM : ViewModel() {

    private val _screenState = MutableStateFlow<IEnterState>(LoginState())
    val screenState = _screenState.asStateFlow()


    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.UpdateEnteredEmail -> updateEnteredEmail(intent.value)
            is AuthIntent.UpdateEnteredPassword -> updateEnteredPassword(intent.value)
            is AuthIntent.UpdateEnteredPasswordCheck -> updateEnteredPasswordCheck(intent.value)
            is AuthIntent.SwitchBetweenLoginAndRegister -> changeStateForAppropriateBecauseOfScreen()
            is AuthIntent.ButtonClickProceed -> {
                when (_screenState.value) {
                    is LoginState -> loginUser()
                    is RegistrationState -> registerUser()
                }
            }
        }
    }

    private fun updateEnteredEmail(value: String) {
        _screenState.update { state ->
            when (state) {
                is LoginState -> state.copy(enteredEmail = value)
                is RegistrationState -> state.copy(enteredEmail = value)
            }
        }
    }

    private fun updateEnteredPassword(value: String) {
        _screenState.update { state ->
            when (state) {
                is LoginState -> state.copy(enteredPassword = value)
                is RegistrationState -> state.copy(enteredPassword = value)
            }
        }
    }

    private fun updateEnteredPasswordCheck(value: String) {
        _screenState.update { state ->
            when (state) {
                is LoginState -> state
                is RegistrationState -> state.copy(enteredPasswordCheck = value)
            }
        }
    }

    private fun loginUser() {
        Log.d("INTENT_ENTER", "User is logged in")
    }

    private fun registerUser() {
        Log.d("INTENT_ENTER", "User is registered")
    }

    private fun changeStateForAppropriateBecauseOfScreen() {
        _screenState.update { value ->
            when (value) {
                is LoginState -> RegistrationState()
                is RegistrationState -> LoginState()
            }
        }
    }
}