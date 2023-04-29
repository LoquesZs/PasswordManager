package by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.components

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.theme.PassManagerTheme
import by.loqueszs.passwordmanager.common.ui.util.OutlinedCopyableTextField
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.viewmodel.CredentialsDetailViewModel
import by.loqueszs.passwordmanager.features.navigation.AUTH_REQUEST_LOGIN
import by.loqueszs.passwordmanager.features.navigation.AUTH_REQUEST_PASSWORD
import by.loqueszs.passwordmanager.utils.copyTextToClipBoard
import kotlinx.coroutines.launch

sealed class CredentialsType {
    object Login : CredentialsType()
    object Password : CredentialsType()
}

private const val LABEL_LOGIN = "label_login"
private const val LABEL_PASSWORD = "label_password"

@Composable
fun CredentialsDetailScreen(
    id: Long,
    onAuth: () -> Unit = {},
    onRequestConfirmation: (String) -> Unit = {},
    onConfirmationResult: () -> Result<CredentialsType>,
    viewModel: CredentialsDetailViewModel = hiltViewModel()
) {
    onAuth()

    val context = LocalContext.current

    viewModel.setID(id)
    val title by viewModel.title
    val login by viewModel.login
    val password by viewModel.password
    val date by viewModel.date
    val note by viewModel.note
    val url by viewModel.url
    val isFavourite by viewModel.isFavourite

    val isConfirmationEnabled by viewModel.isConfirmationEnabled.collectAsState()

    val composableScope = rememberCoroutineScope()

    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val snackMessage = stringResource(id = R.string.invalid_url_message)
    val snackActionLabel = stringResource(id = R.string.invalid_url_action_label)

    onConfirmationResult().onSuccess {
        when (it) {
            is CredentialsType.Login -> {
                copyTextToClipBoard(context, "label", login)
            }
            is CredentialsType.Password -> {
                copyTextToClipBoard(context, "label", password)
            }
        }
    }.onFailure {
        // Do nothing
    }

    Box(
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
    ) {
        DetailItem(
            onAddToFavorites = {
                viewModel.addToFavorites(it)
            },
            onCopyClick = { login, label ->
                when (isConfirmationEnabled) {
                    true -> {
                        when (label) {
                            LABEL_LOGIN -> onRequestConfirmation(AUTH_REQUEST_LOGIN)
                            LABEL_PASSWORD -> onRequestConfirmation(AUTH_REQUEST_PASSWORD)
                        }
                    }
                    else -> {
                        copyTextToClipBoard(context, label, login)
                    }
                }
            },
            onInvalidUrl = {
                composableScope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackMessage,
                        actionLabel = snackActionLabel,
                        duration = SnackbarDuration.Short
                    )
                }
            },
            title = title,
            onTitleEditing = {
                viewModel.updateTitle(it)
            },
            login = login,
            onLoginChange = {
                viewModel.updateLogin(it)
            },
            password = password,
            onPasswordChange = {
                viewModel.updatePassword(it)
            },
            date = date,
            note = note,
            onNoteChange = {
                viewModel.updateNote(it)
            },
            url = url,
            isFavourite = isFavourite
        )
        SnackbarHost(
            hostState = snackbarHostState
        ) {
            Snackbar(snackbarData = it)
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Divider()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding),
                onClick = {
                    composableScope.launch {
                        viewModel.saveChanges()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Composable
fun DetailItem(
    onCopyClick: (textToCopy: String, label: String) -> Unit,
    onInvalidUrl: () -> Unit = {},
    title: String = "",
    onTitleEditing: (String) -> Unit = {},
    isFavourite: Boolean = false,
    onAddToFavorites: (Boolean) -> Unit,
    login: String = "",
    onLoginChange: (String) -> Unit = {},
    password: String = "",
    onPasswordChange: (String) -> Unit = {},
    note: String = "",
    onNoteChange: (String) -> Unit = {},
    url: String = "",
    onUrlChange: (String) -> Unit = {},
    date: String = "",
    onDateChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    // var dateTimePickerVisibility by remember { mutableStateOf(false) }
    var loginVisibility by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            )
            .padding(
                start = defaultPadding * 2,
                end = defaultPadding * 2,
                top = defaultPadding * 2,
                bottom = defaultPadding * 10
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(defaultPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(7F),
                value = title,
                onValueChange = { newValue ->
                    onTitleEditing(newValue)
                },
                textStyle = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            IconButton(
                modifier = Modifier
                    .weight(1F),
                onClick = {
                    onAddToFavorites(!isFavourite)
                }
            ) {
                val icon = if (isFavourite) {
                    Icons.Outlined.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                }
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }

        Text(
            text = date,
            style = MaterialTheme.typography.bodySmall
        )

        SelectionContainer {
            Column {
                ClickableText(
                    modifier = Modifier.padding(vertical = defaultPadding),
                    text = AnnotatedString(
                        text = url,
                        spanStyle = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    style = MaterialTheme.typography.bodySmall.merge(TextStyle(fontSize = 16.sp)),
                    maxLines = 1,
                    onClick = {
                        if (Patterns.WEB_URL.matcher(url).matches()) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        } else {
                            onInvalidUrl()
                        }
                    }
                )
                BasicTextField(
                    value = note,
                    onValueChange = { newValue ->
                        onNoteChange(newValue)
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
        }
        OutlinedCopyableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = defaultPadding
                ),
            value = login,
            label = stringResource(id = R.string.login_label),
            onValueChange = {
                onLoginChange(it)
            },
            isPasswordVisible = loginVisibility,
            onVisibilityToggleClick = {
                loginVisibility = !loginVisibility
            },
            onCopyClick = { textToCopy, _ ->
                onCopyClick(textToCopy, LABEL_LOGIN)
            }
        )
        OutlinedCopyableTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = defaultPadding
                ),
            value = password,
            label = stringResource(id = R.string.password_label),
            onValueChange = {
                onPasswordChange(it)
            },
            isPasswordVisible = passwordVisibility,
            onVisibilityToggleClick = {
                passwordVisibility = !passwordVisibility
            },
            onCopyClick = { textToCopy, _ ->
                onCopyClick(textToCopy, LABEL_PASSWORD)
            }
        )
    }
}

@Preview
@Composable
fun CredentialsDetailScreenPreview() {
    PassManagerTheme {
        DetailItem(
            onAddToFavorites = { },
            onCopyClick = { _, _ ->
            }
        ) {
        }
    }
}
