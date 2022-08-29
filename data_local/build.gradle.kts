plugins {
    id(Plugins.android_lib)
    id(Plugins.kotlin_android)
    kotlin(Plugins.kotlin_kapt)
    id(Plugins.hilt_android)
}

android {
    namespace = "com.nglauber.architecture_sample.data_local"
    compileSdk = Apps.compile_sdk_version

    defaultConfig {
        minSdk = Apps.min_sdk_version
        targetSdk = Apps.target_sdk_version

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
}

dependencies {
    implementation(project(Libs.project_domain))

    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)

    testImplementation(TestLibs.room)

    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)

    androidTestImplementation(TestLibs.ext_junit)
    androidTestImplementation(TestLibs.arch_core)
    androidTestImplementation(TestLibs.test_runner)
}