package by.loqueszs.passwordmanager

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import by.loqueszs.passwordmanager.features.credentials.data.datasource.database.CredentialsDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test"

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        CredentialsDatabase::class.java
    )

    @Test
    fun testMigrations() {
        helper.createDatabase(DB_NAME, 2).apply {
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            CredentialsDatabase::class.java,
            DB_NAME
        ).addMigrations(CredentialsDatabase.MIGRATION_2_3).build().apply {
            close()
        }
    }
}
