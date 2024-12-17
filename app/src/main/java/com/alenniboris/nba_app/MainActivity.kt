package com.alenniboris.nba_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alenniboris.nba_app.presentation.navigation.NavigationGraph
import com.alenniboris.nba_app.presentation.uikit.theme.NBA_APPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBA_APPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityShow()
                }
            }
        }
    }
}

@Composable
private fun MainActivityShow() {

    val navController = rememberNavController()
    val isUserAuthenticated: Boolean = false

    Scaffold { pv ->
        Box(modifier = Modifier.padding(pv)){
            NavigationGraph(
                navController = navController,
                isUserAuthenticated = isUserAuthenticated
            )
        }

    }

}