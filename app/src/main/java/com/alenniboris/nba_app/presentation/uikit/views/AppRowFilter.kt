package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterFirstPossibleVariantPadding
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterItemTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterItemTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterPossibleVariantPadding
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.rowFilterContainerColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowSelectedItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowSelectedItemTextColor

@Composable
@Preview
fun AppRowFilter(
    modifier: Modifier = Modifier,
    itemsLazyListState: LazyListState = rememberLazyListState(),
    headerText: String = "Header",
    elements: List<ActionImplementedUiModel> = emptyList(),
    currentSelectedElement: ActionImplementedUiModel = ActionImplementedUiModel(),
) {

    LaunchedEffect(key1 = currentSelectedElement) {
        val index = elements.indexOfFirst { it.name == currentSelectedElement.name }
        if (index >= 0) {
            itemsLazyListState.animateScrollToItem(index)
        }
    }

    Column(
        modifier = modifier
    ) {
        AppDividerWithHeader(headerText = headerText)

        LazyRow(
            modifier = Modifier
                .padding(RowFilterTopPadding)
                .background(rowFilterContainerColor),
            state = itemsLazyListState
        ) {
            itemsIndexed(elements) { index, element ->
                RowFilterItem(
                    modifier = Modifier
                        .animateItem(fadeInSpec = null, fadeOutSpec = null)
                        .padding(
                            if (index == 0) RowFilterFirstPossibleVariantPadding
                            else RowFilterPossibleVariantPadding
                        ),
                    elementText = element.name,
                    itemBackgroundColor = if (element.name == currentSelectedElement.name) rowSelectedItemColor
                    else rowItemColor,
                    itemTextColor = if (element.name == currentSelectedElement.name) rowSelectedItemTextColor
                    else rowItemTextColor,
                    onItemClicked = { element.onClick() },
                )
            }
        }
    }
}

@Composable
@Preview
private fun RowFilterItem(
    modifier: Modifier = Modifier,
    elementText: String = "Item text",
    itemBackgroundColor: Color = rowItemColor,
    itemTextColor: Color = rowItemTextColor,
    onItemClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RowFilterItemShape)
            .background(itemBackgroundColor)
            .clickable(onClick = onItemClicked),
    ) {
        Text(
            modifier = Modifier.padding(RowFilterItemTextPadding),
            text = elementText,
            color = itemTextColor,
            fontSize = RowFilterItemTextSize
        )
    }
}