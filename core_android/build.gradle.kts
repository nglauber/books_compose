plugins {
    id(Plugins.android_lib)
    id(Plugins.kotlin_android)
}

android {
    namespace = "com.nglauber.architecture_sample.core_android"
    compileSdk = Apps.compile_sdk_version

    defaultConfig {
        minSdk = Apps.min_sdk_version
        testInstrumentationRunner = Apps.test_instrumentation_runner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Apps.java_compatibility_version
        targetCompatibility = Apps.java_compatibility_version
    }
    kotlinOptions {
        jvmTarget = Apps.jvm_target_version
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlin_compiler_ext_version
    }
}

dependencies {
    api(project(Libs.project_core))
    implementation(Libs.core_ktx)
    implementation(Libs.compose_ui)
    implementation(Libs.ui_tooling_preview)
    implementation(Libs.compose_material)

    implementation(Libs.accompanist_navigation_animation)
    implementation(Libs.app_compat)

    debugImplementation(Libs.ui_tooling)
    debugImplementation(Libs.ui_test_manifest)
    implementation(Libs.tracing)
    androidTestImplementation(TestLibs.ext_junit)
    androidTestImplementation(TestLibs.espresso_core)
    androidTestImplementation(TestLibs.compose_ui_test_junit)
}