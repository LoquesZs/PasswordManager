package by.loqueszs.passwordmanager.features.credentials.data.datasource.database.util

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PassphraseManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : PassphraseManager {
    companion object {
        private const val PASSPHRASE_LENGTH = 32
        private const val PASSPHRASE_FILE_NAME = "passphrase.bin"
    }
    override fun getPassphrase(): ByteArray {
        val file = File(context.filesDir, PASSPHRASE_FILE_NAME)
        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        return if (file.exists()) {
            encryptedFile.openFileInput().use { it.readBytes() }
        } else {
            generatePassphrase().also { passphrase ->
                encryptedFile.openFileOutput().use { it.write(passphrase) }
            }
        }
    }

    private fun generatePassphrase(): ByteArray {
        val random = SecureRandom.getInstanceStrong()
        val result = ByteArray(PASSPHRASE_LENGTH)

        random.nextBytes(result)
        while (result.contains(0)) {
            random.nextBytes(result)
        }
        return result
    }
}