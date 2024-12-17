package com.alenniboris.nba_app.presentation.screens.enter.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.EVTFInnerBoxPadding
import com.alenniboris.nba_app.presentation.uikit.theme.EVTFTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.PasswordHidePicture
import com.alenniboris.nba_app.presentation.uikit.theme.PasswordShowPicture
import com.alenniboris.nba_app.presentation.uikit.theme.SelectedTextBackgroundColor
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.selectedTextHandlesColor


@Composable
fun CustomEnterValueField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String = "",
    isPasswordField: Boolean = false,
    showPassword: Boolean = false,
    changePasswordVisibility: () -> Unit = {}
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = selectedTextHandlesColor,
        backgroundColor = SelectedTextBackgroundColor
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        EnterValueTextField(
            value = value,
            onValueChanged = onValueChanged,
            placeholder = placeholder,
            isPasswordField = isPasswordField,
            modifier = modifier,
            showPassword = showPassword,
            changePasswordVisibility = changePasswordVisibility
        )
    }
}

@Composable
@Preview
private fun EnterValueTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChanged: (String) -> Unit = {},
    placeholder: String = "Hint",
    isPasswordField: Boolean = false,
    showPassword: Boolean = false,
    changePasswordVisibility: () -> Unit = {}
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .width(IntrinsicSize.Max),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(EVTFInnerBoxPadding)
                        .weight(1f),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = enterTextFieldTextColor,
                            style = bodyStyle.copy(fontSize = EVTFTextSize)
                        )
                    }
                    innerTextField()
                }

                if (isPasswordField) {
                    IconButton(
                        onClick = { changePasswordVisibility() },
                    ) {
                        Icon(
                            painter = if (showPassword) painterResource(PasswordShowPicture)
                            else painterResource(PasswordHidePicture),
                            contentDescription = stringResource(R.string.show_password_btn_descr),
                            tint = enterTextFieldTextColor
                        )
                    }
                }
            }
        },
        textStyle = bodyStyle.copy(
            color = enterTextFieldTextColor,
            fontSize = EVTFTextSize
        ),
        cursorBrush = SolidColor(enterTextFieldTextColor),
        visualTransformation = if (isPasswordField && !showPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        maxLines = 1
    )
}


@Composable
@Preview
private fun CustomEnterValueFieldPreview() {
    Column {
        CustomEnterValueField(
            value = "",
            onValueChanged = {},
            placeholder = "Hello",
            isPasswordField = true
        )

        CustomEnterValueField(
            value = "",
            onValueChanged = {},
            placeholder = "world",
            isPasswordField = true,
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
        )

        CustomEnterValueField(
            value = "",
            onValueChanged = {},
            placeholder = "world coming true",
            isPasswordField = false,
            modifier = Modifier
                .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                .padding(10.dp)
                .width(100.dp)
        )

        CustomEnterValueField(
            value = "",
            onValueChanged = {},
            placeholder = "world",
            isPasswordField = true,
            modifier = Modifier
                .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                .padding(10.dp)
                .width(300.dp)
        )

        CustomEnterValueField(
            value = "",
            onValueChanged = {},
            placeholder = "world",
            isPasswordField = true,
            modifier = Modifier
                .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                .padding(10.dp)
                .width(500.dp)
        )
    }
}

