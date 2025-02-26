package com.alenniboris.nba_app.presentation.learning_recycler.details

import androidx.lifecycle.ViewModel
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LRDetailsScreenVM() : ViewModel() {

    private val _screenState = MutableStateFlow(LRDetailsScreenState())
    val state = _screenState.asStateFlow()

    fun setElement(element: LearningRecyclerDataModelDomain?) {
        _screenState.update { it.copy(element = element) }
    }

}