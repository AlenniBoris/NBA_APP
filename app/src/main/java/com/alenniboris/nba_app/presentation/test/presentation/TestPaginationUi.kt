package com.alenniboris.nba_app.presentation.test.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppTextField
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun TestPaginationUi() {

    val testVM = koinViewModel<TestVM>()
    val state by testVM.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(testVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }
    val proceedIntentAction by remember {
        mutableStateOf(testVM::proceedIntent)
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<ITestEvent.ShowMessage>().collect { value ->
                toastMessage.cancel()
                toastMessage = Toast.makeText(context, value.message, Toast.LENGTH_SHORT)
                toastMessage.show()
            }
        }
    }


    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { items ->
                val lastVisible = items.lastOrNull()
                lastVisible?.let {
                    val lastVisibleIndex = it.index
                    if (
                    // базоваое
//                        (lastVisibleIndex >= state.elements.lastIndex) &&
                    // идеальноек
                        (lastVisibleIndex >= state.elements.size - state.pageSize) &&
                        state.isLoadingMorePossible
                    ) {
                        proceedIntentAction(ITestIntent.LoadMoreData)
                    }
                }
            }
    }

    Scaffold { pv ->
        Box(modifier = Modifier.padding(pv)) {
            LazyColumn(
                state = listState
            ) {
                items(state.elements) { text ->
                    AppTextField(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(color = Color.Red),
                        value = text,
                        onValueChanged = {},
                        isEnabled = false
                    )
                }

                if (state.isLoading) {
                    item {
                        AppProgressBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                }
            }
        }
    }
}