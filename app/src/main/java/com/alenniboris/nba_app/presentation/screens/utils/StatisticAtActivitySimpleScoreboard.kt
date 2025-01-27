package com.alenniboris.nba_app.presentation.screens.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemTextColor

@Composable
@Preview
fun StatisticAtActivitySimpleScoreboard(
    modifier: Modifier = Modifier,
    activityText: String = "activityText",
    resultText: String = "resultText",
    textColor: Color = rowItemTextColor,
    fontSize: TextUnit = 15.sp
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = activityText,
            color = textColor,
            style = bodyStyle.copy(
                fontSize = fontSize
            )
        )

        Text(
            text = resultText,
            color = textColor,
            style = bodyStyle.copy(
                fontSize = fontSize
            )
        )
    }

}