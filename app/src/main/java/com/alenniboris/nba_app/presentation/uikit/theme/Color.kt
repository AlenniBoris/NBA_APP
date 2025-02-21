package com.alenniboris.nba_app.presentation.uikit.theme

import androidx.compose.ui.graphics.Color


private val AppColorLight = Color(0xfff77e56)
private val AppColorDark = Color(0xff050300)

val appColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppColorLight
        true -> AppColorDark
    }


// CustomEnterValueField colors

private val EnterTextFieldColorLight = Color(0xff050300)
private val EnterTextFieldColorDark = Color(0xfff77e56)

val enterTextFieldColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EnterTextFieldColorLight
        true -> EnterTextFieldColorDark
    }

private val EnterTextFieldTextColorLight = Color(0xffffffff)
private val EnterTextFieldTextColorDark = Color(0xff050300)

val enterTextFieldTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EnterTextFieldTextColorLight
        true -> EnterTextFieldTextColorDark
    }


private val SelectedTextBackgroundColorLight = Color(0xfff77e56)
private val SelectedTextBackgroundColorDark = Color(0xffffffff)
val selectedTextBackgroundColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> SelectedTextBackgroundColorLight
        true -> SelectedTextBackgroundColorDark
    }


val selectedTextHandlesColor = Color(0xffffffff)

// AppTopBarColors
private val AppTopBarElementsColorLight = Color(0xff050300)
private val AppTopBarElementsColorDark = Color(0xfff77e56)
val appTopBarElementsColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppTopBarElementsColorLight
        true -> AppTopBarElementsColorDark
    }

// Dropdown menu item
private val DropdownItemColorLight = Color(0xff050300)
private val DropdownItemColorDark = Color(0xfff77e56)
val dropdownItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DropdownItemColorLight
        true -> DropdownItemColorDark
    }

private val DropdownItemTextColorLight = Color(0xffffffff)
private val DropdownItemTextColorDark = Color(0xff050300)
val dropdownItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DropdownItemTextColorLight
        true -> DropdownItemTextColorDark
    }

//Floating action button

private val FloatingActionButtonColorLight = Color(0xff050300)
private val FloatingActionButtonColorDark = Color(0xfff77e56)
val floatingActionButtonColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> FloatingActionButtonColorLight
        true -> FloatingActionButtonColorDark
    }

private val FloatingActionButtonTextColorLight = Color(0xffffffff)
private val FloatingActionButtonTextColorDark = Color(0xff050300)
val floatingActionButtonTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> FloatingActionButtonTextColorLight
        true -> FloatingActionButtonTextColorDark
    }

// Date picker

private val DatePickerFieldContainerColorLight = Color(0xff050300)
private val DatePickerFieldContainerColorDark = Color(0xfff77e56)

val datePickerFieldContainerColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DatePickerFieldContainerColorLight
        true -> DatePickerFieldContainerColorDark
    }

private val DatePickerFieldTextColorLight = Color(0xffffffff)
private val DatePickerFieldTextColorDark = Color(0xff050300)

val datePickerFieldTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DatePickerFieldTextColorLight
        true -> DatePickerFieldTextColorDark
    }


private val DatePickerFieldSelectedContainerColorLight = Color(0xffffffff).copy(0.5f)
private val DatePickerFieldSelectedContainerColorDark = Color(0xff050300).copy(0.5f)

val datePickerFieldSelectedContainerColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DatePickerFieldSelectedContainerColorLight
        true -> DatePickerFieldSelectedContainerColorDark
    }

private val DatePickerFieldSelectedTextColorLight = Color(0xff050300)
private val DatePickerFieldSelectedTextColorDark = Color(0xffffffff)

val datePickerFieldSelectedTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DatePickerFieldSelectedTextColorLight
        true -> DatePickerFieldSelectedTextColorDark
    }


private val DatePickerTodayContentColorLight = Color(0xffffffff)
private val DatePickerTodayContentColorDark = Color(0xff050300)
val datePickerTodayColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> DatePickerTodayContentColorLight
        true -> DatePickerTodayContentColorDark
    }

// CustomRowFilter

private val RowFilterContainerColorLight = Color(0xfff77e56)
private val RowFilterContainerColorDark = Color(0xff050300)
val rowFilterContainerColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> RowFilterContainerColorLight
        true -> RowFilterContainerColorDark
    }


private val RowFilterTextColorLight = Color(0xff050300)
private val RowFilterTextColorDark = Color(0xffffffff)
val rowFilterTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> RowFilterTextColorLight
        true -> RowFilterTextColorDark
    }


private val RowItemColorLight = Color(0xff050300)
private val RowItemColorDark = Color(0xfff77e56)
val rowItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> RowItemColorLight
        true -> RowItemColorDark
    }

private val RowItemTextColorLight = Color(0xffffffff)
private val RowItemTextColorDark = Color(0xff050300)
val rowItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> RowItemTextColorLight
        true -> RowItemTextColorDark
    }

val rowSelectedItemColor = Color(0xffffffff)
val rowSelectedItemTextColor = Color(0xff050300)

// Category item

private val CategoryItemColorLight = Color(0xff050300)
private val CategoryItemColorDark = Color(0xfff77e56)
val categoryItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> CategoryItemColorLight
        true -> CategoryItemColorDark
    }

private val CategoryItemTextColorLight = Color(0xffffffff)
private val CategoryItemTextColorDark = Color(0xff050300)
val categoryItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> CategoryItemTextColorLight
        true -> CategoryItemTextColorDark
    }

// Statuses
val GoodStatusColor = Color(0xff1fff00)
val NeutralStatusColor = Color(0xffff7100)
val BadStatusColor = Color(0xffff0000)

// Empty screen
private val EmptyScreenIconColorLight = Color(0xff050300)
private val EmptyScreenIconColorDark = Color(0xfff77e56)
val emptyScreenIconColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EmptyScreenIconColorLight
        true -> EmptyScreenIconColorDark
    }

// Pager
private val PagerSelectedColorLight = Color(0xffffffff)
private val PagerSelectedColorDark = Color(0xfff77e56)
val pagerSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> PagerSelectedColorLight
        true -> PagerSelectedColorDark
    }

private val PagerNotSelectedColorLight = Color(0xff050300)
private val PagerNotSelectedColorDark = Color(0xffffffff)
val pagerNotSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> PagerNotSelectedColorLight
        true -> PagerNotSelectedColorDark
    }