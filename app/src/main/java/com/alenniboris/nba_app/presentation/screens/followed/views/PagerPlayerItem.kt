package com.alenniboris.nba_app.presentation.screens.followed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.presentation.screens.followed.IFollowedScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemCountryTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNameTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemNumberTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PlayerItemPositionTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton

@Composable
@Preview
fun PagerPlayerItem(
    modifier: Modifier = Modifier,
    element: PlayerModelDomain = PlayerModelDomain(),
    onFollowedBtnClicked: () -> Unit = {}
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
            onClick = onFollowedBtnClicked,
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
                    color = categoryItemTextColor
                )
                Text(
                    text = element.position ?: stringResource(R.string.nan_text),
                    style = bodyStyle.copy(
                        fontSize = PlayerItemPositionTextSize
                    ),
                    color = categoryItemTextColor
                )
                Text(
                    text = element.country ?: stringResource(R.string.nan_text),
                    style = bodyStyle.copy(
                        fontSize = PlayerItemCountryTextSize
                    ),
                    color = categoryItemTextColor
                )
            }
            Box {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.number ?: stringResource(R.string.nan_text),
                    style = bodyStyle.copy(
                        fontSize = PlayerItemNumberTextSize
                    ),
                    color = categoryItemTextColor
                )
            }
        }

    }

}