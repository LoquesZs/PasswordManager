package by.loqueszs.passwordmanager.features.profile.presentation.createprofile.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.common.util.EMPTY
import by.loqueszs.passwordmanager.features.profile.presentation.authorization.components.PinCodeBlock
import by.loqueszs.passwordmanager.features.profile.presentation.authorization.components.PinIndicatorRow
import by.loqueszs.passwordmanager.features.profile.presentation.createprofile.viewmodel.CreateProfileViewModel

private const val MAX_PIN_LENGTH = 4

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateProfileScreen(
    onComplete: () -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    BackHandler {
        (context as FragmentActivity).finish()
    }

    val scrollState = rememberScrollState()

    var loginState by remember { mutableStateOf(String.EMPTY) }
    var pinState by remember { mutableStateOf(listOf<Int>()) }

    var pinKeyboardAvailability by remember { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    pinKeyboardAvailability = pinState.size in 0 until MAX_PIN_LENGTH

    val confirmSigningUp = viewModel.isUserSignedUp
    LaunchedEffect(null) {
        confirmSigningUp.collect {
            if (it) onComplete()
        }
    }

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(defaultPadding * 2),
            style = MaterialTheme.typography.headlineSmall,
            text = "Create login"
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = defaultPadding * 2
                ),
            value = loginState,
            onValueChange = {
                loginState = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        )
        Text(
            modifier = Modifier.padding(defaultPadding * 2),
            style = MaterialTheme.typography.headlineSmall,
            text = "Create pin"
        )
        PinIndicatorRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLength = MAX_PIN_LENGTH,
            currentLength = pinState.size
        )
        PinCodeBlock(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = {
                pinState = pinState + it
            },
            isKeyboardAvailable = pinKeyboardAvailability,
            onBiometricsClick = { /*TODO*/ },
            isBackspaceAvailable = pinState.isNotEmpty(),
            onBackspace = {
                pinState = pinState.dropLast(1)
            }
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.createAccount(
                    loginState,
                    pinState.toString()
                )
            },
            enabled = loginState.isNotEmpty() && pinState.size == MAX_PIN_LENGTH
        ) {
            Text("Create")
        }
    }
}