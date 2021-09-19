plugins {
    id("com.gradle.enterprise").version("3.5")
}

include(":core-legacy")
include(":firebase-admin")
include(":firebase-database")
include(":firebase-firestore")
include(":logging")
include(":remote-config")
include(":repository")

apply(from = java.io.File(settingsDir, "buildCacheSettings.gradle"))
