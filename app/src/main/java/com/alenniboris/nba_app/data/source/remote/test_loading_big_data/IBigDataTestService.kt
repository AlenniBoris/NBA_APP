package com.alenniboris.nba_app.data.source.remote.test_loading_big_data

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.data.mappers.toNbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID


val TestBigDataModule = module {
    single<IBigDataTestService> {
        IBigDataTestService.get()
    }

    single<ITestBigDataRepository> {
        TestBigDataRepositoryImpl(
            testBigDataService = get<IBigDataTestService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    viewModel<TestBigDataVM> {
        TestBigDataVM(
            repository = get<ITestBigDataRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }
}

// --------------------------------------------------------------------
interface IBigDataTestService {
    @Streaming
    @GET
    suspend fun downloadTestData(
        @Url url: String
    ): ResponseBody

    companion object {
        fun get() = Retrofit
            .Builder()
            .baseUrl("http://lg.hosterby.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()
            .create<IBigDataTestService>()
    }
}

// --------------------------------------------------------------------

sealed class Downloading {
    class Loading(val progress: String) : Downloading()
    class Error(val error: NbaApiExceptionModelDomain) : Downloading()
    class Success(val file: File) : Downloading()
    class Started : Downloading()
}

interface ITestBigDataRepository {
    fun downloadData(
        url: String,
        savePath: String,
        fileName: String
    ): Flow<Downloading>
}

class TestBigDataRepositoryImpl(
    private val testBigDataService: IBigDataTestService,
    private val dispatchers: IAppDispatchers
) : ITestBigDataRepository {

    override fun downloadData(
        url: String,
        savePath: String,
        fileName: String
    ) = channelFlow {

        send(Downloading.Started())

        val tempFile = File(savePath, UUID.randomUUID().toString())
        val input: InputStream? = null

        runCatching {
            tempFile.apply {
                parentFile?.mkdirs()
                createNewFile()
            }

            val requestResult = testBigDataService.downloadTestData(
                url = url
            )

            val input = requestResult.byteStream()
            val outputStream = FileOutputStream(tempFile)
            outputStream.use { output ->
                val totalBytes = requestResult.contentLength().toFloat()
                var progressBytes = 0f
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                    progressBytes += read
                    send(
                        Downloading.Loading(
                            progress = ((progressBytes / totalBytes) * 100).toInt().toString()
                        )
                    )
                }
                output.flush()
            }

            val resultFile = File(savePath, fileName).apply {
                parentFile?.mkdirs()
            }

            tempFile.renameTo(resultFile)
            send(Downloading.Success(file = resultFile))
        }.getOrElse { exception ->
            send(Downloading.Error(error = exception.toNbaApiExceptionModelDomain()))
        }
        tempFile.delete()
        input?.close()
    }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .flowOn(dispatchers.IO)
}

// --------------------------------------------------------------------
interface ITestEvent {
    data class ShowToast(val text: String) : ITestEvent
}

data class LoadingState(
    val loadingProgress: String = "0",
)

class TestBigDataVM(
    private val repository: ITestBigDataRepository,
    private val dispatchers: IAppDispatchers
) : ViewModel() {

    private val _state = MutableStateFlow(LoadingState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<ITestEvent>(viewModelScope)
    val event = _event.flow

    fun load(url: String, savePath: String, fileName: String) {
        viewModelScope.launch(dispatchers.IO) {
            repository.downloadData(
                url = url,
                savePath = savePath,
                fileName = fileName
            )
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { collected ->
                    when (collected) {
                        is Downloading.Error -> {
//                            Log.e("!!!", "Fucke you, error")
                            _event.emit(ITestEvent.ShowToast("Fucke you, error"))
                        }

                        is Downloading.Loading -> {
//                            Log.e("!!!", collected.progress)
                            _state.update { it.copy(loadingProgress = collected.progress) }
                        }

                        is Downloading.Success -> {
//                            Log.e("!!!", "Downloaded")
                            _event.emit(ITestEvent.ShowToast("Downloaded"))
                        }

                        is Downloading.Started -> {
                            _event.emit(ITestEvent.ShowToast("Downloading started, wait a bit"))
                        }
                    }
                }
        }
    }


}

// --------------------------------------------------------------------

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TestBigDataUi(modifier: Modifier) {

    val vm = koinViewModel<TestBigDataVM>()
    val state by vm.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(vm.event) }

    val load by remember { mutableStateOf(vm::load) }


    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<ITestEvent.ShowToast>().collect() {
                toastMessage.cancel()
                toastMessage = Toast.makeText(context, it.text, Toast.LENGTH_SHORT)
                toastMessage.show()
            }
        }
    }

    Column(
        modifier = modifier
    ) {

        Button({
            load(
                "http://lg.hosterby.com/10MB.test",
                "/data/data/com.alenniboris.nba_app/files/TestBigData/Test/",
                "testing"
            )
        }) {
            Text("To filesDir")
        }
        Text("${state.loadingProgress}/100")

        Text("--------------------------------------")
//
//        Button({
//            val file = File("/data/data/com.alenniboris.nba_app/cache/TestBigData/Test/")
//            file.parentFile?.mkdirs()
//            loadSecond(file)
//        }) {
//            Text("To externalCacheDir")
//        }
//        Text("${state.secondProgress}/100")
//
//        Text("--------------------------------------")
//
//        Button({
//            val file = File(
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                "TestBigData/Test/"
//            )
//            file.parentFile?.mkdirs()
//            loadThird(file)
//        }) {
//            Text("To \"БазовоеМесто/MyTestFolder/Test/testFile.png\"")
//        }
//        Text("${state.thirdProgress}/100")
//
//        Text("--------------------------------------")
    }

}
