package by.loqueszs.passwordmanager.features.profile.presentation.profilescreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.profile.presentation.profilescreen.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userSettings by viewModel.userSettings
    var userLogin by remember { mutableStateOf(viewModel.login.value) }
    var doneButtonVisibility by remember { mutableStateOf(userLogin != viewModel.login.value) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null
        )
        Text("Hello,")
        Row(
            modifier = Modifier.padding(horizontal = defaultPadding * 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .requiredWidth(150.dp)
                    .wrapContentWidth(),
                value = userLogin,
                onValueChange = { value ->
                    doneButtonVisibility = value != viewModel.login.value
                    viewModel.onLoginChange(value)
                    userLogin = value
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.updateUserInfo()
                        doneButtonVisibility = userLogin != viewModel.login.value
                        focusManager.clearFocus()
                    }
                ),
                textStyle = MaterialTheme.typography.headlineSmall
            )
            AnimatedVisibility(visible = doneButtonVisibility) {
                IconButton(
                    onClick = {
                        viewModel.updateUserInfo()
                        doneButtonVisibility = userLogin != viewModel.login.value
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            }
        }
        userSettings?.let { settings ->
            ProfileSettings(
                userSettings = settings,
                onConfirmationChanged = { value ->
                    viewModel.setRequireConfirmation(value)
                },
                onAuthChanged = { value ->
                    viewModel.setRequireAuth(value)
                },
                onUseBiometricChanged = { value ->
                    viewModel.setUseBiometric(value)
                }
            )
        }
    }
}