package com.alenniboris.nba_app.presentation.activity

import android.os.Bundle
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
import com.alenniboris.nba_app.presentation.uikit.theme.NbaAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NbaAppTheme {
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

    val mainActivityVM = koinViewModel<MainActivityVM>()
    val isUserAuthenticated by mainActivityVM.userAuthenticationStatus.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(mainActivityVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
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