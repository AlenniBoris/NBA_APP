package com.alenniboris.nba_app.presentation.screens.followed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.presentation.screens.followed.IFollowedScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemPictureSectionTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton

@Composable
@Preview
fun PagerTeamItem(
    modifier: Modifier = Modifier,
    element: TeamModelDomain = TeamModelDomain(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val iconPainterRes = remember(element.isFollowed) {
            if (element.isFollowed) {
                R.drawable.icon_in_followed
            } else R.drawable.icon_not_in_followed
        }

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

            Text(
                modifier = Modifier
                    .weight(1f),
                text = element.name,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemPictureSectionTextSize
                ),
                color = categoryItemTextColor
            )

            PagerTeamItemTextSection(
                modifier = Modifier.weight(1f),
                isNationalText =
                (element.isNational)?.let {
                    stringResource(
                        if (it) R.string.league_national_text
                        else R.string.league_not_national_text
                    )
                } ?: stringResource(R.string.league_national_no_text_text),
                countryName = element.country?.name ?: stringResource(R.string.nan_text),
            )

        }

    }

}

@Composable
@Preview
private fun PagerTeamItemTextSection(
    modifier: Modifier = Modifier,
    isNationalText: String = "",
    countryName: String = "Name",
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = countryName,
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
            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionStartTextPadding),
                text = isNationalText,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = categoryItemTextColor
            )
        }
    }
}