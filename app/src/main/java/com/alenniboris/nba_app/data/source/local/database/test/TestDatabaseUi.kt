package com.alenniboris.nba_app.data.source.local.database.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun TestDatabaseUi() {

    val testVM: TestVM = koinViewModel<TestVM>()

    val elements by testVM.elementsState.collectAsStateWithLifecycle()

    Scaffold { pv ->
        Column(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(elements) { element ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(all = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            Text(
                                text = "b -> w = ${element.w}",
                                color = Color.White
                            )
                            Text(
                                text = "c = ${element.c}",
                                color = Color.White
                            )
                            Text(
                                text = "d = ${element.d}",
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                testVM.delete(element)
                            }
                        ) {
                            Text(
                                text = "delete",
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    testVM.add(
                        TestEntity(
                            id = System.currentTimeMillis().toString()
                        )
                    )
                }
            ) {
                Text(
                    text = "Add"
                )
            }
        }
    }



}