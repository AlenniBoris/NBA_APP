package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.EmptyScreenFontSize
import com.alenniboris.nba_app.presentation.uikit.theme.EmptyScreenSpacerHeight
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.emptyScreenIconColor

@Composable
@Preview
fun AppAlertScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.danger),
                tint = emptyScreenIconColor,
                contentDescription = stringResource(R.string.alert_screen_icon_description)
            )
            Spacer(
                modifier = Modifier.height(EmptyScreenSpacerHeight)
            )
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.alert_screen_text),
                color = emptyScreenIconColor,
                style = bodyStyle.copy(
                    fontSize = EmptyScreenFontSize
                )
            )
        }

    }

}