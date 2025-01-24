package com.alenniboris.nba_app.data.source.local.database.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TestVM(
    private val testManager: ITestManager
) : ViewModel() {

    private val _elementsState = MutableStateFlow<List<TestEntity>>(emptyList())
    val elementsState = _elementsState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            testManager.elementsFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect{ elements ->
                    _elementsState.update {
                        elements
                    }
                }
        }
    }

    fun add(testEntity: TestEntity){
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            testManager.addEntity(testEntity)
        }
    }

    fun delete(testEntity: TestEntity){
        viewModelScope.launch(Dispatchers.IO + SupervisorJob()) {
            testManager.deleteEntity(testEntity)
        }
    }

}