package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LRSecondTypeModelDomain
import com.alenniboris.nba_app.domain.usecase.learning_recycler.IGetLearningRecyclerDataUseCase
import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi.Companion.toModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.LRModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.SecondTypeModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.SecondTypeModelUi.Companion.toModelUi
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

    private fun loadDataFromServer() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (val result = getLearningRecyclerDataUseCase.invoke()) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            data = result.result
                                .map {
                                    when (it) {
                                        is LRFirstTypeModelDomain -> {
                                            it.toModelUi()
                                        }

                                        is LRSecondTypeModelDomain -> {
                                            it.toModelUi()
                                        }

                                        else -> throw IllegalArgumentException("Unknown type")
                                    }
                                }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    Log.e("!!!", result.exception.stackTraceToString())
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun changeElementText(
        element: LRModelUi,
        newText: String
    ) {
        val domainModel = element.getModel()
        _screenState.update { state ->
            val editedData = state.data.toMutableList()
            editedData.replaceAll { item ->
                when (element) {
                    is FirstTypeModelUi -> {
                        if (item is FirstTypeModelUi && item.getModel().id == domainModel.id) {
                            item.editableText = newText

                            if (newText.isNotEmpty()) {
                                item.getModel().copy(text = newText).toModelUi()
                            } else item
                        } else item
                    }

                    is SecondTypeModelUi -> {
                        if (item is SecondTypeModelUi && item.getModel().id == domainModel.id) {
                            if (newText.isNotEmpty()) {
                                item.getModel().copy(name = newText).toModelUi()
                            } else item
                        } else item
                    }

                    else -> throw IllegalArgumentException("Unknown type")
                }
            }
            state.copy(data = editedData)
        }

    }

}