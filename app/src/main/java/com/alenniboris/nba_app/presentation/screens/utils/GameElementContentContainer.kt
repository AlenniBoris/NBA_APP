package com.alenniboris.nba_app.presentation.screens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
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
fun GameElementContentContainer(
    modifier: Modifier = Modifier,
    textColor: Color = categoryItemTextColor,
    element: GameModelDomain = GameModelDomain()
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween
        ) {
        Text(
            text = stringResource(R.string.home_team_text),
        color = textColor
        )
        AppItemPictureSection(
            modifier = Modifier
                .fillMaxHeight(),
        textColor = textColor,
        name = element.homeTeam.name,
        logoUrl = element.homeTeam.logo
        )
    }


        GameItemTextSection(
            modifier = Modifier.width(IntrinsicSize.Min),
            textColor = textColor,
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

        Column(
            modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween
        ) {
        Text(
            text = stringResource(R.string.visitors_team_text),
        color = textColor,
        modifier = Modifier.align(Alignment.End)
        )
        AppItemPictureSection(
            modifier = Modifier
                .fillMaxHeight(),
        textColor = textColor,
        name = element.visitorsTeam.name,
        logoUrl = element.visitorsTeam.logo,
        textAlign = Alignment.End,
        pictureAlignment = Alignment.End
        )

        }

    }

}

@Composable
@Preview
private fun GameItemTextSection(
    modifier: Modifier = Modifier,
    textColor: Color = categoryItemTextColor,
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
                color = textColor
            )
            Text(
                text = beginningTimeOfTheGame,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionTimeTextSize
                ),
                color = textColor
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
                color = textColor
            )

            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionHorizontalRowPadding),
                text = "-",
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = textColor
            )

            Text(
                text = visitorsTeamScore,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = textColor
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
                color = textColor
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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.home_team_text),
                    color = categoryItemTextColor
                )
                AppItemPictureSection(
                    modifier = Modifier
                        .fillMaxHeight(),
                    name = "element.homeTeam.name",
                    logoUrl = "element.homeTeam.logo"
                )
            }
            GameItemTextSection(
                modifier = Modifier.width(IntrinsicSize.Min),
                dateOfTheGame = "2020-22-22",
                beginningTimeOfTheGame = "12:00:00",
                homeTeamScore = "163",
                visitorsTeamScore = "98",
                gameStatus = GameStatus.Game_Finished
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.visitors_team_text),
                    color = categoryItemTextColor
                )
                AppItemPictureSection(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    name = "element.visitorsTeam.name",
                    logoUrl = "element.visitorsTeam.logo",
                    textAlign = Alignment.End,
                    pictureAlignment = Alignment.End
                )

            }
        }

    }

}