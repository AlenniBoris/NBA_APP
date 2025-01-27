package com.alenniboris.nba_app.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityVM(
    private val authenticationManager: IAuthenticationManager,
) : ViewModel() {

    private val _userAuthenticationState = MutableStateFlow(
        authenticationManager.user.value != null
    )
    val userAuthenticationStatus = _userAuthenticationState.asStateFlow()

    private val _event = SingleFlowEvent<MainActivityEvent>(viewModelScope)
    val event = _event.flow

    init {
        if (_userAuthenticationState.value) {
            _event.emit(MainActivityEvent.ShowToastMessage(R.string.welcome_back_text))
        }
    }

    init {
        viewModelScope.launch {
            authenticationManager.user.collect { currentUser ->
                if (!_userAuthenticationState.value && currentUser != null) {
                    _event.emit(MainActivityEvent.ShowToastMessage(R.string.welcome_text))
                }
                if (_userAuthenticationState.value && currentUser == null) {
                    _event.emit(MainActivityEvent.ShowToastMessage(R.string.goodbye_text))
                }
                _userAuthenticationState.update {
                    currentUser != null
                }
            }
        }
    }

}