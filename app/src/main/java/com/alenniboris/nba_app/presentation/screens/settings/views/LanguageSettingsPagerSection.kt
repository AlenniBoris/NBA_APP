package com.alenniboris.nba_app.presentation.screens.settings.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.AppLanguage
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPageCircleSize
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPagePadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerNotSelectedColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSectionPadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSelectedColor
import com.alenniboris.nba_app.presentation.uikit.theme.setLanguage
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader

@Composable
@Preview
fun LanguageSettingsPagerSection(
    modifier: Modifier = Modifier,
    currentLanguage: AppLanguage = AppLanguage.English,
    elements: List<AppLanguage> = emptyList(),
) {
    Column(
        modifier = modifier
    ) {
        AppDividerWithHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = stringResource(R.string.languages_settings_header),
            insidesColor = appTopBarElementsColor
        )

        LanguagePagerSectionCards(
            modifier = Modifier
                .padding(pagerSectionPadding)
                .fillMaxWidth(),
            currentLanguage = currentLanguage,
            list = elements,
        )
    }
}

@Composable
@Preview
private fun LanguagePagerSectionCards(
    modifier: Modifier = Modifier,
    currentLanguage: AppLanguage = AppLanguage.English,
    list: List<AppLanguage> = emptyList(),
) {

    val pagerState = rememberPagerState(
        initialPage = list.indexOf(currentLanguage),
        pageCount = { list.size }
    )

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
        ) {
            list.getOrNull(it)?.let { el ->
                LanguagePagerItem(
                    element = el
                )
            }
        }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) pagerSelectedColor else pagerNotSelectedColor
                Box(
                    modifier = Modifier
                        .padding(pagerCurrentPagePadding)
                        .clip(CircleShape)
                        .background(
                            animateColorAsState(
                                color,
                                tween(800),
                                label = ""
                            ).value
                        )
                        .size(pagerCurrentPageCircleSize)
                )
            }
        }
    }

}

@Composable
@Preview
private fun LanguagePagerItem(
    element: AppLanguage = AppLanguage.English,
) {
    val context = LocalContext.current
    SettingsPagerItem(
        content = { modifier ->
            Text(
                modifier = modifier,
                text = element.langString,
                color = categoryItemTextColor
            )
        },
        onClick = {
            context.setLanguage(element)
        }
    )
}