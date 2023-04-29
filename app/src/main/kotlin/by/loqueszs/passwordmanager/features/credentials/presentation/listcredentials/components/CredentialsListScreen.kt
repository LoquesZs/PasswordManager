package by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.components

import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.loqueszs.passwordmanager.R
import by.loqueszs.passwordmanager.common.ui.theme.PassManagerTheme
import by.loqueszs.passwordmanager.common.ui.util.defaultPadding
import by.loqueszs.passwordmanager.features.credentials.domain.model.CredentialsUIModel
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.CredentialsEvent
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.viewmodel.CredentialsListViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsListScreen(
    onAuth: () -> Unit,
    onNavToDetail: (id: Long) -> Unit,
    onNavToAddCredentialsScreen: () -> Unit,
    onNavToProfile: () -> Unit,
    viewModel: CredentialsListViewModel = hiltViewModel()
) {
    onAuth()

    val items = viewModel.state.value.credentials
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    var filteringVisibility by remember { mutableStateOf(false) }

    Column {
        MediumTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        scope.launch {
                            onNavToProfile()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        filteringVisibility = !filteringVisibility
                    }
                ) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
                }
            },
            windowInsets = WindowInsets.statusBars,
            scrollBehavior = scrollBehavior
        )

        Box {
            with(items) {
                when {
                    this == null -> {
                        LoadingState()
                    }

                    this.isEmpty() -> {
                        EmptyState(
                            onNavToAddCredentialsScreen = onNavToAddCredentialsScreen
                        )
                    }

                    else -> {
                        NotEmptyState(
                            scrollBehavior = scrollBehavior,
                            items = this,
                            viewModel = viewModel,
                            onNavToDetail = onNavToDetail,
                            onNavToAddCredentialsScreen = onNavToAddCredentialsScreen
                        )
                    }
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                enter = expandVertically(),
                exit = shrinkVertically(),
                visible = filteringVisibility
            ) {
                CredentialsFiltering(
                    order = viewModel.state.value.credentialsOrder,
                    onClick = {
                        viewModel.onEvent(CredentialsEvent.Order(it))
                    }
                )
            }
        }
    }
}

@Composable
fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(text = stringResource(R.string.loading))
    }
}

@Composable
fun EmptyState(
    onNavToAddCredentialsScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AddFab(
            onClick = onNavToAddCredentialsScreen
        )
        Text(
            text = stringResource(R.string.empty_credentials_list),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotEmptyState(
    scrollBehavior: TopAppBarScrollBehavior,
    items: List<CredentialsUIModel>,
    viewModel: CredentialsListViewModel,
    onNavToDetail: (id: Long) -> Unit,
    onNavToAddCredentialsScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        CredentialsList(
            items = items,
            onNavToDetail = { id: Long ->
                onNavToDetail(id)
            },
            onAddToFavorites = { id: Long ->
                viewModel.onEvent(CredentialsEvent.AddToFavorites(id))
            },
            onDeleteCredentials = { id: Long ->
                viewModel.onEvent(CredentialsEvent.DeleteCredentials(id))
            }
        )
        AddFab(
            modifier = Modifier
                .systemBarsPadding()
                .align(Alignment.BottomEnd),
            onClick = onNavToAddCredentialsScreen
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CredentialsList(
    items: List<CredentialsUIModel>,
    onNavToDetail: (id: Long) -> Unit,
    onAddToFavorites: suspend (id: Long) -> Unit,
    onDeleteCredentials: suspend (id: Long) -> Unit
) {
    val composableScope = rememberCoroutineScope()
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val snackMessage = stringResource(id = R.string.invalid_url_message)
    val snackActionLabel = stringResource(id = R.string.invalid_url_action_label)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                start = defaultPadding * 2,
                top = defaultPadding * 2,
                end = defaultPadding * 2,
                bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).dp / 2
            )
        ) {
            items(
                items = items,
                key = { it.id }
            ) { credentials: CredentialsUIModel ->
                CredentialsCard(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    onShowSnackBar = {
                        snackbarHostState.showSnackbar(
                            message = snackMessage,
                            actionLabel = snackActionLabel,
                            duration = SnackbarDuration.Short
                        )
                    },
                    item = credentials,
                    onClick = {
                        onNavToDetail(credentials.id)
                    },
                    onAddToFavorites = {
                        composableScope.launch {
                            onAddToFavorites(credentials.id)
                        }
                    },
                    onDelete = {
                        composableScope.launch {
                            onDeleteCredentials(credentials.id)
                        }
                    }
                )
            }
        }
        SnackbarHost(
            hostState = snackbarHostState
        ) {
            Snackbar(snackbarData = it)
        }
    }
}

@Composable
fun AddFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .padding(defaultPadding),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(defaultPadding * 2),
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(end = defaultPadding * 2),
                text = stringResource(R.string.create)
            )
        }
    }
}

@Preview
@Composable
fun CredentialsListPreview() {
    PassManagerTheme {
        CredentialsList(
            items = List(10) {
                CredentialsUIModel(
                    id = 0,
                    title = "TITLE",
                    dateLong = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                    login = "LOGIN",
                    password = "PASSWORD"
                )
            },
            onNavToDetail = {},
            onAddToFavorites = {},
            onDeleteCredentials = {}
        )
    }
}

@Preview
@Composable
fun CredentialsCardPreview() {
    PassManagerTheme {
        CredentialsCard(
            item = CredentialsUIModel(
                title = "TITLE",
                dateLong = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                login = "LOGIN",
                password = "PASSWORD"
            ),
            onShowSnackBar = { SnackbarResult.ActionPerformed }
        )
    }
}