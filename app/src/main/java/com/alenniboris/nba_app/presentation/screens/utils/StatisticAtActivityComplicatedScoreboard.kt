package com.alenniboris.nba_app.presentation.screens.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader

@Composable
@Preview
fun StatisticAtActivityComplicatedScoreboard(
    modifier: Modifier = Modifier,
    typeOfActivityText: String = "goals",
    isFirstActivityExists: Boolean = true,
    firstActivityText: String = "first",
    firstActivityCount: Int? = 0,
    isSecondActivityExists: Boolean = true,
    secondActivityText: String = "second",
    secondActivityCount: Int? = 0,
    isThirdActivityExists: Boolean = true,
    thirdActivityText: String = "third",
    thirdActivityCount: Int? = 0,
    textColor: Color = rowItemTextColor
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center
    ) {
        AppDividerWithHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = typeOfActivityText,
            insidesColor = textColor
        )

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (isFirstActivityExists){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = firstActivityText,
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = firstActivityCount?.toString() ?: stringResource(R.string.nan_text),
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                }
            }
            if(isSecondActivityExists){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = secondActivityText,
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = secondActivityCount?.toString() ?: stringResource(R.string.nan_text),
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                }
            }
            if (isThirdActivityExists){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = thirdActivityText,
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = thirdActivityCount?.toString() ?: stringResource(R.string.nan_text),
                        color = textColor,
                        style = bodyStyle.copy(
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }

    }

}