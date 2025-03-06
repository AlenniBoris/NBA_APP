package com.alenniboris.nba_app.presentation.test_livedata

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean

val TestLivedataModule = module {
    viewModel<TestLivedataVM> {
        TestLivedataVM()
    }
}

data class TestLivedataState(
    val data: List<String> = emptyList(),
    val isLoading: Boolean = false
)

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { value ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        }
    }

    fun call(value: T) {
        pending.set(true)
        super.setValue(value)
    }
}


class TestLivedataVM : ViewModel() {
    private val _state = MutableLiveData(TestLivedataState())
    val state: LiveData<TestLivedataState> get() = _state

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(_state.value?.copy(isLoading = true))

            val newData = emptyList<String>().toMutableList()
            for (i in 0..100) {
                delay(50)
                newData.add(UUID.randomUUID().toString())
            }

            _state.postValue(
                _state.value?.copy(
                    data = newData.toList(),
                    isLoading = false
                )
            )
        }
    }

    private val firstLivedata = MutableLiveData<String>("")
    private val secondLivedata = MutableLiveData<String>("")
    val mediatorLiveData = MediatorLiveData<String>().apply {
        addSource(firstLivedata) {
            value = "first live data / $it"
        }
        addSource(secondLivedata) {
            value = "second live data / $it"
        }
    }

    fun setFirstLivedataValue(value: String) {
        firstLivedata.value = value
    }

    fun setSecondLivedataValue(value: String) {
        secondLivedata.value = value
    }

    private val _initLivedata = MutableLiveData<Int>(0)
    val mappedLiveData = _initLivedata.map { it * 2 }
    val distinctLivedata = _initLivedata.distinctUntilChanged()

    fun changeInitialLivedata(value: Int) {
        _initLivedata.value = value
    }

    private val _event = SingleLiveEvent<String>()
    val event: LiveData<String> get() = _event

    fun triggerEvent(){
        _event.call(UUID.randomUUID().toString())
    }

}


@Composable
fun TestLivedataUi() {
    val testLivedataVM = koinViewModel<TestLivedataVM>()
//    val state by testLivedataVM.state.observeAsState(initial = TestLivedataState())

    // медиатор
//    val mediator by testLivedataVM.mediatorLiveData.observeAsState()
//    LaunchedEffect(mediator) { Log.e("!!!", "mediator changed to -> $mediator") }

    // мап и дистинкт
//    val mapped by testLivedataVM.mappedLiveData.observeAsState()
//    LaunchedEffect(mapped) { Log.e("!!!", "Mapped = $mapped") }
//
//    val distincted by testLivedataVM.distinctLivedata.observeAsState()
//    LaunchedEffect(distincted) { Log.e("!!!", "Distincted = $distincted") }

    //ивент
    val event by testLivedataVM.event.observeAsState()
    LaunchedEffect(event) { Log.e("!!!", "Triggered event, $event") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Просто подписка

//        Button(
//            onClick = { testLivedataVM.loadData() }
//        ) { Text(text = "Load data") }
//        when {
//            state.isLoading -> {
//                AppProgressBar(modifier = Modifier.fillMaxSize())
//            }
//
//            state.data.isNotEmpty() -> {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(state.data) { dataValue ->
//                        Box(
//                            modifier = Modifier
//                                .padding(vertical = 15.dp, horizontal = 5.dp)
//                                .fillMaxWidth()
//                                .height(40.dp)
//                                .background(appColor)
//                        ) {
//                            Text(
//                                modifier = Modifier.align(Alignment.Center),
//                                text = dataValue
//                            )
//                        }
//                    }
//                }
//            }
//        }

        // Медиатор

//        Column {
//            Button(
//                onClick = { testLivedataVM.setFirstLivedataValue(UUID.randomUUID().toString()) }
//            ) { Text("First set new value") }
//            Button(
//                onClick = {
//                    testLivedataVM.setSecondLivedataValue(
//                        UUID.randomUUID().toString()
//                    )
//                }
//            ) { Text("Second set new value") }
//        }

        // Мап и дистинкты
//        Column {
//            Button(
//                onClick = { testLivedataVM.changeInitialLivedata(1) }
//            ) { Text("1 для мапа") }
//            Row {
//                Button(
//                    onClick = { testLivedataVM.changeInitialLivedata(1) }
//                ) { Text("1 в дистинкт") }
//                Button(
//                    onClick = { testLivedataVM.changeInitialLivedata(2) }
//                ) { Text("2 в дистинкт") }
//            }
//        }

        // ивент
        Button(
            onClick = { testLivedataVM.triggerEvent() }
        ) { Text(text = "event") }


    }
}