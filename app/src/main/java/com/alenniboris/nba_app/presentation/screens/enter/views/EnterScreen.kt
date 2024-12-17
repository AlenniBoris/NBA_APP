package com.alenniboris.nba_app.presentation.screens.enter.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.screens.enter.AuthIntent
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import com.alenniboris.nba_app.presentation.screens.enter.state.IEnterState
import com.alenniboris.nba_app.presentation.screens.enter.state.LoginState
import com.alenniboris.nba_app.presentation.screens.enter.state.RegistrationState
import com.alenniboris.nba_app.presentation.uikit.theme.ESColumnPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldShape
import com.alenniboris.nba_app.presentation.uikit.theme.ESSpacerHeight
import com.alenniboris.nba_app.presentation.uikit.theme.ESSpacerHeightDouble
import com.alenniboris.nba_app.presentation.uikit.theme.EVTFTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.EnterScreenPicture
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldTextColor

@Composable
fun EnterScreen() {

    val authViewModel: EnterScreenVM = viewModel()
    val state by authViewModel.screenState.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(authViewModel::handleIntent) }

    UI(
        state = state,
        intent = intent
    )
}


@Composable
@Preview
private fun UI(
    state: IEnterState = LoginState(),
    intent: (AuthIntent) -> Unit = {}
) {
    var showPassword by remember { mutableStateOf(false) }
    var showPasswordCheck by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(appColor)
            .fillMaxSize()
            .padding(ESColumnPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(EnterScreenPicture),
            contentDescription = stringResource(R.string.enter_logo_description)
        )

        Spacer(modifier = Modifier.height(ESSpacerHeightDouble))

        CustomEnterValueField(
            value = state.enteredEmail,
            onValueChanged = { text ->
                intent(AuthIntent.UpdateEnteredEmail(text))
            },
            placeholder = stringResource(R.string.text_email),
            isPasswordField = false,
            modifier = Modifier
                .background(
                    enterTextFieldColor,
                    shape = ESCustomTextFieldShape
                )
                .padding(ESCustomTextFieldPadding)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(ESSpacerHeight))

        CustomEnterValueField(
            value = state.enteredPassword,
            onValueChanged = { text ->
                intent(AuthIntent.UpdateEnteredPassword(text))
            },
            placeholder = stringResource(R.string.text_password),
            isPasswordField = true,
            modifier = Modifier
                .background(
                    enterTextFieldColor,
                    shape = ESCustomTextFieldShape
                )
                .padding(ESCustomTextFieldPadding)
                .fillMaxWidth(),
            showPassword = showPassword,
            changePasswordVisibility = { showPassword = !showPassword }
        )

        (state as? RegistrationState)?.let { registrationState ->
            Spacer(modifier = Modifier.height(ESSpacerHeight))

            CustomEnterValueField(
                value = registrationState.enteredPasswordCheck,
                onValueChanged = { text ->
                    intent(AuthIntent.UpdateEnteredPasswordCheck(text))
                },
                placeholder = stringResource(R.string.text_password_check),
                isPasswordField = true,
                modifier = Modifier
                    .background(
                        enterTextFieldColor,
                        shape = ESCustomTextFieldShape
                    )
                    .padding(ESCustomTextFieldPadding)
                    .fillMaxWidth(),
                showPassword = showPasswordCheck,
                changePasswordVisibility = { showPasswordCheck = !showPasswordCheck }
            )
        }

        Spacer(modifier = Modifier.height(ESSpacerHeightDouble))

        Button(
            onClick = {
                intent(AuthIntent.ButtonClickProceed)
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = enterTextFieldColor
            )
        ) {
            Text(
                text = if (state is RegistrationState) stringResource(R.string.register_btn_text)
                else stringResource(R.string.login_btn_text),
                color = enterTextFieldTextColor,
                style = bodyStyle.copy(
                    fontSize = EVTFTextSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(ESSpacerHeight))

        TextButton(
            onClick = {
                showPassword = false
                showPasswordCheck = false
                intent(AuthIntent.SwitchBetweenLoginAndRegister)
            }
        ) {
            Text(
                text = if (state is RegistrationState) stringResource(R.string.switch_to_login_process_text)
                else stringResource(R.string.switch_to_registration_process_text),
                color = enterTextFieldColor,
                style = bodyStyle.copy(fontSize = EVTFTextSize)
            )
        }
    }
}