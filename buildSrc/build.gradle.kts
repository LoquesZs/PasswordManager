plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("bundleReleaseFiles") {
            // id that will be used to identify the Plugin and apply it in a module
            id = "by.loqueszs.bundle_release_files"
            implementationClass = "BundleReleaseFilesPlugin"
        }
    }
}