package com.alenniboris.nba_app.presentation.screens.details.game.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamInGameStatisticsModelDomain
import com.alenniboris.nba_app.presentation.screens.utils.StatisticAtActivityComplicatedScoreboard
import com.alenniboris.nba_app.presentation.screens.utils.StatisticAtActivitySimpleScoreboard
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnShape
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementVerticalPadding

@Composable
@Preview
fun TeamStatisticsInGameUi(
    teamStatistics: TeamInGameStatisticsModelDomain = TeamInGameStatisticsModelDomain(),
) {
    Column(
        modifier = Modifier
            .padding(statisticsColumnTopPadding)
            .fillMaxHeight()
            .background(
                color = rowItemColor,
                shape = statisticsColumnShape
            )
            .padding(statisticsColumnHorizontalPadding),
    ) {

        val elementsModifier = Modifier
            .padding(statisticsElementTopPadding)
            .fillMaxWidth()
            .padding(statisticsElementHorizontalPadding)

        StatisticAtActivitySimpleScoreboard(
            modifier = elementsModifier,
            activityText = stringResource(R.string.assists_text),
            resultText = teamStatistics.assists?.toString()
                ?: stringResource(R.string.nan_text)
        )

        StatisticAtActivitySimpleScoreboard(
            modifier = elementsModifier,
            activityText = stringResource(R.string.steals_text),
            resultText = teamStatistics.steals?.toString()
                ?: stringResource(R.string.nan_text)
        )

        StatisticAtActivitySimpleScoreboard(
            modifier = elementsModifier,
            activityText = stringResource(R.string.blocks_text),
            resultText = teamStatistics.blocks?.toString()
                ?: stringResource(R.string.nan_text)
        )

        StatisticAtActivitySimpleScoreboard(
            modifier = elementsModifier,
            activityText = stringResource(R.string.turnovers_text),
            resultText = teamStatistics.turnovers?.toString()
                ?: stringResource(R.string.nan_text)
        )

        StatisticAtActivitySimpleScoreboard(
            modifier = elementsModifier,
            activityText = stringResource(R.string.personal_fouls_text),
            resultText = teamStatistics.personalFouls?.toString()
                ?: stringResource(R.string.nan_text)
        )

        StatisticAtActivityComplicatedScoreboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(statisticsElementVerticalPadding),
            typeOfActivityText = stringResource(R.string.field_goals_text),
            firstActivityText = stringResource(R.string.attempts_text),
            firstActivityCount = teamStatistics.fieldGoals?.attempts,
            secondActivityText = stringResource(R.string.total_text),
            secondActivityCount = teamStatistics.fieldGoals?.total,
            thirdActivityText = stringResource(R.string.percentage_text),
            thirdActivityCount = teamStatistics.fieldGoals?.percentage
        )

        StatisticAtActivityComplicatedScoreboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(statisticsElementVerticalPadding),
            typeOfActivityText = stringResource(R.string.three_point_goals_text),
            firstActivityText = stringResource(R.string.attempts_text),
            firstActivityCount = teamStatistics.threePointGoals?.attempts,
            secondActivityText = stringResource(R.string.total_text),
            secondActivityCount = teamStatistics.threePointGoals?.total,
            thirdActivityText = stringResource(R.string.percentage_text),
            thirdActivityCount = teamStatistics.threePointGoals?.percentage
        )

        StatisticAtActivityComplicatedScoreboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(statisticsElementVerticalPadding),
            typeOfActivityText = stringResource(R.string.free_throws_goals_text),
            firstActivityText = stringResource(R.string.attempts_text),
            firstActivityCount = teamStatistics.freeThrowsGoals?.attempts,
            secondActivityText = stringResource(R.string.total_text),
            secondActivityCount = teamStatistics.freeThrowsGoals?.total,
            thirdActivityText = stringResource(R.string.percentage_text),
            thirdActivityCount = teamStatistics.freeThrowsGoals?.percentage
        )

        StatisticAtActivityComplicatedScoreboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(statisticsElementVerticalPadding),
            typeOfActivityText = stringResource(R.string.rebounds_text),
            firstActivityText = stringResource(R.string.defence_text),
            firstActivityCount = teamStatistics.rebounds?.defense,
            secondActivityText = stringResource(R.string.offence_text),
            secondActivityCount = teamStatistics.rebounds?.offence,
            thirdActivityText = stringResource(R.string.total_text),
            thirdActivityCount = teamStatistics.rebounds?.total
        )

    }
}