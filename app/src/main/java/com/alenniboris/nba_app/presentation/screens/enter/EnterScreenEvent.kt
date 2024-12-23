package com.alenniboris.nba_app.presentation.screens.enter

interface EnterScreenEvent {

    data class ShowToastMessage(val messageId: Int) : EnterScreenEvent

}