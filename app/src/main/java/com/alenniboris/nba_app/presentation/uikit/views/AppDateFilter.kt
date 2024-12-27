package com.alenniboris.nba_app.presentation.uikit.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldShape
import com.alenniboris.nba_app.presentation.uikit.theme.datePickerFieldContainerColor
import com.alenniboris.nba_app.presentation.uikit.theme.datePickerFieldSelectedContainerColor
import com.alenniboris.nba_app.presentation.uikit.theme.datePickerFieldSelectedTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.datePickerFieldTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.datePickerTodayColor
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldTextColor
import java.util.Date

@Composable
@Preview
fun AppDateFilter(
    currentDateTime: Date = Date(),
    currentDateTimeText: String = "",
    onDateSelectedAction: (Date) -> Unit = {},
) {

    var showDatePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    enterTextFieldColor,
                    shape = ESCustomTextFieldShape
                )
                .padding(ESCustomTextFieldPadding)
                .fillMaxWidth()
                .weight(1f),
            isEnabled = false,
            value = currentDateTimeText,
            onValueChanged = {},
            placeholder = stringResource(R.string.date_selection_placeholder)
        )

        Button(
            modifier = Modifier
                .padding(start = 15.dp),
            onClick = {
                showDatePicker = true
            },
            colors = ButtonColors(
                containerColor = enterTextFieldColor,
                contentColor = enterTextFieldTextColor,
                disabledContainerColor = enterTextFieldColor,
                disabledContentColor = enterTextFieldTextColor
            ),
            shape = ESCustomTextFieldShape
        ) {
            Text(
                text = stringResource(R.string.date_chooser_btn_text)
            )
        }
    }


    if (showDatePicker) {
        AppDatePicker(
            currentDateTime = currentDateTime,
            onDateSelected = { selectedDate ->
                selectedDate?.let {
                    onDateSelectedAction(Date(selectedDate))
                }
                Log.d("Approved", " Begin  ${selectedDate.toString()}")
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun AppDatePicker(
    currentDateTime: Date = Date(),
    onDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedDateMillis = currentDateTime.time
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                colors = ButtonColors(
                    containerColor = datePickerFieldContainerColor,
                    contentColor = datePickerFieldTextColor,
                    disabledContainerColor = datePickerFieldContainerColor,
                    disabledContentColor = datePickerFieldTextColor
                )
            ) {
                Text(
                    text = stringResource(R.string.date_chooser_approve_text)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonColors(
                    containerColor = datePickerFieldContainerColor,
                    contentColor = datePickerFieldTextColor,
                    disabledContainerColor = datePickerFieldContainerColor,
                    disabledContentColor = datePickerFieldTextColor
                )
            ) {
                Text(
                    text = stringResource(R.string.date_chooser_cancel_text)
                )
            }
        },
        colors = DatePickerDefaults.colors().copy(
            containerColor = datePickerFieldContainerColor,
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors().copy(
                titleContentColor = datePickerFieldTextColor,
                headlineContentColor = datePickerFieldTextColor,
                navigationContentColor = datePickerFieldTextColor,
                yearContentColor = datePickerFieldSelectedTextColor,
                currentYearContentColor = datePickerFieldSelectedTextColor,
                selectedYearContentColor = datePickerFieldSelectedTextColor,
                selectedYearContainerColor = datePickerFieldSelectedContainerColor,
                dayContentColor = datePickerFieldTextColor,
                selectedDayContainerColor = datePickerFieldSelectedContainerColor,
                selectedDayContentColor = datePickerFieldSelectedTextColor,
                todayDateBorderColor = datePickerTodayColor,
                todayContentColor = datePickerTodayColor,
                weekdayContentColor = datePickerTodayColor,

                dateTextFieldColors =
                OutlinedTextFieldDefaults.colors().copy(
                    focusedTextColor = datePickerTodayColor,
                    unfocusedTextColor = datePickerTodayColor,
                    focusedPlaceholderColor = datePickerTodayColor,
                    cursorColor = datePickerTodayColor,
                    focusedContainerColor = datePickerFieldContainerColor,
                    unfocusedContainerColor = datePickerFieldContainerColor,
                    focusedLabelColor = datePickerFieldTextColor,
                    unfocusedLabelColor = datePickerFieldTextColor,
                    errorTextColor = datePickerTodayColor
                )

            )
        )
    }
}