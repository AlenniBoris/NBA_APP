package com.alenniboris.nba_app.presentation.screens.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemCountryTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNameTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNumberTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemPositionTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor

@Composable
@Preview
fun PlayerElementContentContainer(
    modifier: Modifier = Modifier,
    element: PlayerModelDomain = PlayerModelDomain(),
    textColor: Color = categoryItemTextColor
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = element.name,
                style = bodyStyle.copy(
                    fontSize = PlayerItemNameTextSize
                ),
                color = textColor
            )
            Text(
                text = element.position,
                style = bodyStyle.copy(
                    fontSize = PlayerItemPositionTextSize
                ),
                color = textColor
            )
            Text(
                text = element.country,
                style = bodyStyle.copy(
                    fontSize = PlayerItemCountryTextSize
                ),
                color = textColor
            )
        }
        Box {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = element.number,
                style = bodyStyle.copy(
                    fontSize = PlayerItemNumberTextSize
                ),
                color = textColor
            )
        }
    }
}