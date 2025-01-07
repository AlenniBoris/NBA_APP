package com.alenniboris.nba_app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking


//channel flow

fun fetchDataFlow(): Flow<String> = channelFlow() {
    val data1 = launch { // Launch a coroutine to simulate fetching data
        delay(1000) // Simulate some asynchronous operation
        send("Data from source 1")
//        throw Exception("en")
    }
    val data2 = async { // Launch another coroutine to fetch more data
        delay(3000)
        send("Data from source 2")
    }
    // Await completion of both coroutines (optional)
    data1.join()
    data2.await()
    // No need to explicitly close the channel, as the flow will be complete
}

// callback flow

class Sensor {
    private var listener: ((Int) -> Unit)? = null

    fun setOnDataListener(callback: (Int) -> Unit) {
        listener = callback
    }

    fun startEmittingData() {
        Thread {
            for (i in 1..10) {
                Thread.sleep(500) // Имитация задержки
                listener?.invoke(i)
            }
        }.start()
    }

    fun stopEmittingData() {
        listener = null
    }
}

// Преобразуем Sensor API в Flow
fun getSensorDataFlow() = callbackFlow {
    val sensor = Sensor()

    // Регистрируем callback
    sensor.setOnDataListener { data ->
        trySend(data) // Отправляем данные в поток
    }

    sensor.startEmittingData()

    // Освобождаем ресурсы при завершении потока
    awaitClose {
        sensor.stopEmittingData()
        println("Sensor stopped")
    }
}

class Button {
    private var onClickListener: (() -> Unit)? = null

    fun setOnClickListener(listener: (() -> Unit)?) {
        onClickListener = listener
    }

    fun click() {
        onClickListener?.invoke()
    }
}

// Преобразуем события в Flow
fun buttonClicks(button: Button) = callbackFlow {
    val listener: () -> Unit = {
        trySend(Unit).isSuccess // Отправляем событие в поток
    }

    button.setOnClickListener(listener)

    awaitClose {
        button.setOnClickListener(null)// Удаляем listener при завершении
    }
}


