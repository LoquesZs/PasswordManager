package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.TextIncrease
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.credentials.domain.util.CredentialsOrder
import by.loqueszs.passwordmanager.features.credentials.domain.util.OrderType

@Composable
fun CredentialsFiltering(
    order: CredentialsOrder,
    onClick: (CredentialsOrder) -> Unit
) {
    Surface(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Divider(modifier = Modifier.height(2.dp))
            OrderTypeRow(order = order, onClick = onClick)
            Divider(modifier = Modifier.padding(horizontal = defaultPadding * 2))
            CredentialsFieldOrderRow(order = order, onClick = onClick)
            Divider(modifier = Modifier.height(2.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTypeRow(
    order: CredentialsOrder,
    onClick: (CredentialsOrder) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(defaultPadding),
                imageVector = Icons.Default.Sort,
                contentDescription = null
            )
            Text("Order type")
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterChip(
                modifier = Modifier.padding(defaultPadding),
                selected = (order.orderType is OrderType.Ascending),
                label = { Text("Ascending") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
                },
                onClick = {
                    onClick(order.copy(OrderType.Ascending))
                }
            )

            FilterChip(
                modifier = Modifier.padding(defaultPadding),
                selected = (order.orderType is OrderType.Descending),
                label = { Text("Descending") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null)
                },
                onClick = {
                    onClick(order.copy(OrderType.Descending))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsFieldOrderRow(
    order: CredentialsOrder,
    onClick: (CredentialsOrder) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(defaultPadding),
                imageVector = Icons.Outlined.Category,
                contentDescription = null
            )
            Text("Credentials type")
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(defaultPadding))
            FilterChip(
                selected = (order is CredentialsOrder.Date),
                label = { Text("Date") },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.CalendarToday, contentDescription = null)
                },
                onClick = {
                    onClick(CredentialsOrder.Date(order.orderType))
                }
            )
            Spacer(modifier = Modifier.width(defaultPadding))
            FilterChip(
                selected = (order is CredentialsOrder.Title),
                label = { Text("Title") },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.TextIncrease, contentDescription = null)
                },
                onClick = {
                    onClick(CredentialsOrder.Title(order.orderType))
                }
            )
            Spacer(modifier = Modifier.width(defaultPadding))
            FilterChip(
                selected = (order is CredentialsOrder.Favorites),
                label = { Text("Favorites") },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null)
                },
                onClick = {
                    onClick(CredentialsOrder.Favorites(order.orderType))
                }
            )
            Spacer(modifier = Modifier.width(defaultPadding))
        }
    }
}

@Preview
@Composable
fun OrderTypePreview() {
    var credentialsOrder: CredentialsOrder by remember {
        mutableStateOf(CredentialsOrder.Title(OrderType.Ascending))
    }
    OrderTypeRow(
        order = credentialsOrder,
        onClick = { order ->
            credentialsOrder = order
        }
    )
}

@Preview
@Composable
fun CredentialsFieldRowPreview() {
    var credentialsOrder: CredentialsOrder by remember {
        mutableStateOf(CredentialsOrder.Date(OrderType.Ascending))
    }

    CredentialsFieldOrderRow(
        order = credentialsOrder,
        onClick = { order ->
            credentialsOrder = order
        }
    )
}

@Preview
@Composable
fun FilteringPreview() {
    Column {
        OrderTypePreview()
        Divider(
            modifier = Modifier.padding(horizontal = defaultPadding * 2)
        )
        CredentialsFieldRowPreview()
    }
}