package by.loqueszs.passwordmanager.common

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import by.loqueszs.passwordmanager.common.ui.theme.PassManagerTheme
import by.loqueszs.passwordmanager.features.navigation.SingleNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            val loginState by viewModel.isLoggedIn.collectAsState()

            navController.addOnDestinationChangedListener { controller, dest, args ->
                Log.d(
                    "Navigation",
                    "=========START NAVIGATION EVENT========\n" +
                        "From: ${controller.previousBackStackEntry?.destination?.route}\n" +
                        "To: ${dest.route}\n" +
                        "Args: $args"
                )
            }

            PassManagerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SingleNavHost(
                        navController = navController,
                        isLoggedIn = loginState
                    )
                }
            }
        }
    }

    override fun onStop() {
        viewModel.logOut()
        super.onStop()
    }
}
