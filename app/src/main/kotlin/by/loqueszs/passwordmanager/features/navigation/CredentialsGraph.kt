package by.loqueszs.passwordmanager.features.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import by.loqueszs.passwordmanager.features.credentials.presentation.addcredentials.components.AddCredentialsScreen
import by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.components.CredentialsDetailScreen
import by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.components.CredentialsType
import by.loqueszs.passwordmanager.features.credentials.presentation.listcredentials.components.CredentialsListScreen
import by.loqueszs.passwordmanager.features.navigation.CredentialsListDestinations.addCredentials
import by.loqueszs.passwordmanager.features.navigation.CredentialsListDestinations.credentialsDetail
import by.loqueszs.passwordmanager.features.navigation.CredentialsListDestinations.credentialsList
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.credentialsGraph(
    navController: NavHostController,
    onAuth: () -> Unit,
    onConfirmationResult: () -> Result<CredentialsType> = { Result.failure(Throwable()) }
) {
    credentialsList(
        navController = navController,
        onAuth = onAuth
    )
    addCredentials(
        navController = navController,
        onAuth = onAuth
    )
    credentialsDetail(
        navController = navController,
        onAuth = onAuth,
        onConfirmationResult = onConfirmationResult
    )
}

private object CredentialsListDestinations {

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.credentialsList(
        navController: NavHostController,
        onAuth: () -> Unit
    ) {
        composable(
            route = Destinations.CredentialsListScreen.route,
            enterTransition = {
                when {
                    initialState.destination.route == Destinations.AddCredentialsScreen.route -> {
                        fadeIn(tween(200))
                    }
                    initialState.destination.route?.contains(Destinations.CredentialsDetailScreen.route) == true -> {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right)
                    }
                    initialState.destination.route == Destinations.ProfileSettingsScreen.route -> {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                    }
                    else -> null
                }
            },
            exitTransition = {
                when {
                    targetState.destination.route == Destinations.AddCredentialsScreen.route -> {
                        fadeOut(tween(200))
                    }
                    targetState.destination.route?.contains(Destinations.CredentialsDetailScreen.route) == true -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left)
                    }
                    targetState.destination.route == Destinations.ProfileSettingsScreen.route -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
                    }
                    else -> null
                }
            }
        ) {
            CredentialsListScreen(
                onAuth = onAuth,
                onNavToDetail = { id ->
                    navController.navigate(
                        route = Destinations.CredentialsDetailScreen.routeWithIDArgument(id)
                    )
                },
                onNavToAddCredentialsScreen = {
                    navController.navigate(Destinations.AddCredentialsScreen.route)
                },
                onNavToProfile = {
                    navController.navigate(Destinations.ProfileSettingsScreen.route)
                }
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.addCredentials(
        navController: NavHostController,
        onAuth: () -> Unit
    ) {
        composable(
            route = Destinations.AddCredentialsScreen.route,
            enterTransition = {
                expandIn(expandFrom = Alignment.TopStart) + fadeIn()
            },
            exitTransition = {
                shrinkOut(shrinkTowards = Alignment.TopStart) + fadeOut()
            }
        ) {
            AddCredentialsScreen(
                onAuth = onAuth,
                onComplete = {
                    navController.popBackStack()
                }
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.credentialsDetail(
        navController: NavController,
        onAuth: () -> Unit,
        onConfirmationResult: () -> Result<CredentialsType>
    ) {
        composable(
            route = Destinations.CredentialsDetailScreen.ROUTE_WITH_ID_PLACEHOLDER,
            arguments = listOf(
                navArgument(name = Destinations.CredentialsDetailScreen.ID_ARGUMENT_NAME) {
                    type = Destinations.CredentialsDetailScreen.ID_ARGUMENT_TYPE
                    nullable = false
                }
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Up)
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
            }
        ) {
            CredentialsDetailScreen(
                id = it.arguments?.getLong(Destinations.CredentialsDetailScreen.ID_ARGUMENT_NAME) ?: 0L,
                onRequestConfirmation = {
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        set(AUTH_REQUEST_KEY, it)
                    }
                    Log.d(
                        "navigation",
                        "onRequestConfirmation: ${navController.currentBackStackEntry?.savedStateHandle?.get<String>(AUTH_REQUEST_KEY)}\n" +
                            "PreviousBackStackEntry: ${navController.currentBackStackEntry?.destination}"
                    )
                    navController.navigate(Destinations.AuthScreen.route)
                },
                onConfirmationResult = {
                    onConfirmationResult()
                },
                onAuth = onAuth
            )
        }
    }
}