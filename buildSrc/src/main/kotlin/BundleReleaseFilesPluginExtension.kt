import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

interface BundleReleaseFilesPluginExtension {
    val appVersion: Property<String>
    val outputDirectory: DirectoryProperty
}