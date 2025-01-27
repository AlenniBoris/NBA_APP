package com.alenniboris.nba_app.presentation.screens.details.game.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.presentation.screens.utils.StatisticAtActivityComplicatedScoreboard
import com.alenniboris.nba_app.presentation.screens.utils.StatisticAtActivitySimpleScoreboard
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnShape
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementVerticalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsRowFontSize
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton

@Composable
@Preview
fun PlayersStatisticsInGameUi(
    playersStatisticsModelDomain: List<PlayerStatisticsModelDomain> = emptyList(),
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        playersStatisticsModelDomain.forEach { playerStatistics ->
            PlayerStatisticsCard(
                playerStatistics = playerStatistics
            )
        }
    }
}


@Composable
@Preview
fun PlayerStatisticsCard(
    playerStatistics: PlayerStatisticsModelDomain = PlayerStatisticsModelDomain()
) {
    var isStatsVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(statisticsColumnTopPadding)
            .fillMaxWidth()
            .background(
                color = rowItemColor,
                shape = statisticsColumnShape
            )
            .padding(statisticsColumnHorizontalPadding),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = playerStatistics.playerName,
                color = rowItemTextColor,
                style = bodyStyle.copy(
                    fontSize = statisticsRowFontSize
                )
            )

            AppIconButton(
                isAnimated = true,
                iconPainter = painterResource(
                    if (isStatsVisible) R.drawable.icon_close_details
                    else R.drawable.icon_open_details
                ),
                tint = rowItemTextColor,
                onClick = {
                    isStatsVisible = !isStatsVisible
                }
            )
        }
        AnimatedVisibility(
            visible = isStatsVisible,
            enter = slideInVertically(
                initialOffsetY = { 0 }
            ),
            exit = slideOutVertically(
                targetOffsetY = { 0 }
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()

            ) {

                val elementsModifier = Modifier
                    .padding(statisticsElementTopPadding)
                    .fillMaxWidth()
                    .padding(statisticsElementHorizontalPadding)

                StatisticAtActivitySimpleScoreboard(
                    modifier = elementsModifier,
                    activityText = stringResource(R.string.played_minutes_text),
                    resultText = playerStatistics.playedMinutes ?: stringResource(R.string.nan_text)
                )

                StatisticAtActivitySimpleScoreboard(
                    modifier = elementsModifier,
                    activityText = stringResource(R.string.assists_text),
                    resultText = playerStatistics.assists?.toString()
                        ?: stringResource(R.string.nan_text)
                )

                StatisticAtActivitySimpleScoreboard(
                    modifier = elementsModifier,
                    activityText = stringResource(R.string.points_text),
                    resultText = playerStatistics.earnedPoints?.toString()
                        ?: stringResource(R.string.nan_text)
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.field_goals_text),
                    firstActivityText = stringResource(R.string.attempts_text),
                    firstActivityCount = playerStatistics.fieldGoals?.attempts,
                    secondActivityText = stringResource(R.string.total_text),
                    secondActivityCount = playerStatistics.fieldGoals?.total,
                    thirdActivityText = stringResource(R.string.percentage_text),
                    thirdActivityCount = playerStatistics.fieldGoals?.percentage
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.three_point_goals_text),
                    firstActivityText = stringResource(R.string.attempts_text),
                    firstActivityCount = playerStatistics.threePointGoals?.attempts,
                    secondActivityText = stringResource(R.string.total_text),
                    secondActivityCount = playerStatistics.threePointGoals?.total,
                    thirdActivityText = stringResource(R.string.percentage_text),
                    thirdActivityCount = playerStatistics.threePointGoals?.percentage
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.free_throws_goals_text),
                    firstActivityText = stringResource(R.string.attempts_text),
                    firstActivityCount = playerStatistics.freeThrowsGoals?.attempts,
                    secondActivityText = stringResource(R.string.total_text),
                    secondActivityCount = playerStatistics.freeThrowsGoals?.total,
                    thirdActivityText = stringResource(R.string.percentage_text),
                    thirdActivityCount = playerStatistics.freeThrowsGoals?.percentage
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.rebounds_text),
                    firstActivityText = stringResource(R.string.defence_text),
                    firstActivityCount = playerStatistics.rebounds?.defense,
                    secondActivityText = stringResource(R.string.offence_text),
                    secondActivityCount = playerStatistics.rebounds?.offence,
                    thirdActivityText = stringResource(R.string.total_text),
                    thirdActivityCount = playerStatistics.rebounds?.total
                )
            }
        }
    }

}

