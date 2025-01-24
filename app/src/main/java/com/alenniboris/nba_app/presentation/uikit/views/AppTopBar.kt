package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenHeaderTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.titleStyle

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    headerTextString: String = "",
    isLeftBtnAnimated: Boolean = false,
    leftBtnPainter: Painter? = null,
    onLeftBtnClicked: () -> Unit = {},
    isRightBtnAnimated: Boolean = false,
    rightBtnPainter: Painter? = null,
    onRightBtnClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier,
    ) {

        leftBtnPainter?.let {
            AppIconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                isAnimated = isLeftBtnAnimated,
                iconPainter = leftBtnPainter,
                onClick = onLeftBtnClicked,
                tint = appTopBarElementsColor,
                contentDescription = stringResource(R.string.top_bar_left_btn_description)
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = headerTextString,
            color = appTopBarElementsColor,
            style = titleStyle.copy(
                fontSize = TBShowingScreenHeaderTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        rightBtnPainter?.let {
            AppIconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                isAnimated = isRightBtnAnimated,
                iconPainter = rightBtnPainter,
                onClick = onRightBtnClicked,
                tint = appTopBarElementsColor,
                contentDescription = stringResource(R.string.top_bar_right_btn_description)
            )
        }

    }
}

@Composable
@Preview
fun UiPreview() {
    Column {
        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TBShowingScreenPadding),
            headerTextString = "hello",
            isLeftBtnAnimated = true,
            leftBtnPainter = painterResource(R.drawable.basketball_ball),
            rightBtnPainter = painterResource(R.drawable.icon_in_followed),
        )

        Spacer(Modifier.height(10.dp))

        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TBShowingScreenPadding),
            headerTextString = "hello",
            isLeftBtnAnimated = true,
            isRightBtnAnimated = true,
            leftBtnPainter = painterResource(R.drawable.basketball_ball),
            rightBtnPainter = painterResource(R.drawable.icon_in_followed)
        )

        Spacer(Modifier.height(10.dp))

        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TBShowingScreenPadding),
            headerTextString = "hello",
            isLeftBtnAnimated = true,
        )
    }
}