package com.alenniboris.nba_app.presentation.uikit.theme

import androidx.compose.ui.graphics.Color


private val AppColorLight = Color(0xfff77e56)
private val AppColorDark = Color(0xff050300)

val appColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> AppColorLight
        ThemeChooser.ThemeMode.DARK -> AppColorDark
    }

private val EnterTextFieldColorLight = Color(0xff050300)
private val EnterTextFieldColorDark = Color(0xffffffff)

val enterTextFieldColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> EnterTextFieldColorLight
        ThemeChooser.ThemeMode.DARK -> EnterTextFieldColorDark
    }

private val EnterTextFieldTextColorLight = Color(0xffffffff)
private val EnterTextFieldTextColorDark = Color(0xff050300)

val enterTextFieldTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> EnterTextFieldTextColorLight
        ThemeChooser.ThemeMode.DARK -> EnterTextFieldTextColorDark
    }


val SelectedTextBackgroundColor = Color(0xfff77e56)

private val SelectedTextHandlesColorLight = Color(0xffffffff)
private val SelectedTextHandlesColorDark = Color(0xfff77e56)

val selectedTextHandlesColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> SelectedTextHandlesColorLight
        ThemeChooser.ThemeMode.DARK -> SelectedTextHandlesColorDark
    }

