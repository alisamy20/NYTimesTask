pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NYTimesTask"
include(":app")
include(":core")
include(":core:common-kotlin")
include(":core:core-android")
include(":core:database")
include(":core:ui")
include(":features")
include(":features:home")
include(":features:home:data")
include(":features:home:domain")
include(":features:home:presentation")
include(":features")
include(":features:home")
include(":features:bookmark")
include(":features:bookmark:domain")
include(":features:bookmark:data")
include(":features:bookmark:presentation")
include(":features:details")
