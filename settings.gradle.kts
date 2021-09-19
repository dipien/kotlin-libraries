plugins {
    id("com.gradle.enterprise").version("3.7")
}

if (System.getenv("CI") == "true") {
    buildCache {
        local {
            directory = File(System.getProperty("user.home"), "/gradle-build-cache")
        }
    }
}

include(":core-legacy")
include(":firebase-admin")
include(":firebase-database")
include(":firebase-firestore")
include(":logging")
include(":remote-config")
include(":repository")
