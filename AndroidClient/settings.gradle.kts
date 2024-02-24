pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.greenrobot.greendao") {
                useModule("org.greenrobot:greendao-gradle-plugin:3.3.0")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidClient"
include(":app")
 