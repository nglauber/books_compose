plugins {
    id(Plugins.android_lib)
    id(Plugins.kotlin_android)
}

android {
    namespace = "com.nglauber.architecture_sample.datafb"
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
    implementation(Libs.core_ktx)
    implementation(Libs.kotlinx_coroutines_core)

    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_core)
    implementation(Libs.firebase_firestore)
    implementation(Libs.firebase_storage)
    implementation(Libs.firebase_auth)
    implementation(Libs.exif_interface)
}