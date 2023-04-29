package by.loqueszs.passwordmanager.features.credentials.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.util.OutlinedPasswordTextField
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun PasswordBlock(
    visible: Boolean,
    password: String,
    onValueChange: (String) -> Unit,
    onClearToggleClick: () -> Unit,
    onGeneratePassword: () -> Unit,
    focusManager: FocusManager,
    onFocusEvent: (FocusState, BringIntoViewRequester) -> Unit,
    onBringIntoNextViewRequest: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        var isPasswordTextVisible by remember { mutableStateOf(false) }
        val bringIntoViewRequester = remember { BringIntoViewRequester() }
        Column(
            modifier = Modifier
                .onFocusEvent { focusState ->
                    onFocusEvent(focusState, bringIntoViewRequester)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.input_password_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(defaultPadding)
                    .align(Alignment.Start)
            )

            OutlinedPasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                                    onFocusEvent(it, bringIntoViewRequester)
                    },
                value = password,
                onValueChange = onValueChange,
                isPasswordVisible = isPasswordTextVisible,
                onVisibilityToggleClick = { isPasswordTextVisible = !isPasswordTextVisible },
                onClearToggleClick = onClearToggleClick,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onBringIntoNextViewRequest()
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            OutlinedButton(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(defaultPadding)
                    .focusProperties {
                        canFocus = false
                    }
                    .bringIntoViewRequester(bringIntoViewRequester),
                onClick = onGeneratePassword
            ) {
                Text(
                    text = stringResource(R.string.generate_password),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
