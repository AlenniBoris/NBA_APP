package com.alenniboris.nba_app.presentation.uikit.theme

import com.alenniboris.nba_app.R

val EnterScreenPicture
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> R.drawable.basketball_pl_light
        ThemeChooser.ThemeMode.DARK -> R.drawable.basketball_pl_dark
    }

val PasswordShowPicture
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> R.drawable.password_opened_light_theme
        ThemeChooser.ThemeMode.DARK -> R.drawable.password_opened_dark_theme
    }

val PasswordHidePicture
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> R.drawable.password_closed_light_theme
        ThemeChooser.ThemeMode.DARK -> R.drawable.password_closed_dark_theme
    }