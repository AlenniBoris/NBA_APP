package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    textAlign: Alignment.Horizontal = Alignment.Start,
    pictureAlignment: Alignment.Horizontal = Alignment.Start,
    name: String? = "name",
    logoUrl: String? = "11",
    isPictureNeeded: Boolean = true
) {
    Column(
        modifier = modifier.padding(GameColumnItemPictureSectionVerticalMargin),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .align(textAlign),
            text = name ?: "Nan",
            style = bodyStyle.copy(
                fontSize = GameColumnItemPictureSectionTextSize
            ),
            color = categoryItemTextColor
        )

        if (isPictureNeeded){
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(pictureAlignment),
                model = logoUrl,
                placeholder = painterResource(Placeholder),
                error = painterResource(Placeholder),
                contentDescription = "Team picture"
            )
        }
    }
}