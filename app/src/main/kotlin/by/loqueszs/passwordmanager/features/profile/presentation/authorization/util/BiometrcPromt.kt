package by.loqueszs.passwordmanager.features.profile.presentation.authorization.util

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import by.loqueszs.passwordmanager.R

fun showBiometricPrompt(
    activity: FragmentActivity,
    onSuccess: () -> Unit,
    onError: (errorCode: Int, errorString: CharSequence) -> Unit,
    onFail: () -> Unit
) {
    val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle(activity.getString(R.string.biometric_fingerprint_title))
        .setSubtitle(activity.getString(R.string.biometric_fingerprint_subtitle))
        .setNegativeButtonText(activity.getString(R.string.biometric_cancel_button_text))
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        .build()
    val biometricPrompt = BiometricPrompt(
        activity,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errorCode, errString)
                Log.d("BIOMETRIC PROMPT", "$errorCode $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFail()
            }
        }
    )
    biometricPrompt.authenticate(info)
}

// object BiometricPromptHolder : BiometricPrompt.AuthenticationCallback() {
//
//    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//
//    }
//
//    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//
//    }
//
//    override fun onAuthenticationFailed() {
//
//    }
//
//    fun showBiometricPrompt(activity: FragmentActivity) {
//
//        val info = BiometricPrompt.PromptInfo.Builder()
//            .setTitle(activity.getString(R.string.biometric_fingerprint_title))
//            .setSubtitle(activity.getString(R.string.biometric_fingerprint_subtitle))
//            .setNegativeButtonText(activity.getString(R.string.biometric_cancel_button_text))
//            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
//            .build()
//
//        BiometricPrompt(activity, this).authenticate(info)
//    }
// }
