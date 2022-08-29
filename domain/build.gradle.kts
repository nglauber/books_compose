plugins {
    id(Plugins.java_lib)
    id(Plugins.kotlin_jvm)
    kotlin(Plugins.kotlin_kapt)
}

java {
    sourceCompatibility = Apps.java_compatibility_version
    targetCompatibility = Apps.java_compatibility_version
}

dependencies {
    api(project(Libs.project_core))

    implementation(Libs.kotlinx_coroutines_core)

    implementation(Libs.hilt_core)
    kapt(Libs.hilt_compiler)

    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.mockk)
}