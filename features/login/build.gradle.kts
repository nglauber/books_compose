plugins {
    id(Plugins.android_lib)
    id(Plugins.kotlin_android)
    id(Plugins.hilt_android)
    kotlin(Plugins.kotlin_kapt)
}

android {
    namespace = "com.nglauber.architecture_sample.login"
    compileSdk = Apps.compile_sdk_version

    defaultConfig {
        minSdk = Apps.min_sdk_version
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
}

dependencies {
    implementation(project(Libs.project_domain))
    implementation(project(Libs.project_core_android))
    implementation(Libs.activity_compose)
    implementation(Libs.core_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.hilt_navigation_compose)
    
    implementation(Libs.compose_ui)
    implementation(Libs.ui_tooling_preview)
    implementation(Libs.compose_material)

    implementation(Libs.accompanist_navigation_animation)

    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)

    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_auth_ktx)
    implementation(Libs.play_services_auth)
}