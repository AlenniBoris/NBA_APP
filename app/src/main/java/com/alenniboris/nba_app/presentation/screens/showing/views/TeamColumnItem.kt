package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.model.TeamUiModel
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.Placeholder
import com.alenniboris.nba_app.presentation.uikit.theme.TeamColumnItemFlagSize
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection

@Composable
@Preview
fun TeamColumnItem(
    modifier: Modifier = Modifier,
    element: TeamUiModel = TeamUiModel(),
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
            iconPainter = if (isElementFollowed) painterResource(R.drawable.icon_in_followed)
            else painterResource(R.drawable.icon_not_in_followed),
            replacementPainter = if (isElementFollowed) painterResource(R.drawable.icon_not_in_followed)
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
                name = element.name,
                logoUrl = element.logoUrl
            )

            TeamItemTextSection(
                modifier = Modifier.weight(1f),
                isNational = element.national,
                countryName = element.country.name ?: stringResource(R.string.nan_text),
                countryFlagUrl = element.country.flag
            )

        }

    }

}

@Composable
@Preview
private fun TeamItemTextSection(
    modifier: Modifier = Modifier,
    isNational: Boolean = false,
    countryName: String = "Name",
    countryFlagUrl: String? = null
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = countryName,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = categoryItemTextColor
            )

            AsyncImage(
                modifier = Modifier
                    .width(TeamColumnItemFlagSize)
                    .height(TeamColumnItemFlagSize),
                model = countryFlagUrl,
                placeholder = painterResource(Placeholder),
                contentDescription = stringResource(R.string.team_picture_description)
            )
        }

        Row(
            modifier = Modifier.padding(GameColumnItemTextSectionVerticalRowPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionStartTextPadding),
                text = if (isNational) stringResource(R.string.league_national_text)
                else stringResource(R.string.league_not_national_text),
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = categoryItemTextColor
            )
        }
    }

}