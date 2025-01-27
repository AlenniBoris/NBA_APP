package com.alenniboris.nba_app.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.IStatisticsModel
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayersInGameStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain
import com.alenniboris.nba_app.presentation.navigation.NavigationGraph
import com.alenniboris.nba_app.presentation.uikit.theme.NBA_APPTheme
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

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
//                    A()
                }
            }
        }
    }
}

@Composable
fun A() {
    val testModel = GameModelDomain(
        id = 391053,
        homeTeam = TeamModelDomain(
            id = 813
        ),
        visitorsTeam = TeamModelDomain(
            id = 2316
        )
    )
    val manager = koinInject<INbaApiManager>()


    var team by remember {
        mutableStateOf<CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain>?>(
            null
        )
    }
//    var team by remember { mutableStateOf<CustomResultModelDomain<List<IStatisticsModel>, NbaApiExceptionModelDomain>?>(null) }
    var players by remember {
        mutableStateOf<CustomResultModelDomain<PlayersInGameStatisticsModelDomain, NbaApiExceptionModelDomain>?>(
            null
        )
    }

    LaunchedEffect(Unit) {

//        team = manager.requestForTeamStatistics(
//            team = TeamModelDomain(id = 139),
//            league = LeagueModelDomain(id = 12),
//            season = SeasonModelDomain(
//                name = "2021-2022"
//            )
//        )

        team = manager.requestForTeamsStatisticsInGame(
            testModel
        )

        players = manager.requestForPlayersStatisticsInGame(
            testModel
        )
    }

    team?.let {
        when (it) {
            is CustomResultModelDomain.Error -> {
                Log.e("!!!", "Teams errors")
            }

            is CustomResultModelDomain.Success -> {
                val result = it.result

                Log.e(
                    "!!!", """home teams result =
                    |${result.homeTeamStatistics.teamId}
                    |--------------------------------
                """.trimMargin()
                )

                Log.e(
                    "!!!", """visitors teams result =
                    |${result.visitorsTeamStatistics.teamId}
                    |--------------------------------
                """.trimMargin()
                )
            }
        }
    }


    players?.let {
        when (it) {
            is CustomResultModelDomain.Error -> {
                Log.e("!!!", "players errors")
            }

            is CustomResultModelDomain.Success -> {

                val result = it.result

                result.homeTeamPlayersStatistics.forEach {
                    Log.e(
                        "!!!", """home players result =
                    |${it.teamId}
                    |${it.playerId}
                    |${it.playerName}
                    |--------------------------------
                """.trimMargin()
                    )
                }

                result.visitorTeamPlayersStatistics.forEach {
                    Log.e(
                        "!!!", """visitors players result =
                    |${it.teamId}
                    |${it.playerId}
                    |${it.playerName}
                    |--------------------------------
                """.trimMargin()
                    )
                }
            }
        }
    }


}

@Composable
private fun MainActivityShow() {

    val navController = rememberNavController()
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

    Scaffold { pv ->
        Box(modifier = Modifier.padding(pv)) {
            NavigationGraph(
                navController = navController,
                isUserAuthenticated = isUserAuthenticated
            )
        }

    }

}