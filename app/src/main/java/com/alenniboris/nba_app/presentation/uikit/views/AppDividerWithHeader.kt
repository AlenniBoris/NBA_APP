package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDividerHeight
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDividerStartPadding
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDividerWidth
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.rowFilterTextColor

@Composable
@Preview
fun AppDividerWithHeader(
    headerText: String = ""
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .width(CustomDividerWidth)
                .height(CustomDividerHeight)
                .background(rowFilterTextColor)
        )
        Text(
            modifier = Modifier.padding(CustomDividerStartPadding),
            text = headerText,
            color = rowFilterTextColor,
            style = bodyStyle.copy(
                fontSize = 20.sp
            )
        )
        Spacer(
            modifier = Modifier
                .padding(CustomDividerStartPadding)
                .fillMaxWidth()
                .height(CustomDividerHeight)
                .background(rowFilterTextColor)
        )
    }
}