package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import kotlinx.coroutines.launch

@Composable
fun CredentialsCard(
    item: CredentialsUIModel,
    modifier: Modifier = Modifier,
    onShowSnackBar: suspend () -> SnackbarResult,
    onClick: () -> Unit = {},
    onAddToFavorites: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val context = LocalContext.current
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dpOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ) {
        Card(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onClick()
                        },
                        onLongPress = {
                            isMenuExpanded = true
                            dpOffset = DpOffset(x = it.x.toDp(), y = -it.y.toDp())
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding * 2)
            ) {
                Row(
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Text(
                        modifier = Modifier
                            .weight(7F),
                        text = item.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1F),
                        onClick = {
                            onAddToFavorites()
                        }
                    ) {
                        val icon = if (item.favourite) {
                            Icons.Outlined.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        }
                        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                }

                Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelLarge
                )
                if (item.url.isNotBlank()) {
                    ClickableText(
                        modifier = Modifier.padding(vertical = defaultPadding),
                        text = AnnotatedString(
                            text = item.url,
                            spanStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        onClick = {
                            try {
                                if (Patterns.WEB_URL.matcher("https://${item.url}").matches()) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                                    context.startActivity(intent)
                                }
                            } catch (e: ActivityNotFoundException) {
                                scope.launch {
                                    when (onShowSnackBar()) {
                                        SnackbarResult.ActionPerformed -> {
                                        }

                                        SnackbarResult.Dismissed -> {
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
                if (item.note.isNotBlank()) {
                    Text(
                        text = item.note,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 4
                    )
                }
            }
        }

        DropdownMenu(
            modifier = Modifier.wrapContentWidth(),
            expanded = isMenuExpanded,
            offset = dpOffset,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.delete_credentials))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                },
                onClick = onDelete
            )
        }
    }
}
