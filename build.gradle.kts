buildscript {
    repositories {
        google()  // Google's Maven repository
    }
    dependencies {
        classpath(BuildDependencies.GoogleServices)
        classpath(BuildDependencies.Hilt)
    }
}
plugins {
    id(Plugins.android_app) version Versions.android_plugin_id apply false
    id(Plugins.android_lib) version Versions.android_plugin_id apply false
    id(Plugins.kotlin_android) version Versions.kotlin_version apply false
    id(Plugins.kotlin_jvm) version Versions.kotlin_version apply false
}