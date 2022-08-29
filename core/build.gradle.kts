plugins {
    id(Plugins.java_lib)
    id(Plugins.kotlin_jvm)
}

java {
    sourceCompatibility = Apps.java_compatibility_version
    targetCompatibility = Apps.java_compatibility_version
}

dependencies {
    implementation(Libs.kotlinx_coroutines_core)
}