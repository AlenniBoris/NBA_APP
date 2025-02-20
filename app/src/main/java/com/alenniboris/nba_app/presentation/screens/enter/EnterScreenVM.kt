package com.alenniboris.nba_app.presentation.screens.enter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.nba_app.domain.usecase.authentication.ILoginUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.IRegisterUserUseCase
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.mappers.toUiMessageString
import com.alenniboris.nba_app.presentation.screens.enter.state.IEnterState
import com.alenniboris.nba_app.presentation.screens.enter.state.LoginState
import com.alenniboris.nba_app.presentation.screens.enter.state.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnterScreenVM(
    private val loginUserUseCase: ILoginUserUseCase,
    private val registerUserUseCase: IRegisterUserUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<IEnterState>(LoginState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<EnterScreenEvent>(viewModelScope)
    val event = _event.flow


    fun handleIntent(intent: AuthenticationIntent) {
        when (intent) {
            is AuthenticationIntent.UpdateEnteredEmail -> updateEnteredEmail(intent.value)
            is AuthenticationIntent.UpdateEnteredPassword -> updateEnteredPassword(intent.value)
            is AuthenticationIntent.UpdateEnteredPasswordCheck -> updateEnteredPasswordCheck(intent.value)
            is AuthenticationIntent.SwitchBetweenLoginAndRegister -> changeStateForAppropriateBecauseOfScreen()
            is AuthenticationIntent.ButtonClickProceed -> {
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
        viewModelScope.launch {
            val currentState = _screenState.value
            val loginResult = loginUserUseCase.invoke(
                currentState.enteredEmail, currentState.enteredPassword
            )

            emitShowToastEventIfIsErrorCase(resultOfOperation = loginResult)
        }
    }

    private fun registerUser() {
        viewModelScope.launch {

            val currentState = _screenState.value

            (currentState as? RegistrationState)?.let {
                val registerResult = registerUserUseCase.invoke(
                    currentState.enteredEmail,
                    currentState.enteredPassword,
                    currentState.enteredPasswordCheck
                )

                emitShowToastEventIfIsErrorCase(resultOfOperation = registerResult)
            }
        }
    }

    private fun emitShowToastEventIfIsErrorCase(resultOfOperation: CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>) {
        (resultOfOperation as? CustomResultModelDomain.Error)?.let {
            val exception = resultOfOperation.exception
            _event.emit(
                EnterScreenEvent.ShowToastMessage(
                    exception.toUiMessageString()
                )
            )
        }
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