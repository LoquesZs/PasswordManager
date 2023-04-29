package by.loqueszs.passwordmanager.features.credentials.presentation.addcredentials.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.util.defaultCornerSize
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun LoginBlock(
    visible: Boolean,
    login: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Column {
            Text(
                text = stringResource(R.string.input_login_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(defaultPadding)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = login,
                label = { Text(stringResource(R.string.login_label)) },
                onValueChange = onValueChange,
                shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
        }
    }
}