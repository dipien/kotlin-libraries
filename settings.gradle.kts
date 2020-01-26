plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

include(":jdroid-java-core")
include(":jdroid-java-firebase-database")
include(":jdroid-java-remote-config")

apply(from = java.io.File(settingsDir, "buildCacheSettings.gradle"))
