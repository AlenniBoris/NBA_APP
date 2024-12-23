package com.alenniboris.nba_app.presentation.screens.games

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun GamesScreen() {

    val manager: IAuthenticationManager = koinInject<IAuthenticationManager>()
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize()
    ) {
        Text("Games")

        Button(
            onClick = {
                coroutineScope.launch {
                    manager.signOut()
                }
            }
        ) {
            Text("Sign out")
        }
    }

}