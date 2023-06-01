plugins {
    id(Plugins.android_lib)
    id(Plugins.kotlin_android)
    id(Plugins.hilt_android)
    kotlin(Plugins.kotlin_kapt)
}

android {
    namespace = "com.nglauber.architecture_sample.books"
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
        sourceCompatibility(Apps.java_compatibility_version)
        targetCompatibility(Apps.java_compatibility_version)
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
    packaging {
        resources {
            excludes += "/META-INF/com.google.dagger_dagger.version"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(project(Libs.project_core_android))
    implementation(project(Libs.project_domain))

    implementation(Libs.activity_compose)
    implementation(Libs.core_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.hilt_navigation_compose)

    implementation(Libs.compose_ui)
    implementation(Libs.ui_tooling_preview)
    implementation(Libs.compose_material)

    implementation(Libs.accompanist_swipe_refresh)
    implementation(Libs.accompanist_navigation_animation)

    implementation(Libs.coil_compose)
    implementation(Libs.reveal_swipe)

    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)

    androidTestImplementation(TestLibs.ext_junit)
    androidTestImplementation(TestLibs.espresso_core)
    androidTestImplementation(TestLibs.compose_ui_test_junit)
    androidTestImplementation(TestLibs.mockk)
    androidTestImplementation(TestLibs.mockk_android)
    androidTestImplementation(TestLibs.kotlin_coroutines_test)

    androidTestImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.ext_junit_ktx)
    androidTestImplementation(TestLibs.arch_core_ktx)
    androidTestImplementation(TestLibs.kotlin_reflect)
    androidTestImplementation(TestLibs.mockk_agent_jvm)
}