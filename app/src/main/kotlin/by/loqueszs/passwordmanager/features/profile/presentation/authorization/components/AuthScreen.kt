package by.loqueszs.passwordmanager.features.profile.presentation.authorization.components

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.features.profile.domain.model.AuthStatus
import by.loqueszs.passwordmanager.features.profile.presentation.authorization.util.showBiometricPrompt
import by.loqueszs.passwordmanager.features.profile.presentation.authorization.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavToCreateAccount: () -> Unit = {},
    onAuthSuccess: () -> Unit = { }
) {
    val context = LocalContext.current
    BackHandler {
        (context as FragmentActivity).finish()
    }

    if (!viewModel.isUserSignedUp()) {
        Log.d("Auth Screen", " Is user signed up: ${ viewModel.isUserSignedUp() }")
        onNavToCreateAccount()
    }

    val isKeyboardAvailable by viewModel.isKeyboardAvailable.collectAsState()
    val isBackSpaceAvailable by viewModel.isBackspaceAvailable.collectAsState()
    val isPinCorrect by viewModel.userAuthStatus.collectAsState()
    val userInputSize by viewModel.userInputSize.collectAsState()
    var isError by remember { mutableStateOf(false) }

    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()

    val isBiometricAvailable by remember {
        mutableStateOf(context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
    }
    var wasBiometricShown by remember { mutableStateOf(false) }

    if (isBiometricAvailable && isBiometricEnabled == true && !wasBiometricShown && viewModel.isUserSignedUp()) {
        showBiometricPrompt(
            activity = context as FragmentActivity,
            onSuccess = {
                viewModel.biometricAuth()
                onAuthSuccess()
            },
            onError = { code, message ->
            },
            onFail = {
            }
        )
        wasBiometricShown = true
    }

    when (isPinCorrect) {
        is AuthStatus.Authorized -> {
            onAuthSuccess()
        }
        is AuthStatus.NotAuthorized -> {
            isError = userInputSize == 4
        }
    }

    Column(
        modifier = Modifier.systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    start = 60.dp,
                    top = 100.dp,
                    end = 60.dp
                )
                .size(56.dp),
            imageVector = Icons.Filled.Lock,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Text(text = "Input your pin code")
        PinIndicatorRow(
            maxLength = 4,
            currentLength = userInputSize,
            isError = isError
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 40.dp)
        )
        PinCodeBlock(
            modifier = Modifier
                .padding(
                    start = 30.dp,
                    end = 30.dp
                ),
            isKeyboardAvailable = isKeyboardAvailable,
            isBiometricAvailable = isBiometricAvailable,
            isBackspaceAvailable = isBackSpaceAvailable,
            onClick = {
                viewModel.userInputAdd(it)
            },
            onBiometricsClick = {
                if (isBiometricAvailable) {
                    showBiometricPrompt(
                        activity = context as FragmentActivity,
                        onSuccess = {
                            onAuthSuccess()
                            viewModel.biometricAuth()
                        },
                        onError = { code, message ->
                        },
                        onFail = { }
                    )
                }
            },
            onBackspace = {
                viewModel.userInputRemoveLast()
            }
        )
    }
}