package by.loqueszs.passwordmanager.features.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import by.loqueszs.passwordmanager.features.credentials.presentation.detailcredentials.components.CredentialsType
import com.google.accompanist.navigation.animation.AnimatedNavHost

internal const val AUTH_RESULT_KEY = "auth_result"
internal const val AUTH_REQUEST_KEY = "auth_request"
internal const val AUTH_REQUEST_LOGIN = "login"
internal const val AUTH_REQUEST_PASSWORD = "password"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SingleNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Destinations.CredentialsListScreen.route,
        route = Destinations.Root.route,
        modifier = Modifier
    ) {
        profileGraph(
            navController = navController,
            onAuthSuccess = {
                Log.d("navigation", "Request: ${navController.previousBackStackEntry?.destination?.route}")
                when {
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>(AUTH_REQUEST_KEY) == AUTH_REQUEST_LOGIN -> {
                        navController.previousBackStackEntry?.savedStateHandle?.remove<String>(AUTH_REQUEST_KEY)
                        Log.d("navigation", "Request to copy login")
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set(AUTH_RESULT_KEY, AUTH_REQUEST_LOGIN)
                        }
                        navController.popBackStack()
                    }

                    navController.previousBackStackEntry?.savedStateHandle?.get<String>(AUTH_REQUEST_KEY) == AUTH_REQUEST_PASSWORD -> {
                        navController.previousBackStackEntry?.savedStateHandle?.remove<String>(AUTH_REQUEST_KEY)
                        Log.d("navigation", "Request to copy password")
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set(AUTH_RESULT_KEY, AUTH_REQUEST_PASSWORD)
                        }
                        navController.popBackStack()
                    }

                    navController.previousBackStackEntry == null -> {
                        Log.d("navigation", "Auth from nowhere")
                        navController.navigate(Destinations.CredentialsListScreen.route)
                    }

                    else -> {
                        Log.d("navigation", "Success auth from: ${navController.previousBackStackEntry?.destination?.route}")
                        navController.popBackStack()
                    }
                }
            }
        )
        credentialsGraph(
            navController = navController,
            onAuth = {
                Log.d("navigation", "LoggedIn: $isLoggedIn")
                if (!isLoggedIn && navController.currentBackStackEntry?.destination?.route != Destinations.AuthScreen.route) {
                    navController.navigate(Destinations.AuthScreen.route)
                }
            },
            onConfirmationResult = {
                val result = navController.currentBackStackEntry?.savedStateHandle?.remove<String>(AUTH_RESULT_KEY)
                Log.d("navigation", "Result: $result")
                when (result) {
                    AUTH_REQUEST_LOGIN -> {
                        Result.success(CredentialsType.Login)
                    }
                    AUTH_REQUEST_PASSWORD -> {
                        Result.success(CredentialsType.Password)
                    }
                    else -> {
                        Result.failure(Throwable())
                    }
                }
            }
        )
    }
}
