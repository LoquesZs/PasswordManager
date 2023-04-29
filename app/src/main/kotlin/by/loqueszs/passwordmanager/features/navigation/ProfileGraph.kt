package by.loqueszs.passwordmanager.features.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import by.loqueszs.passwordmanager.features.navigation.ProfileListDestinations.authScreen
import by.loqueszs.passwordmanager.features.navigation.ProfileListDestinations.createProfile
import by.loqueszs.passwordmanager.features.navigation.ProfileListDestinations.profileSettingsScreen
import by.loqueszs.passwordmanager.features.profile.presentation.authorization.components.AuthScreen
import by.loqueszs.passwordmanager.features.profile.presentation.createprofile.components.CreateProfileScreen
import by.loqueszs.passwordmanager.features.profile.presentation.profilescreen.components.ProfileScreen
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    onAuthSuccess: () -> Unit
) {
    authScreen(
        onAuthSuccess = onAuthSuccess,
        onNavToCreateAccount = {
            navController.navigate(Destinations.CreateProfileScreen.route)
        }
    )
    profileSettingsScreen()
    createProfile(
        onComplete = {
            navController.popBackStack()
        }
    )
}

private object ProfileListDestinations {

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.authScreen(
        onAuthSuccess: () -> Unit = {},
        onNavToCreateAccount: () -> Unit = {}
    ) {
        composable(
            route = Destinations.AuthScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
            }
        ) {
            AuthScreen(
                onAuthSuccess = {
                    onAuthSuccess()
                },
                onNavToCreateAccount = {
                    onNavToCreateAccount()
                }
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.profileSettingsScreen() {
        composable(
            route = Destinations.ProfileSettingsScreen.route
        ) {
            ProfileScreen()
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.createProfile(
        onComplete: () -> Unit
    ) {
        composable(
            route = Destinations.CreateProfileScreen.route
        ) {
            CreateProfileScreen(onComplete = onComplete)
        }
    }
}