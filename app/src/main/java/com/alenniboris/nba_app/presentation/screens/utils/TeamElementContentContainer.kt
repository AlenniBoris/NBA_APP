package com.alenniboris.nba_app.presentation.screens.utils

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.Placeholder
import com.alenniboris.nba_app.presentation.uikit.theme.TeamColumnItemFlagSize
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection

@Composable
@Preview
fun TeamElementContentContainer(
    modifier: Modifier = Modifier,
    textColor: Color = categoryItemTextColor,
    element: TeamModelDomain = TeamModelDomain(),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppItemPictureSection(
            modifier = Modifier
                .weight(1f),
            name = element.name,
            logoUrl = element.logo
        )

        TeamItemTextSection(
            modifier = Modifier.weight(1f),
            isNationalText =
            (element.isNational)?.let {
                stringResource(
                    if (it) R.string.league_national_text
                    else R.string.league_not_national_text
                )
            } ?: stringResource(R.string.league_national_no_text_text),
            countryName = element.country?.name ?: stringResource(R.string.unknown_country_text),
            countryFlagUrl = element.country?.flag,
            textColor = textColor
        )

    }
}

@Composable
@Preview
private fun TeamItemTextSection(
    modifier: Modifier = Modifier,
    isNationalText: String = "",
    countryName: String = "Name",
    countryFlagUrl: String? = null,
    textColor: Color = categoryItemTextColor
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
                color = textColor
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
                text = isNationalText,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = textColor
            )
        }
    }

}