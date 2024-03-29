pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "architecture_sample"
include(":app")
include(":domain")
include(":core")
include(":core_android")
include(":data_local")
include(":data_firebase")
include(":features:books")
include(":features:login")
include(":features:settings")
