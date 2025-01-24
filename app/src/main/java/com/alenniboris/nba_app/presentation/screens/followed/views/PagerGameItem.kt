package com.alenniboris.nba_app.presentation.screens.followed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.alenniboris.nba_app.presentation.screens.followed.IFollowedScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionHorizontalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionTimeTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
@Preview
fun PagerGameItem(
    modifier: Modifier = Modifier,
    element: GameModelDomain = GameModelDomain(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {

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
            onClick = {
                proceedIntentAction(
                    IFollowedScreenUpdateIntent.proceedRemovingAction(element)
                )
            },
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
                    .wrapContentHeight()
                    .weight(1f),
                name = element.homeTeam.name,
                isPictureNeeded = false
            )

            PagerGameItemTextSection(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
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
                }
            )

            AppItemPictureSection(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                name = element.visitorsTeam.name,
                textAlign = Alignment.End,
                isPictureNeeded = false
            )

        }

    }

}

@Composable
fun PagerGameItemTextSection(
    modifier: Modifier = Modifier,
    dateOfTheGame: String = "",
    beginningTimeOfTheGame: String = ""
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        Text(
            modifier = Modifier.padding(GameColumnItemTextSectionHorizontalRowPadding),
            text = stringResource(R.string.versus_text),
            style = bodyStyle.copy(
                fontSize = GameColumnItemTextSectionMainTextSize
            ),
            color = categoryItemTextColor
        )

    }
}
