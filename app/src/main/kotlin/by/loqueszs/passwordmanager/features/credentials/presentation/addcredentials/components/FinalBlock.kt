package by.loqueszs.passwordmanager.features.credentials.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.util.defaultCornerSize
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun FinalBlock(
    visibility: Boolean,
    urlValue: String,
    onURLChange: (String) -> Unit,
    noteValue: String,
    onNoteChange: (String) -> Unit,
    onComplete: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    onFocusEvent: (FocusState, BringIntoViewRequester) -> Unit,
    bringIntoViewRequester: BringIntoViewRequester
) {
    AnimatedVisibility(
        visible = visibility,
        enter = scaleIn(),
        exit = scaleOut()
    ) {

        Column(
            modifier = Modifier
                .onFocusEvent { focusState ->
                    onFocusEvent(focusState, bringIntoViewRequester)
                }
        ) {
            Text(text = stringResource(R.string.url_title))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = defaultPadding),
                value = urlValue,
                label = { stringResource(R.string.url_label) },
                onValueChange = onURLChange,
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

            Text(text = stringResource(R.string.note_title))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = defaultPadding),
                value = noteValue,
                label = { stringResource(R.string.note_label) },
                onValueChange = onNoteChange,
                shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = defaultPadding)
                    .bringIntoViewRequester(bringIntoViewRequester),
                onClick = onComplete
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}