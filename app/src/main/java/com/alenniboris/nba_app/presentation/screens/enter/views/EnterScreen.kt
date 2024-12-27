package com.alenniboris.nba_app.presentation.screens.enter.views

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.screens.enter.AuthenticationIntent
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenEvent
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import com.alenniboris.nba_app.presentation.screens.enter.state.IEnterState
import com.alenniboris.nba_app.presentation.screens.enter.state.LoginState
import com.alenniboris.nba_app.presentation.screens.enter.state.RegistrationState
import com.alenniboris.nba_app.presentation.uikit.theme.ESColumnPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldShape
import com.alenniboris.nba_app.presentation.uikit.theme.ESSpacerHeight
import com.alenniboris.nba_app.presentation.uikit.theme.ESSpacerHeightDouble
import com.alenniboris.nba_app.presentation.uikit.theme.EnterValueTextFieldTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.EnterScreenPicture
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.nba_app.presentation.uikit.views.AppTextField
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterScreen() {

    val authViewModel: EnterScreenVM = koinViewModel<EnterScreenVM>()

    val state by authViewModel.screenState.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(authViewModel::handleIntent) }
    val context = LocalContext.current
    val event by remember { mutableStateOf(authViewModel.event) }
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<EnterScreenEvent.ShowToastMessage>().collect { ev ->
                toastMessage.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(ev.messageId), Toast.LENGTH_SHORT)
                toastMessage.show()
            }
        }
    }

    EnterScreenUi(
        state = state,
        intent = intent,
    )
}


@Composable
@Preview
private fun EnterScreenUi(
    state: IEnterState = LoginState(),
    intent: (AuthenticationIntent) -> Unit = {},
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

        AppTextField(
            value = state.enteredEmail,
            onValueChanged = { text ->
                intent(AuthenticationIntent.UpdateEnteredEmail(text))
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

        AppTextField(
            value = state.enteredPassword,
            onValueChanged = { text ->
                intent(AuthenticationIntent.UpdateEnteredPassword(text))
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
            isPasswordVisible = showPassword,
            onPasswordVisibilityChange = { showPassword = !showPassword }
        )

        (state as? RegistrationState)?.let { registrationState ->
            Spacer(modifier = Modifier.height(ESSpacerHeight))

            AppTextField(
                value = registrationState.enteredPasswordCheck,
                onValueChanged = { text ->
                    intent(AuthenticationIntent.UpdateEnteredPasswordCheck(text))
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
                isPasswordVisible = showPasswordCheck,
                onPasswordVisibilityChange = { showPasswordCheck = !showPasswordCheck }
            )
        }

        Spacer(modifier = Modifier.height(ESSpacerHeightDouble))

        Button(
            onClick = {
                intent(AuthenticationIntent.ButtonClickProceed)
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
                    fontSize = EnterValueTextFieldTextSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(ESSpacerHeight))

        TextButton(
            onClick = {
                showPassword = false
                showPasswordCheck = false
                intent(AuthenticationIntent.SwitchBetweenLoginAndRegister)
            }
        ) {
            Text(
                text = if (state is RegistrationState) stringResource(R.string.switch_to_login_process_text)
                else stringResource(R.string.switch_to_registration_process_text),
                color = enterTextFieldColor,
                style = bodyStyle.copy(fontSize = EnterValueTextFieldTextSize)
            )
        }
    }
}