package by.loqueszs.passwordmanager.features.profile.presentation.profilescreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.profile.domain.model.UserSettings

@Composable
fun ProfileSettings(
    userSettings: UserSettings,
    onConfirmationChanged: (Boolean) -> Unit = {},
    onAuthChanged: (Boolean) -> Unit = {},
    onUseBiometricChanged: (Boolean) -> Unit = {}
) {
    var confirmState by remember { mutableStateOf(userSettings.confirmByPin) }
    var authState by remember { mutableStateOf(userSettings.authByPin) }
    var biometricState by remember { mutableStateOf(userSettings.useBiometric) }

    Column(
        modifier = Modifier
            .padding(defaultPadding * 2)
    ) {
        Text(
            text = "Auth settings",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Require confirmation by PIN"
            )
            IconedSwitch(
                modifier = Modifier,
                checked = confirmState,
                onCheckedChange = {
                    onConfirmationChanged(it)
                    confirmState = it
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Require authorization by PIN"
            )
            IconedSwitch(
                checked = authState,
                onCheckedChange = {
                    onAuthChanged(it)
                    authState = it
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Use biometric"
            )
            IconedSwitch(
                checked = biometricState,
                onCheckedChange = {
                    onUseBiometricChanged(it)
                    biometricState = it
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IconedSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = {
            AnimatedContent(targetState = checked, label = "") {
                if (it) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        },
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}

@Preview
@Composable
fun ProfileSettingsPreview() {
    MaterialTheme {
        ProfileSettings(
            userSettings = UserSettings(),
            onConfirmationChanged = {},
            onAuthChanged = {},
            onUseBiometricChanged = {}
        )
    }
}