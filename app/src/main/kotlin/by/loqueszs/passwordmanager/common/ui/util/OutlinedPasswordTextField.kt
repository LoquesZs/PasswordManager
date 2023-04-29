package by.loqueszs.passwordmanager.common.ui.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import by.loqueszs.passwordmanager.R

internal object StarsPasswordVisualTransformation : VisualTransformation {
    private const val symbol = "*"
    private val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset * symbol.length
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset / symbol.length
        }
    }
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            AnnotatedString(symbol.repeat(text.text.length)),
            offsetTranslator
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedPasswordTextField(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
    value: String,
    label: String = stringResource(id = R.string.password_label),
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggleClick: () -> Unit,
    onClearToggleClick: () -> Unit,
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
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
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
                    onClick = onClearToggleClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedPasswordTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String = stringResource(R.string.password_label),
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.medium.copy(defaultCornerSize),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    isPasswordVisible: Boolean,
    onVisibilityToggleClick: () -> Unit,
    onClearToggleClick: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        enabled = enabled,
        readOnly = false,
        textStyle = textStyle,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = {
            val icon = if (isPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            Row(modifier = Modifier.wrapContentSize()) {
                IconButton(
                    onClick = onClearToggleClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
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
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}
