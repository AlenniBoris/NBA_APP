package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.utils.EnumValues.GameStatus
import com.alenniboris.nba_app.presentation.mappers.toColorValue
import com.alenniboris.nba_app.presentation.mappers.toStringMessageForStatus
import com.alenniboris.nba_app.presentation.model.GameUiModel
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionBoxSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionHorizontalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionTimeTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection

@Composable
@Preview
fun GameColumnItem(
    modifier: Modifier = Modifier,
    element: GameUiModel = GameUiModel(),
    isElementFollowed: Boolean = false,
    onGameCardClicked: () -> Unit = {},
    onFollowGameButtonClicked: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AppIconButton(
            isAnimated = true,
            isReplaceable = true,
            onClick = onFollowGameButtonClicked,
            iconPainter =
            if (isElementFollowed) painterResource(R.drawable.icon_in_followed)
            else painterResource(R.drawable.icon_not_in_followed),
            replacementPainter =
            if (isElementFollowed) painterResource(R.drawable.icon_not_in_followed)
            else painterResource(R.drawable.icon_in_followed),
            tint = categoryItemTextColor,
            contentDescription = stringResource(R.string.following_icon_description)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppItemPictureSection(
                modifier = Modifier.weight(1f),
                name = element.homeTeam.name,
                logoUrl = element.homeTeam.logoUrl
            )

            GameItemTextSection(
                modifier = Modifier.weight(1f),
                dateOfTheGame = element.date ?: stringResource(R.string.nan_text),
                beginningTimeOfTheGame = element.time ?: stringResource(R.string.nan_text),
                homeTeamScore = element.homeScores.totalScore,
                visitorsTeamScore = element.visitorsScores.totalScore,
                gameStatus = element.status
            )

            AppItemPictureSection(
                modifier = Modifier.weight(1f),
                name = element.visitorsTeam.name,
                logoUrl = element.visitorsTeam.logoUrl,
                textAlign = TextAlign.End
            )

        }

    }

}

@Composable
@Preview
private fun GameItemTextSection(
    modifier: Modifier = Modifier,
    dateOfTheGame: String = "1111",
    beginningTimeOfTheGame: String = "0000",
    homeTeamScore: Int = 1,
    visitorsTeamScore: Int = 2,
    gameStatus: GameStatus = GameStatus.In_Play
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(GameColumnItemTextSectionVerticalMargin),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dateOfTheGame,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = categoryItemTextColor
            )
            Text(
                text = beginningTimeOfTheGame,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionTimeTextSize
                ),
                color = categoryItemTextColor
            )
        }

        Row(
            modifier = Modifier.padding(GameColumnItemTextSectionVerticalRowPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = homeTeamScore.toString(),
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = categoryItemTextColor
            )

            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionHorizontalRowPadding),
                text = "-",
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = categoryItemTextColor
            )

            Text(
                text = visitorsTeamScore.toString(),
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = categoryItemTextColor
            )
        }

        Row(
            modifier = Modifier.padding(GameColumnItemTextSectionVerticalRowPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = gameStatus.toColorValue(),
                        shape = CircleShape
                    )

                    .width(GameColumnItemTextSectionBoxSize)
                    .height(GameColumnItemTextSectionBoxSize)
            )

            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionStartTextPadding),
                text = stringResource(gameStatus.toStringMessageForStatus()),
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = categoryItemTextColor
            )
        }

    }

}