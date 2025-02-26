package com.alenniboris.nba_app.presentation.learning_recycler.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.usecase.learning_recycler.IGetLearningRecyclerDataUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LRMainScreenVM(
    private val getLearningRecyclerDataUseCase: IGetLearningRecyclerDataUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(LRMainScreenState())
    val state = _screenState.asStateFlow()

    private var loadingJob: Job? = null

    init {
        loadDataFromServer()
    }

    fun loadDataFromServer() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            _screenState.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }

            when (val result = getLearningRecyclerDataUseCase.invoke()) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(data = result.result) }
                }

                is CustomResultModelDomain.Error -> {
                    _screenState.update { it.copy(isError = true) }
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

}