package by.loqueszs.passwordmanager.features.profile.presentation.authorization.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.common.util.EMPTY

@Composable
fun PinCodeBlock(
    modifier: Modifier = Modifier,
    isKeyboardAvailable: Boolean = true,
    isBiometricAvailable: Boolean = false,
    isBackspaceAvailable: Boolean = false,
    onClick: (Int) -> Unit,
    onBiometricsClick: () -> Unit,
    onBackspace: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
    ) {
        Row {
            for (number in 1..3) {
                CircleButton(
                    modifier = Modifier.weight(1F),
                    text = number.toString(),
                    enabled = isKeyboardAvailable,
                    onClick = { onClick(number) }
                )
            }
        }
        Row {
            for (number in 4..6) {
                CircleButton(
                    modifier = Modifier.weight(1F),
                    text = number.toString(),
                    enabled = isKeyboardAvailable,
                    onClick = { onClick(number) }
                )
            }
        }
        Row {
            for (number in 7..9) {
                CircleButton(
                    modifier = Modifier.weight(1F),
                    text = number.toString(),
                    enabled = isKeyboardAvailable,
                    onClick = { onClick(number) }
                )
            }
        }
        Row {
            CircleButton(
                modifier = Modifier.weight(1F),
                enabled = isBiometricAvailable,
                contentPadding = PaddingValues(0.dp),
                onClick = onBiometricsClick
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    imageVector = Icons.Outlined.Fingerprint,
                    contentDescription = null
                )
            }

            CircleButton(
                modifier = Modifier.weight(1F),
                text = "0",
                enabled = isKeyboardAvailable,
                onClick = { onClick(0) }
            )

            CircleButton(
                modifier = Modifier.weight(1F),
                enabled = isBackspaceAvailable,
                contentPadding = PaddingValues(0.dp),
                onClick = onBackspace
            ) {
                Icon(
                    imageVector = Icons.Outlined.Backspace,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = String.EMPTY,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit = {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )
    }
) {
    FilledTonalButton(
        modifier = modifier
            .aspectRatio(1F)
            .padding(defaultPadding),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onClick,
        content = content
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinIndicatorRow(
    modifier: Modifier = Modifier,
    maxLength: Int = 4,
    currentLength: Int = 0,
    isError: Boolean = false
) {
    LazyRow(
        modifier = modifier
            .defaultMinSize(minHeight = 64.dp)
            .padding(defaultPadding / 2),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        items(maxLength) {
            PinIndicator(
                modifier = Modifier
                    .animateItemPlacement(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )
                    .padding(defaultPadding),
                isFilled = it + 1 in 0..currentLength,
                isError = isError
            )
        }
    }
}

@Composable
private fun PinIndicator(
    modifier: Modifier = Modifier,
    isFilled: Boolean = false,
    isError: Boolean = false
) {
    val color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    val boxModifier = if (isFilled) {
        modifier
            .padding(defaultPadding)
            .size(16.dp)
            .clip(CircleShape)
            .background(color)
    } else {
        modifier
            .padding(defaultPadding)
            .size(16.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = color,
                shape = CircleShape
            )
    }
    Box(
        modifier = boxModifier
    )
}

@Preview
@Composable
fun PinIndicatorPreview() {
    PinIndicatorRow(
        maxLength = 4,
        isError = false
    )
}

@Preview
@Composable
fun CircleButtonPreview() {
    MaterialTheme {
        CircleButton(
            text = "0"
        )
    }
}

@Preview
@Composable
fun PinCodePreview() {
    MaterialTheme {
        PinCodeBlock(
            onClick = {},
            onBiometricsClick = {},
            onBackspace = {}
        )
    }
}