package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemPictureSectionTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemPictureSectionVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.Placeholder
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor

@Composable
@Preview
fun AppItemPictureSection(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    name: String? = "name",
    logoUrl: String? = "11",
) {
    Column(
        modifier = modifier.padding(GameColumnItemPictureSectionVerticalMargin)
    ) {
        Text(
            textAlign = textAlign,
            text = name ?: "Nan",
            style = bodyStyle.copy(
                fontSize = GameColumnItemPictureSectionTextSize
            ),
            color = categoryItemTextColor
        )

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = logoUrl,
            placeholder = painterResource(Placeholder),
            contentDescription = "Team picture"
        )
    }
}