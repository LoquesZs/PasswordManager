package by.loqueszs.passwordmanager.common.ui.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun OutlinedCopyableTextField(
//    modifier: Modifier = Modifier,
//    value: String,
//    shape: Shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
//    isPassword: Boolean = false,
//    onCopyClick: (textToCopy: String, label: String) -> Unit = { _, _ -> }
// ) {
//    var internalVisualTransformation by remember {
//        mutableStateOf(if (isPassword) PasswordVisualTransformation() else VisualTransformation.None)
//    }
//    OutlinedTextField(
//        value = value,
//        onValueChange = {},
//        modifier = modifier
//            .clickable(false) {}
//            .fillMaxWidth(),
//        shape = shape,
//        readOnly = true,
//        visualTransformation = internalVisualTransformation,
//        trailingIcon = {
//            when {
//                isPassword -> {
//                    var isPasswordVisible by remember { mutableStateOf(false) }
//                    val icon = if (isPasswordVisible) {
//                        Icons.Filled.Visibility
//                    } else {
//                        Icons.Filled.VisibilityOff
//                    }
//                    Row {
//                        IconButton(
//                            onClick = {
//                                internalVisualTransformation = if (isPasswordVisible) {
//                                    VisualTransformation.None
//                                } else {
//                                    PasswordVisualTransformation()
//                                }
//                                isPasswordVisible = !isPasswordVisible
//                            }
//                        ) {
//                            Icon(
//                                imageVector = icon,
//                                contentDescription = null
//                            )
//                        }
//                        IconButton(
//                            onClick = { onCopyClick(value, "label") }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Outlined.ContentCopy,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                }
//                else -> {
//                    IconButton(
//                        onClick = { onCopyClick(value, "label") }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.ContentCopy,
//                            contentDescription = null
//                        )
//                    }
//                }
//            }
//        }
//    )
// }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedCopyableTextField(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggleClick: () -> Unit,
    onCopyClick: (textToCopy: String, label: String) -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        label = { Text(label) },
        onValueChange = onValueChange,
        shape = shape,
        textStyle = textStyle,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = maxLines,
        interactionSource = interactionSource,
        colors = colors,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else StarsPasswordVisualTransformation,
        trailingIcon = {
            val icon = if (isPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            Row(modifier = Modifier.wrapContentSize()) {
                IconButton(
                    onClick = {
                        onCopyClick(value, "label")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = onVisibilityToggleClick
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun OutlinedCopyableTextFieldPreview() {
    var passwordVisibility by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("text") }
    OutlinedCopyableTextField(
        value = value,
        onValueChange = {
            value = it
        },
        label = "text to copy",
        isPasswordVisible = passwordVisibility,
        onVisibilityToggleClick = {
            passwordVisibility = !passwordVisibility
        },
        onCopyClick = { _, _ ->
        }
    )
}