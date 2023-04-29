package by.loqueszs.passwordmanager.features.credentials.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.util.defaultCornerSize
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TitleBLock(
    title: String,
    titleExistsError: Boolean,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {
    val focusRequester = remember { FocusRequester() }
    var isFocusRequested by remember { mutableStateOf(false) }
    Column {
        Text(
            text = stringResource(R.string.input_title_label),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(defaultPadding)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = title,
            label = { Text(stringResource(R.string.title_label)) },
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
            singleLine = true,
            isError = titleExistsError,
            supportingText = {
                if (titleExistsError) {
                    Text(text = stringResource(R.string.title_already_exists_error_message))
                }
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        LaunchedEffect(Unit) {
            if (!isFocusRequested) {
                focusRequester.requestFocus()
                isFocusRequested = true
            }
        }
    }
}