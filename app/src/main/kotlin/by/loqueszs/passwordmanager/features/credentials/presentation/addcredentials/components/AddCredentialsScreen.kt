package by.loqueszs.passwordmanager.features.credentials.presentation.addcredentials.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.common.ui.theme.PassManagerTheme
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.credentials.presentation.addcredentials.viewmodel.AddCredentialsViewModel
import by.loqueszs.passwordmanager.features.credentials.ui.FinalBlock
import by.loqueszs.passwordmanager.features.credentials.ui.PasswordBlock
import by.loqueszs.passwordmanager.features.credentials.ui.TitleBLock
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AddCredentialsScreen(
    onAuth: () -> Unit = {},
    onComplete: () -> Unit = {},
    viewModel: AddCredentialsViewModel = hiltViewModel()
) {
    onAuth()

    val scrollState = rememberScrollState()

    val addCredentialsUIState by viewModel.uiState.collectAsState()

    val titleExistsError by viewModel.titleExistsError.collectAsState()

    var loginVisibility by remember {
        mutableStateOf(addCredentialsUIState.title.isNotBlank() && !titleExistsError)
    }

    var passwordVisibility by remember {
        mutableStateOf(
            addCredentialsUIState.title.isNotBlank() &&
                addCredentialsUIState.login.isNotBlank() &&
                !titleExistsError
        )
    }

    var finalBlockVisibility by remember {
        mutableStateOf(
            addCredentialsUIState.title.isNotBlank() &&
                addCredentialsUIState.login.isNotBlank() &&
                addCredentialsUIState.password.isNotBlank() &&
                !titleExistsError
        )
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val composableScope = rememberCoroutineScope()
    val onFocusEvent: (focusState: FocusState, bringIntoViewRequester: BringIntoViewRequester) -> Unit = remember {
        { focusState, bringIntoViewRequester ->
            Log.d("AddCreds", "$focusState")
            if (focusState.hasFocus) {
                composableScope.launch {
                    bringIntoViewRequester.bringIntoView()
                }
            }
        }
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .imePadding()
            .systemBarsPadding()
            .padding(defaultPadding * 2)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // TITLE BLOCK

        TitleBLock(
            title = addCredentialsUIState.title,
            titleExistsError = titleExistsError,
            onValueChange = {
                viewModel.setTitle(it)
                loginVisibility = it.isNotBlank()
            },
            focusManager = focusManager
        )

        // LOGIN BLOCK

        LoginBlock(
            visible = loginVisibility && !titleExistsError,
            login = addCredentialsUIState.login,
            onValueChange = {
                viewModel.setLogin(it)
                passwordVisibility = loginVisibility && it.isNotBlank()
                finalBlockVisibility = loginVisibility && passwordVisibility && addCredentialsUIState.password.isNotBlank()
            },
            focusManager = focusManager
        )

        // PASSWORD BLOCK

        PasswordBlock(
            visible = passwordVisibility && !titleExistsError,
            password = addCredentialsUIState.password,
            onValueChange = {
                viewModel.setPassword(it)
                finalBlockVisibility = loginVisibility && passwordVisibility && it.isNotBlank()
            },
            onClearToggleClick = {
                viewModel.clearPassword()
                finalBlockVisibility = false
            },
            onGeneratePassword = {
                viewModel.setRandomPassword()
                finalBlockVisibility = true
            },
            focusManager = focusManager,
            onFocusEvent = onFocusEvent,
            onBringIntoNextViewRequest = {
                composableScope.launch {
                    bringIntoViewRequester.bringIntoView()
                }
            }
        )

        // SUBMIT BUTTON

        FinalBlock(
            visibility = finalBlockVisibility && !titleExistsError,
            urlValue = addCredentialsUIState.url,
            onURLChange = {
                viewModel.setURL(it)
            },
            noteValue = addCredentialsUIState.note,
            onNoteChange = {
                viewModel.setNote(it)
            },
            keyboardController = keyboardController,
            onComplete = {
                composableScope.launch {
                    viewModel.submitCredentials()
                }
                keyboardController?.hide()
                onComplete()
            },
            focusManager = focusManager,
            onFocusEvent = onFocusEvent,
            bringIntoViewRequester = bringIntoViewRequester
        )
    }
}

@Preview
@Composable
fun AddCredentialsPreview() {
    PassManagerTheme {
        AddCredentialsScreen()
    }
}
