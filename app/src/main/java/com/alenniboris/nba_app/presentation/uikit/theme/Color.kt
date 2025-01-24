package com.alenniboris.nba_app.presentation.uikit.theme

import androidx.compose.ui.graphics.Color


private val AppColorLight = Color(0xfff77e56)
private val AppColorDark = Color(0xff050300)

val appColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> AppColorLight
        ThemeChooser.ThemeMode.DARK -> AppColorDark
    }


// CustomEnterValueField colors

private val EnterTextFieldColorLight = Color(0xff050300)
private val EnterTextFieldColorDark = Color(0xfff77e56)

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


private val SelectedTextBackgroundColorLight = Color(0xfff77e56)
private val SelectedTextBackgroundColorDark = Color(0xffffffff)
val selectedTextBackgroundColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> SelectedTextBackgroundColorLight
        ThemeChooser.ThemeMode.DARK -> SelectedTextBackgroundColorDark
    }


val selectedTextHandlesColor = Color(0xffffffff)

// AppTopBarColors
private val AppTopBarElementsColorLight = Color(0xff050300)
private val AppTopBarElementsColorDark = Color(0xfff77e56)
val appTopBarElementsColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> AppTopBarElementsColorLight
        ThemeChooser.ThemeMode.DARK -> AppTopBarElementsColorDark
    }

// Dropdown menu item
private val DropdownItemColorLight = Color(0xff050300)
private val DropdownItemColorDark = Color(0xfff77e56)
val dropdownItemColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DropdownItemColorLight
        ThemeChooser.ThemeMode.DARK -> DropdownItemColorDark
    }

private val DropdownItemTextColorLight = Color(0xffffffff)
private val DropdownItemTextColorDark = Color(0xff050300)
val dropdownItemTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DropdownItemTextColorLight
        ThemeChooser.ThemeMode.DARK -> DropdownItemTextColorDark
    }

//Floating action button

private val FloatingActionButtonColorLight = Color(0xff050300)
private val FloatingActionButtonColorDark = Color(0xfff77e56)
val floatingActionButtonColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> FloatingActionButtonColorLight
        ThemeChooser.ThemeMode.DARK -> FloatingActionButtonColorDark
    }

private val FloatingActionButtonTextColorLight = Color(0xffffffff)
private val FloatingActionButtonTextColorDark = Color(0xff050300)
val floatingActionButtonTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> FloatingActionButtonTextColorLight
        ThemeChooser.ThemeMode.DARK -> FloatingActionButtonTextColorDark
    }

// Date picker

private val DatePickerFieldContainerColorLight = Color(0xff050300)
private val DatePickerFieldContainerColorDark = Color(0xfff77e56)

val datePickerFieldContainerColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DatePickerFieldContainerColorLight
        ThemeChooser.ThemeMode.DARK -> DatePickerFieldContainerColorDark
    }

private val DatePickerFieldTextColorLight = Color(0xffffffff)
private val DatePickerFieldTextColorDark = Color(0xff050300)

val datePickerFieldTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DatePickerFieldTextColorLight
        ThemeChooser.ThemeMode.DARK -> DatePickerFieldTextColorDark
    }


private val DatePickerFieldSelectedContainerColorLight = Color(0xffffffff).copy(0.5f)
private val DatePickerFieldSelectedContainerColorDark = Color(0xff050300).copy(0.5f)

val datePickerFieldSelectedContainerColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DatePickerFieldSelectedContainerColorLight
        ThemeChooser.ThemeMode.DARK -> DatePickerFieldSelectedContainerColorDark
    }

private val DatePickerFieldSelectedTextColorLight = Color(0xff050300)
private val DatePickerFieldSelectedTextColorDark = Color(0xffffffff)

val datePickerFieldSelectedTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DatePickerFieldSelectedTextColorLight
        ThemeChooser.ThemeMode.DARK -> DatePickerFieldSelectedTextColorDark
    }


private val DatePickerTodayContentColorLight = Color(0xffffffff)
private val DatePickerTodayContentColorDark = Color(0xff050300)
val datePickerTodayColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> DatePickerTodayContentColorLight
        ThemeChooser.ThemeMode.DARK -> DatePickerTodayContentColorDark
    }

// CustomRowFilter

private val RowFilterContainerColorLight = Color(0xfff77e56)
private val RowFilterContainerColorDark = Color(0xff050300)
val rowFilterContainerColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> RowFilterContainerColorLight
        ThemeChooser.ThemeMode.DARK -> RowFilterContainerColorDark
    }


private val RowFilterTextColorLight = Color(0xff050300)
private val RowFilterTextColorDark = Color(0xffffffff)
val rowFilterTextColor
    get() = when (ThemeChooser.themeMode.value) {
        ThemeChooser.ThemeMode.LIGHT -> RowFilterTextColorLight
        ThemeChooser.ThemeMode.DARK -> RowFilterTextColorDark
    }


private val RowItemColorLight = Color(0xff050300)
private val RowItemColorDark = Color(0xfff77e56)
val rowItemColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> RowItemColorLight
        ThemeChooser.ThemeMode.DARK -> RowItemColorDark
    }

private val RowItemTextColorLight = Color(0xffffffff)
private val RowItemTextColorDark = Color(0xff050300)
val rowItemTextColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> RowItemTextColorLight
        ThemeChooser.ThemeMode.DARK -> RowItemTextColorDark
    }

val rowSelectedItemColor = Color(0xffffffff)
val rowSelectedItemTextColor = Color(0xff050300)

// Category item

private val CategoryItemColorLight = Color(0xff050300)
private val CategoryItemColorDark = Color(0xfff77e56)
val categoryItemColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> CategoryItemColorLight
        ThemeChooser.ThemeMode.DARK -> CategoryItemColorDark
    }

private val CategoryItemTextColorLight = Color(0xffffffff)
private val CategoryItemTextColorDark = Color(0xff050300)
val categoryItemTextColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> CategoryItemTextColorLight
        ThemeChooser.ThemeMode.DARK -> CategoryItemTextColorDark
    }

// Statuses
val GoodStatusColor = Color(0xff1fff00)
val NeutralStatusColor = Color(0xffff7100)
val BadStatusColor = Color(0xffff0000)

// Empty screen
private val EmptyScreenIconColorLight = Color(0xff050300)
private val EmptyScreenIconColorDark = Color(0xfff77e56)
val emptyScreenIconColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> EmptyScreenIconColorLight
        ThemeChooser.ThemeMode.DARK -> EmptyScreenIconColorDark
    }

// Pager
private val PagerSelectedColorLight = Color(0xffffffff)
private val PagerSelectedColorDark = Color(0xfff77e56)
val pagerSelectedColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> PagerSelectedColorLight
        ThemeChooser.ThemeMode.DARK -> PagerSelectedColorDark
    }

private val PagerNotSelectedColorLight = Color(0xff050300)
private val PagerNotSelectedColorDark = Color(0xffffffff)
val pagerNotSelectedColor
    get() = when(ThemeChooser.themeMode.value){
        ThemeChooser.ThemeMode.LIGHT -> PagerNotSelectedColorLight
        ThemeChooser.ThemeMode.DARK -> PagerNotSelectedColorDark
    }