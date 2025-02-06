package com.alenniboris.nba_app.presentation.test_raamcosta

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.BDestination
import com.ramcosta.composedestinations.generated.destinations.CDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.dsl.module
import java.util.UUID

class TestRaamcostaSharedVM : ViewModel() {
    init {
        viewModelScope.launch {
            var a = 0
            while (isActive) {
                delay(1_000)
                a += 1
                Log.e("!!!", "$a")
            }
        }
    }
}

val testRaamcostaModule = module {
    viewModel<TestRaamcostaSharedVM> {
        TestRaamcostaSharedVM()
    }
}

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(
    noinline parameters: ParametersDefinition? = null
): T {
    val navGraphRoute = currentBackStackEntry?.destination?.parent?.route ?: return koinViewModel(
        parameters = parameters
    )
    val parentEntry = remember(currentBackStackEntry) {
        getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry, parameters = parameters)
}

object SlideTransition : DestinationStyle.Animated() {
    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
        { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(2_000)) }
    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
        { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(2_000)) }
    override val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
        { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(2_000)) }
    override val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
        { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(2_000)) }
}

object FadeTransition : DestinationStyle.Animated() {
    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
        { fadeIn(animationSpec = tween(2_000)) }
    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
        { fadeOut(animationSpec = tween(2_000)) }
}

@NavGraph<RootGraph>
annotation class NestedGraph

@Destination<RootGraph>(start = true)
@Composable
fun A(
    navigator: DestinationsNavigator,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "A screen")
        Button(onClick = { navigator.navigate(BDestination) }) {
            Text(text = "navigate to B")
        }
    }
}

@Destination<NestedGraph>(start = true, style = SlideTransition::class)
@Composable
fun B(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<CDestination, String>,
    navController: NavController
) {

    val sharedVM = navController.sharedViewModel<TestRaamcostaSharedVM>()


    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                Log.e("!!!", "String from C = ${result.value}")
            }

            is NavResult.Canceled -> {
                Log.e("!!!", "String from C = none")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "B screen")
        Row {
            Button(onClick = { navigator.popBackStack() }) {
                Text(text = "navigate to A")
            }
            Button(onClick = { navigator.navigate(CDestination) }) {
                Text(text = "navigate to C")
            }
        }
    }
}

@Destination<NestedGraph>(style = FadeTransition::class)
@Composable
fun C(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>,
    navController: NavController
) {

    val sharedVM = navController.sharedViewModel<TestRaamcostaSharedVM>()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "C screen")
        Column {
            Button(onClick = {
                resultNavigator.navigateBack(
                    result = UUID.randomUUID().toString()
                )
            }) {
                Text(text = "navigate to B with sending string")
            }
            Button(onClick = {
                resultNavigator.navigateBack()
            }) {
                Text(text = "navigate to B without sending string")
            }
            Button(onClick = { navigator.popBackStack(NavGraphs.nested, true) }) {
                Text(text = "clear nested graph")
            }
        }
    }
}