fun main() {

    runBlocking {
        // COLD
//        val numbers: Flow<Int> = flow {
//            repeat(10) { it ->
//                emit(it)
//            }
//        }
//
//        launch {
//            numbers.collect{ it ->
//                println("Cold + $it")
//            }
//            println("1")
//        }

//        val mFlow = channelFlow{
//            (0..10).forEach {
//                launch {
////                    delay(100)
//                    send(it)
//
//                }
//            }
//            awaitClose{
//                println("close")
//            }
//        }
//
//        launch {
//            mFlow.collect{
//                println(it)
//            }
//            println("finish")
//        }

            // Channel flow
//        fetchDataFlow().collect{
//            println("Received: $it")
//        }

//        val eventChannel = Channel<String>()

//        // Simulate emitting events asynchronously
//        launch {
//            repeat(5) {
//                delay(500)
//                eventChannel.send("Event $it")
//            }
//            eventChannel.close() // Close the channel when done
//        }

//        // Create a ChannelFlow to process events
//        val eventFlow = channelFlow<String> {
//            for (event in eventChannel) {
//                send("Processed: $event")
//            }
//        }
//
//        eventFlow.collect { processedEvent ->
//            println(processedEvent)
//        }

//        // Combining them
//        //  Define two channels with different ranges of values
//        val channel1 = produce<Int> {
//            repeat(5) {
//                delay(1000)
//                send(it)
//            }
//        }
//        val channel2 = produce<Int> {
//            repeat(5) { send(it + 10) }
//        }
//
            // Create a ChannelFlow to concatenate the values from both channels
//        val concatenatedFlow = channelFlow<Int> {
//            val channels = listOf(channel1, channel2)
//            for (channel in channels) {
//                for (value in channel) {
//                    send(value)
//                }
//            }
//        }
//
            // Collect and print concatenated values
//        concatenatedFlow.collect { value ->
//            println("Value: $value")
//        }

            // callback flow

//        launch {
//            val flow = getSensorDataFlow()
//            flow.collect { data ->
//                println("Received data: $data")
//            }
//        }

//        val button = Button()
//        val job = launch {
//            buttonClicks(button).collect {
//                println("Button clicked!")
//            }
//        }
//        button.click()
//        button.click()
//        job.cancel()


            //HOT
//        val sharedFlow = MutableSharedFlow<Int>()
//
//        launch {
//            sharedFlow.emit(0)
//            sharedFlow.emit(1)
//            sharedFlow.emit(2)
//            sharedFlow.emit(3)
//            sharedFlow.emit(4)
//            sharedFlow.emit(5)
//
//            sharedFlow.collect{ value ->
//                println("Hot + $value")
//            }
//        }
//        launch {
//            sharedFlow.emit(1)
//            sharedFlow.emit(2)
//            sharedFlow.emit(3)
//            sharedFlow.emit(4)
//            sharedFlow.emit(5)
//        }


//        val sharedFlow = MutableSharedFlow<String>(replay = 1)
//
//        // Подписчики
//        launch {
//            sharedFlow.collect { value ->
//                println("Subscriber 1 received: $value")
//            }
//        }
//
//        launch {
//            sharedFlow.collect { value ->
//                println("Subscriber 2 received: $value")
//            }
//        }
//
//        launch {
//            // Отправка данных
//            sharedFlow.emit("Hello")
//            sharedFlow.emit("Kotlin")
//        }


            // Share IN , State In

//        val coldFlow = flow {
//            emit(1)
//            delay(1000)
//            emit(2)
//            delay(1000)
//            emit(3)
//        }
//
//        val hotFlow = coldFlow.stateIn(
//            scope = this,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = 0
//        )
//
//        println(hotFlow.value) // 0 (initialValue)
//
//        launch {
//            hotFlow.collect { value ->
//                println("Subscriber 1: $value")
//            }
//        }
//
//        delay(1500)
//        println(hotFlow.value)
//
//        val coldFlow = flow {
//            emit(1)
//            delay(1000)
//            emit(2)
//            delay(1000)
//            emit(3)
//        }
//
//        val hotFlow = coldFlow.shareIn(
//            scope = this,
//            started = SharingStarted.WhileSubscribed(5000),
//            replay = 1
//        )
//
//        launch {
//            hotFlow.collect { value ->
//                println("Subscriber 1: $value")
//            }
//        }
//
//        launch {
//            hotFlow.collect { value ->
//                println("Subscriber 2: $value")
//            }
//        }
//

            // Operayori
//
//        println("map")
//        flowOf(1, 2, 3)
//            .map { it * 2 }
//            .collect { println(it) }
//
//        println("filter")
//        flowOf(1, 2, 3, 4, 5)
//            .filter { it % 2 == 0 }
//            .collect { println(it) }
//
//        println("debounce")
//        flow {
//            emit(1)
//            emit(2)
//            delay(500)
//            emit(3)
//        }.debounce(300)
//            .collect { println(it) }
//
//        println("take")
//        flowOf(1, 2, 3, 4, 5)
//            .take(3)
//            .collect { println(it) }
//
//        println("buffer")
//        flow {
//            for (i in 1..3) {
//                delay(1000)
//                emit(i)
//            }
//        }.buffer()
//            .map { it * 2 }
//            .collect { println(it) }
//
//        println("catch")
//        flow {
//            emit(1)
//            throw RuntimeException("Error")
//        }.catch { e ->
//            println("error: ${e.message}")
//        }.collect { println(it) }
//
//        println("flwo on")
//        flow {
//            println("emit: ${Thread.currentThread().name}")
//            emit(1)
//        }.flowOn(newFixedThreadPoolContext(4, "myThread"))
//            .collect {
//                println("collect: ${Thread.currentThread().name}")
//            }
//
//        println("combine")
//        val flow1 = flow<Int> {
//            delay(1000)
//            emit(14)
//            emit(21)
//        }
//        val flow2 = flow<Int> {
//            delay(100)
//            emit(7)
//            emit(8)
//            emit(9)
//        }
//        combine(flow1, flow2) { number, letter ->
//            "$number-$letter"
//        }.collect { println(it) }
//
//        println("drop")
//        flowOf(1, 2, 3, 4, 5)
//            .drop(2)
//            .collect { println(it) }
//
//        println("distinctUntilChanged")
//        flowOf(1, 1,3, 2, 3, 4, 4)
//            .distinctUntilChanged()
//            .collect { println(it) }
//
//        println("first / firstOrNull")
//        val result = flowOf(1, 2, 3).first()
////        val result = flow<Int> {  }.first()
//        println(result)
//
//        val emptyResult = emptyFlow<Int>().firstOrNull()
//        println(emptyResult)
//
        println("fold")
        val sum = flowOf(1, 2, 3)
            .fold(0) { accumulator, value ->
                accumulator + value
            }
        println(sum)

            // Flatmap

//        val flow1 = flow {
//            delay(1000)
//            emit(1)
//            delay(1000)
//            emit(2)
//        }
//
//        val flow2 = flow {
//            emit(3)
//            delay(1500)
////            delay(500)
//            emit(4)
//        }
//
//        flowOf(flow1, flow2)
//            .flatMapMerge { it }  // Используем merge, чтобы объединить потоки
//            .collect { value ->
//                println(value)
//            }
//
//        val flow1 = flow {
//            delay(1000)
//            emit(1)
//            delay(1000)
//            emit(2)
//        }
//
//        val flow2 = flow {
//            delay(2000)
//            emit(3)
//            delay(1000)
//            emit(4)
//        }
//
//        flowOf(flow1, flow2)
//            .flatMapConcat { it }
//            .collect { value ->
//                println(value)
//            }


//        val flow1 = flow {
//            delay(100)
//            emit(1)
//            delay(100)
//            emit(2)
//        }
//
//        val flow2 = flow {
//            delay(100)
//            emit(3)
//            delay(500)
//            emit(4)
//        }
//
//        flowOf(flow1, flow2)
//            .flatMapLatest { it }
//            .collect { value ->
//                println(value)
//            }


    }
    return

}
