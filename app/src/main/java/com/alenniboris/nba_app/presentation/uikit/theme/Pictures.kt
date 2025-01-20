package com.alenniboris.nba_app.presentation.uikit.theme

import com.alenniboris.nba_app.R

val EnterScreenPicture
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> R.drawable.basketball_pl_light
        ThemeChooser.ThemeMode.DARK -> R.drawable.basketball_pl_dark
    }

val PasswordShowPicture = R.drawable.password_opened_light_theme

val PasswordHidePicture = R.drawable.password_closed_light_theme

val TopBarDropdownMenuOpeningButton = R.drawable.basketball_ball

val Placeholder
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> R.drawable.ic_placeholder_light
        ThemeChooser.ThemeMode.DARK -> R.drawable.ic_placeholder_dark
    }