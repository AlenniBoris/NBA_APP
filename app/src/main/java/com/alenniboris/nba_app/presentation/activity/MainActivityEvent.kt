package com.alenniboris.nba_app.presentation.activity

interface MainActivityEvent {

    data class ShowToastMessage(val messageId: Int) : MainActivityEvent

}