import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

internal abstract class BundleReleaseFilesTask : DefaultTask() {

    /**
     *  @get:Input, @get: InputDirectory, @get:OutputDirectory
     *  used to mark which parameters have an impact on the Task output
     *  The main goal of them is to skip the Task execution if the output already exists
     *  and inputs didn't change (https://docs.gradle.org/current/userguide/incremental_build.html).
     *  When you execute a Task and it's marked UP-TO-DATE, it means it wasn't executed
     *  because the output would have been the same as the existing one.
     *
     *  All this parameters aren't primitives but are Properties.
     *  A Property is lazy, the computation of it's value is delayed until it's used.
     *
     *  To have a mutable Property, a variable has to be abstract.
     *  This explains why your Task is an abstract class. Gradle is in charge of providing
     *  an implementation of your Task that creates the Properties.
     * */

    @get:InputDirectory
    abstract val rootProject: DirectoryProperty

    @get:Input
    abstract val appVersion: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    // Gradle calls this method when the Task is executed
    @TaskAction
    fun run() {
        check(rootProject.get().asFile.exists())

        val buildOutputDirectory = File(rootProject.get().asFile, "build/outputs")
        if (!buildOutputDirectory.exists()) {
            throw IllegalStateException("File doesn't exists: ${buildOutputDirectory.absolutePath}")
        }

        val resultDirectory = File(outputDirectory.get().asFile, appVersion.get())

        if (resultDirectory.exists()) {
            if (!resultDirectory.deleteRecursively()) {
                throw IllegalStateException("Output directory deletion failed at path: ${resultDirectory.absolutePath}")
            }
        }

        if (!resultDirectory.mkdirs()) {
            throw IllegalStateException("Error when creating output directory at path: ${resultDirectory.absolutePath}")
        }

        val apkReleaseDirectory = File(buildOutputDirectory, "apk/release")
        val apkReleaseFile = apkReleaseDirectory.listFiles().first { it.extension == "apk" }
        apkReleaseFile.copyTo(File(resultDirectory, apkReleaseFile.name))

        val bundleReleaseDirectory = File(buildOutputDirectory, "bundle/release")
        val bundleReleaseFile = bundleReleaseDirectory.listFiles().first { it.extension == "aab" }
        apkReleaseFile.copyTo(File(resultDirectory, bundleReleaseFile.name))

        val mappingReleaseFile = File(buildOutputDirectory, "mapping/release/mapping.txt")
        mappingReleaseFile.copyTo(File(resultDirectory, mappingReleaseFile.name))
    }
}