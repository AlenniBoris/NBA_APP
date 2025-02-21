package com.alenniboris.nba_app.presentation.screens.settings.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.AppTheme
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPageCircleSize
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPagePadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerNotSelectedColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSectionPadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSelectedColor
import com.alenniboris.nba_app.presentation.uikit.theme.setTheme
import com.alenniboris.nba_app.presentation.uikit.theme.settingsCardHeight
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader

@Composable
@Preview
fun ThemeSettingsPagerSection(
    modifier: Modifier = Modifier,
    currentTheme: AppTheme = AppTheme.SYSTEM,
    elements: List<AppTheme> = emptyList(),
) {
    Column(
        modifier = modifier
    ) {
        AppDividerWithHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = stringResource(R.string.themes_settings_header),
            insidesColor = appTopBarElementsColor
        )

        ThemePagerSectionCards(
            modifier = Modifier
                .padding(pagerSectionPadding)
                .fillMaxWidth(),
            currentTheme = currentTheme,
            list = elements,
        )
    }
}

@Composable
@Preview
private fun ThemePagerSectionCards(
    modifier: Modifier = Modifier,
    currentTheme: AppTheme = AppTheme.SYSTEM,
    list: List<AppTheme> = emptyList(),
) {

    val pagerState = rememberPagerState(
        initialPage = list.indexOf(currentTheme),
        pageCount = { list.size }
    )

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
        ) {
            list.getOrNull(it)?.let { el ->
                ThemePagerItem(
                    element = el,
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
private fun ThemePagerItem(
    element: AppTheme = AppTheme.SYSTEM,
) {
    val isSystemDark = isSystemInDarkTheme()
    val context = LocalContext.current
    SettingsPagerItem(
        content = { modifier ->
            Icon(
                modifier = modifier,
                painter = painterResource(
                    when (element) {
                        AppTheme.LIGHT -> R.drawable.light_theme_icon
                        AppTheme.DARK -> R.drawable.dark_theme_icon
                        AppTheme.SYSTEM -> R.drawable.system_theme_icon
                    }
                ),
                tint = categoryItemTextColor,
                contentDescription = ""
            )
        },
        onClick = {
            context.setTheme(element, isSystemDark)
        }
    )
}