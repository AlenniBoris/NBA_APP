package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.alenniboris.nba_app.presentation.mappers.toColorValue
import com.alenniboris.nba_app.presentation.mappers.toStringMessageForStatus
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionBoxSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionHorizontalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionTimeTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
@Preview
fun GameColumnItem(
    modifier: Modifier = Modifier,
    element: GameModelDomain? = null,
    onGameCardClicked: () -> Unit = {},
    onFollowGameButtonClicked: () -> Unit = {}
) {

    element?.let {

        val iconPainterRes = remember(element.isFollowed) {
            if (element.isFollowed) {
                R.drawable.icon_in_followed
            } else R.drawable.icon_not_in_followed
        }

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AppIconButton(
                isAnimated = true,
                onClick = onFollowGameButtonClicked,
                iconPainter = painterResource(iconPainterRes),
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
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    name = element.homeTeam.name,
                    logoUrl = element.homeTeam.logo
                )

                GameItemTextSection(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    dateOfTheGame = remember(element) {
                        element.dateOfTheGame.let {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(it)
                        }
                    },
                    beginningTimeOfTheGame = remember(element) {
                        element.dateOfTheGame.let {
                            SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                .format(it)
                        }
                    },
                    homeTeamScore = element.homeScores?.totalScore
                        ?: stringResource(R.string.nan_text),
                    visitorsTeamScore = element.visitorsScores?.totalScore
                        ?: stringResource(R.string.nan_text),
                    gameStatus = element.gameStatus
                )

                AppItemPictureSection(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    name = element.visitorsTeam.name,
                    logoUrl = element.visitorsTeam.logo,
                    textAlign = Alignment.End,
                    pictureAlignment = Alignment.End
                )

            }

        }

    }

}

@Composable
@Preview
private fun GameItemTextSection(
    modifier: Modifier = Modifier,
    dateOfTheGame: String = "1111",
    beginningTimeOfTheGame: String = "0000",
    homeTeamScore: String = "",
    visitorsTeamScore: String = "",
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
                text = homeTeamScore,
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
                text = visitorsTeamScore,
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

@Composable
@Preview
fun Preview(

) {

    Row(
        modifier = Modifier
            .padding(
                GameColumnItemVerticalMargin
            )
            .background(
                color = categoryItemColor,
                shape = GameColumnItemShape
            )

            .padding(GameColumnItemHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AppIconButton(
            isAnimated = true,
            onClick = { },
            iconPainter =
            painterResource(R.drawable.icon_in_followed),
            tint = categoryItemTextColor,
            contentDescription = stringResource(R.string.following_icon_description)
        )

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppItemPictureSection(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/leagues/12.png"
            )
            GameItemTextSection(
                modifier = Modifier.width(IntrinsicSize.Min),
                dateOfTheGame = "2020-22-22",
                beginningTimeOfTheGame = "12:00:00",
                homeTeamScore = "163",
                visitorsTeamScore = "98",
                gameStatus = GameStatus.Game_Finished
            )

            AppItemPictureSection(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                name = "Miami hewat",
                logoUrl = null,
                textAlign = Alignment.End,
                pictureAlignment = Alignment.End
            )
        }

    }


}