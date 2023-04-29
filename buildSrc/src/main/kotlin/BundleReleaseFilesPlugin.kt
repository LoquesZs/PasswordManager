import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  Plugin <T> is the interface we used to implement to create a Plugin. The apply
 *  method has to be overriden. It will contain the whole logic. This method is
 *  called when th PLugin is applied.
 *
 *  T can be of several types Project, Settings, Gradle. We use Project 'cause we
 *  want to add our Task to an Android Application module, so we will apply the Plugin
 *  in the module-lever build.gradle.kts
 * */

class BundleReleaseFilesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create(
            "bundleReleaseFiles",
            BundleReleaseFilesPluginExtension::class.java
        )
        val bundleReleaseFiles = target.tasks.register(
            "bundleReleaseFiles",
            BundleReleaseFilesTask::class.java
        ) {
            group = "my_plugins"
            description = "Bundle release files (.apk, .aab and mappings) in the same directory"
        }

        bundleReleaseFiles.configure {
            appVersion.set(extension.appVersion)
            rootProject.set(target.projectDir)
            outputDirectory.set(extension.outputDirectory)
        }
    }
}