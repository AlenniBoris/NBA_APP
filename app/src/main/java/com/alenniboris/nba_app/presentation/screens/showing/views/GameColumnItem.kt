package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.presentation.screens.utils.GameElementContentContainer
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton

@Composable
@Preview
fun GameColumnItem(
    modifier: Modifier = Modifier,
    element: GameModelDomain? = null,
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

            GameElementContentContainer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                element = element
            )

        }

    }

}