package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.model.PlayerUiModel
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemCountryTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNameTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNumberTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemPositionTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton

@Composable
@Preview
fun PlayerColumnItem(
    modifier: Modifier = Modifier,
    element: PlayerUiModel = PlayerUiModel(),
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
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = element.name,
                    style = bodyStyle.copy(
                        fontSize = PlayerItemNameTextSize
                    ),
                    color = categoryItemTextColor
                )
                Text(
                    text = element.position,
                    style = bodyStyle.copy(
                        fontSize = PlayerItemPositionTextSize
                    ),
                    color = categoryItemTextColor
                )
                Text(
                    text = element.country,
                    style = bodyStyle.copy(
                        fontSize = PlayerItemCountryTextSize
                    ),
                    color = categoryItemTextColor
                )
            }
            Box {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.number,
                    style = bodyStyle.copy(
                        fontSize = PlayerItemNumberTextSize
                    ),
                    color = categoryItemTextColor
                )
            }
        }

    }

}