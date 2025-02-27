package com.alenniboris.nba_app.presentation.learning_recycler.details

import androidx.lifecycle.ViewModel
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LRDetailsScreenVM(
    private val element: LRFirstTypeModelDomain
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        LRDetailsScreenState(
            element = element
        )
    )
    val state = _screenState.asStateFlow()

}