package by.loqueszs.passwordmanager.features.navigation

import androidx.navigation.NavType

sealed class Destinations(val route: String) {
    companion object {
        private const val ROOT = "root"
        private const val ADD_CREDENTIALS_SCREEN = "add_credentials_screen"
        private const val CREDENTIALS_LIST_SCREEN = "credentials_list_screen"
        private const val CREDENTIALS_DETAIL_SCREEN = "credentials_detail_screen"
        private const val AUTH_SCREEN = "auth_screen"
        private const val PROFILE_SETTINGS_SCREEN = "profile_settings_screen"
        private const val CREATE_PROFILE_SCREEN = "create_profile_screen"
    }
    object Root : Destinations(ROOT)
    object AddCredentialsScreen : Destinations(ADD_CREDENTIALS_SCREEN)
    object CredentialsListScreen : Destinations(CREDENTIALS_LIST_SCREEN)
    object CredentialsDetailScreen : Destinations(CREDENTIALS_DETAIL_SCREEN) {

        const val ID_ARGUMENT_NAME = "id"
        val ID_ARGUMENT_TYPE = NavType.LongType

        val ROUTE_WITH_ID_PLACEHOLDER = "${this.route}/{$ID_ARGUMENT_NAME}"
        fun routeWithIDArgument(id: Long) = "${this.route}/$id"
    }
    object AuthScreen : Destinations(AUTH_SCREEN)
    object ProfileSettingsScreen : Destinations(PROFILE_SETTINGS_SCREEN)
    object CreateProfileScreen : Destinations(CREATE_PROFILE_SCREEN)
}