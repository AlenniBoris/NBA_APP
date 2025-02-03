package com.alenniboris.nba_app.presentation.test.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.test.domain.TestManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TestState(
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val elements: List<String> = emptyList(),
    val isLoadingMorePossible: Boolean = true,
    val pageSize: Int = 0,
)

class TestVM(
    private val testManager: TestManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(TestState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ITestEvent>(viewModelScope)
    val event = _event.flow

    private var _loadingJob: Job? = null

    init {
        loadData()
    }

    fun proceedIntent(intent: ITestIntent) = when (intent) {
        is ITestIntent.LoadMoreData -> loadData()
    }

    private fun loadData() {

        if (!_screenState.value.isLoadingMorePossible || _screenState.value.isLoading) return

        viewModelScope.launch {

            _screenState.update { it.copy(isLoading = true) }
            Log.e("!!!", "loading - ${_screenState.value.currentPage}")

            testManager.getMyDataPage(_screenState.value.currentPage)
                .onSuccess { result ->
                    _screenState.update { state ->
                        state.copy(
                            elements = state.elements + result.data,
                            currentPage = state.currentPage + 1,
                            isLoadingMorePossible = state.currentPage + 1 <= result.maxPage,
                            isLoading = false,
                            pageSize = result.data.size
                        )
                    }
                }
                .onFailure { exception ->
                    _event.emit(ITestEvent.ShowMessage(exception.message!!))
                    _screenState.update { it.copy(isLoading = false) }
                    loadData()
                }


        }

    }

}