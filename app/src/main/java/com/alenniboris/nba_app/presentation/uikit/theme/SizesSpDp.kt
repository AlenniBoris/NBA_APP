package com.alenniboris.nba_app.presentation.uikit.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// EnterValueTextField
val EnterValueTextFieldTextSize = 20.sp
val EnterValueTextFieldInnerBoxPadding = PaddingValues(all = 4.dp)
val EnterValueTextFieldTopPaddingInPlayersFilter = PaddingValues(top = 10.dp)

// EnterScreen
val ESColumnPadding = PaddingValues(all = 20.dp)
val ESSpacerHeight = 15.dp
val ESSpacerHeightDouble = ESSpacerHeight * 2
val ESCustomTextFieldShape = RoundedCornerShape(percent = 20)
val ESCustomTextFieldPadding = PaddingValues(all = 10.dp)

// TopBar
val TBShowingScreenPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp)
val TBShowingScreenHeaderTextSize = 30.sp
val TopBarUIBoxEndPadding = PaddingValues(end = 75.dp)

// CustomDropdownMenu
val DropDownMenuItemCornerShape = RoundedCornerShape(10.dp)
val CustomDropdownMenuItemPadding = PaddingValues(all = 10.dp)
val CustomDropdownMenuItemMargin = PaddingValues(vertical = 5.dp)
val CustomDropdownMenuItemTextSize = 20.sp
val CustomDropdownMenuBoxMinHeight = 10.dp

//Fab
val FloatingActionButtonTextSize = CustomDropdownMenuItemTextSize
val FloatingActionButtonWidth = 1.dp
val FloatingActionButtonShape = RoundedCornerShape(percent = 29)

// Custom row filter
val CustomRowFilterTopPadding = PaddingValues(top = 15.dp)

//Custom Filter screen sheet
const val FilterSheetOpeningCoefficient = 0.75
val FilterSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
val FilterSheetTonalElevation = 16.dp
val FilterDragHandlePadding = PaddingValues(all = 8.dp)
val FilterDragHandleWidth = 50.dp
val FilterDragHandleHeight = 6.dp
val FilterDragHandleShape = RoundedCornerShape(percent = 50)
val FilterSheetColumnMargin = PaddingValues(top = 20.dp)
val FilterSheetColumnPadding = PaddingValues(horizontal = 20.dp)
val FilterSheetFilterColumnMargin =
    PaddingValues(top = FilterSheetColumnMargin.calculateTopPadding() / 2)

//Custom divider
val CustomDividerWidth = 10.dp
val CustomDividerHeight = 1.dp
val CustomDividerStartPadding = PaddingValues(start = 5.dp)

//Custom row filter
val RowFilterTopPadding = PaddingValues(top = 10.dp)
val RowFilterFirstPossibleVariantPadding =
    PaddingValues(start = 0.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
val RowFilterPossibleVariantPadding = PaddingValues(all = 5.dp)
val RowFilterItemShape = RoundedCornerShape(size = 100.dp)
val RowFilterItemTextPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
val RowFilterItemTextSize = 14.sp

//Showing screen
val ShowingScreenLazyColumnTopPadding = PaddingValues(top = 15.dp)

//Game column item
val GameColumnItemVerticalMargin = PaddingValues(vertical = 10.dp)
val GameColumnItemShape = RoundedCornerShape(size = 25.dp)
val GameColumnItemHorizontalPadding = PaddingValues(end = 15.dp)
val GameColumnItemPictureSectionVerticalMargin = PaddingValues(vertical = 10.dp)
val GameColumnItemPictureSectionTextSize = 20.sp
val GameColumnItemTextSectionVerticalMargin = PaddingValues(top = 5.dp)
val GameColumnItemTextSectionDateTextSize = 15.sp
val GameColumnItemTextSectionTimeTextSize = 10.sp
val GameColumnItemTextSectionMainTextSize = 25.sp
val GameColumnItemTextSectionVerticalRowPadding = PaddingValues(vertical = 5.dp)
val GameColumnItemTextSectionHorizontalRowPadding = PaddingValues(horizontal = 5.dp)
val GameColumnItemTextSectionStartTextPadding = PaddingValues(start = 5.dp)
val GameColumnItemTextSectionBoxSize = 24.dp

// Player column item
val PlayerItemNumberTextSize = 40.sp
val PlayerItemNameTextSize = 25.sp
val PlayerItemPositionTextSize = 15.sp
val PlayerItemCountryTextSize = 15.sp

//Team column item
val TeamColumnItemFlagSize = GameColumnItemTextSectionBoxSize * 2

//Empty screen
val EmptyScreenSpacerHeight = 20.dp
val EmptyScreenFontSize = 25.sp

// RequestType dialog
val RequestTypeDialogShape = RoundedCornerShape(size = 40.dp)
val RequestTypeDialogTopPadding = PaddingValues(top = 15.dp)
val RequestTypeDialogFontSize = 15.sp

// PAger
val pagerCurrentPagePadding = PaddingValues(all = 2.dp)
val pagerCurrentPageCircleSize = 16.dp
val pagerSectionPadding = PaddingValues(top = 15.dp)
val pagerElementsEmptyHeight = 40.dp