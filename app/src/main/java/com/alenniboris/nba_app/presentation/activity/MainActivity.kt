package com.alenniboris.nba_app.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.presentation.screens.NavGraphs
import com.alenniboris.nba_app.presentation.screens.destinations.EnterScreenDestination
import com.alenniboris.nba_app.presentation.screens.destinations.ShowingScreenDestination
import com.alenniboris.nba_app.presentation.test.presentation.TestPaginationUi
import com.alenniboris.nba_app.presentation.uikit.theme.NbaAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main()

        enableEdgeToEdge()
        setContent {
            NbaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    TestDatabaseUi()
                    TestPaginationUi()
//                    MainActivityShow()
//                    MainActivityShow()
                }
            }
        }
//        enableEdgeToEdge()
//        setContent {
//            NBA_APPTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MainActivityShow()
//                }
//            }
//        }
    }

    data class News(
        val id: String,
        val text: String
    )

    suspend fun getNewsByID(id: String): News = withContext(Dispatchers.IO) {
        delay(timeMillis = 1000)
        return@withContext News(id = id, text = UUID.randomUUID().toString())
    }

    suspend fun getAllNewsIDs(): List<String> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext (0 until 1000).map { it.toString() }
    }


//    suspend fun fetchAllNews() = withContext(Dispatchers.IO) {
//
//        val result = MutableStateFlow<List<News>>(emptyList())
//
//        val allIds = getAllNewsIDs().map {
//            launch {
//
//                val news = getNewsByID(it)
//
//                result.update { list ->
//                    list.toMutableList().apply {
//                        add(news)
//                    }
//                }
////                result.value = result.value.toMutableList().apply { add(news) }
//
//
//            }
//        }.joinAll()
//
//        return@withContext result.value
//    }

    // втупую (долго)
//    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
//        val allIds = getAllNewsIDs()
//        allIds.map { getNewsByID(it) }
//    }

    // втупую, но параллельно ( долго, но быстро)
    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
        val allIds = getAllNewsIDs()
        val semaphore = Semaphore(500)
        allIds.map {
            async(Dispatchers.IO) {
                semaphore.withPermit {
                    getNewsByID(it)
                }
            }
        }.awaitAll()
    }

//     на флоу
//    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
//        getAllNewsIDs().asFlow()
//            .map {
//                async(Dispatchers.IO) {
//                    getNewsByID(it)
//                }
//            }
//            .buffer(500)
//            .toList()
//            .awaitAll()
//    }

    //на флоу  и мержем
//    @OptIn(ExperimentalCoroutinesApi::class)
//    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
//        getAllNewsIDs()
//            .chunked(50)
//            .asFlow()
//            .flatMapMerge(100) {
//                flow { it.forEach {v -> emit(getNewsByID(v)) } }
//            }
//            .buffer(100)
//            .toList()
//    }

    // маразм, на зато буффер. ждем всех
//    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
//        val ids = async(Dispatchers.IO) {
//            getAllNewsIDs()
//        }
//
//        val channel = Channel<News>(Channel.UNLIMITED)
//
//        val job = launch(Dispatchers.IO) {
//            ids.await()
//                .asFlow()
//                .map { id -> async { getNewsByID(id) } }
//                .buffer(500)
//                .collect {
//                    channel.send(it.await())
//                }
//
//            channel.close()
//        }
//
//        val list: MutableList<News> = mutableListOf()
//        for (el in channel) {
//            list.add(el)
//        }
//
//        job.join()
//        return@withContext list
//    }

    // сложна, но ваот так. тип делаем сразу, как приходит
//    suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
//
//        val channelId = Channel<String>(Channel.UNLIMITED)
//        launch {
//            getAllNewsIDs().forEach {
//                channelId.send(it)
//            }
//            channelId.close()
//        }
//
//        val channelNews = Channel<News>(Channel.UNLIMITED)
//        val job = launch(Dispatchers.IO) {
//            channelId
//                .consumeAsFlow()
//                .map {
//                    async(Dispatchers.IO) {
//                        getNewsByID(it)
//                    }
//                }
//                .buffer(500)
//                .collect {
//                    channelNews.send(it.await())
//                }
//
//            channelNews.close()
//        }
//
//        val list: MutableList<News> = mutableListOf()
//        for (el in channelNews) {
//            list.add(el)
//        }
//
//        job.join()
//
//        return@withContext list
//    }


    suspend fun test(a: Int): Unit = withContext(Dispatchers.IO) {
        delay(a * 1000L)
        if (Random.nextBoolean()) throw Exception("error - ${a}")
        Log.e("!!!", "${a}")
    }


    fun main() {

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("!!!", "Exception: ${throwable.message}")
        }

//        val job = MainScope().launch(Dispatchers.IO + exceptionHandler) {
//            (0..10).forEach {
//                supervisorScope {
//                    launch() {
//                        test(it)
//                    }
//                }
//            }
//        }
        val job = MainScope().launch(Dispatchers.IO) {
            (0..10).forEach {
                supervisorScope {
                    launch(exceptionHandler) {
                        test(it)
                    }
                }
            }
        }

        MainScope().launch(Dispatchers.IO) {
            delay(5_000)
            job.cancel()
            Log.e("!!!", "stop")
        }


//        MainScope().launch(Dispatchers.IO) {
//            Log.e("!!!", "start")
//            val result = fetchAllNews()
//            Log.e("!!!", "${result.size}")
//        }


//        val test = MutableStateFlow("0")
//        val scope = MainScope() + Dispatchers.IO
//
//        scope.launch {
//            test.update {
//                Log.e("!!!", "update - ${it}")
//                delay(5000)
//                Log.e("!!!", "finish delay - ${it}")
//                "45"
//            }
//            Log.e("!!!", "set 45")
//        }
//
//        scope.launch {
//            delay(2000)
//            test.update { "89" }
//            Log.e("!!!", "set 89")
//        }
//
//        scope.launch {
//            delay(1000)
//            test.update { "112" }
//            Log.e("!!!", "set 112")
//        }
    }
}

@Composable
private fun MainActivityShow() {

    val mainActivityVM = koinViewModel<MainActivityVM>()
    val isUserAuthenticated by mainActivityVM.userAuthenticationStatus.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(mainActivityVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<MainActivityEvent.ShowToastMessage>().collect { ev ->
                toastMessage.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(ev.messageId), Toast.LENGTH_SHORT)
                toastMessage.show()
            }
        }
    }

    val navHostEngine = rememberNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(animationSpec = tween(1200)) },
            exitTransition = { fadeOut(animationSpec = tween(1200)) }
        )
    )

    Scaffold { pv ->
        Box(modifier = Modifier.padding(pv)) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = if (isUserAuthenticated) ShowingScreenDestination
                else EnterScreenDestination,
                engine = navHostEngine
            )
        }

    }

}