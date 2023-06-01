plugins {
    id(Plugins.android_app)
    id(Plugins.kotlin_android)
    id(Plugins.hilt_android)
    id(Plugins.google_services)
    kotlin(Plugins.kotlin_kapt)
}

android {
    namespace = "com.nglauber.architecture_sample"
    compileSdk = Apps.compile_sdk_version

    defaultConfig {
        applicationId = Apps.application_id
        minSdk = Apps.min_sdk_version
        targetSdk = Apps.target_sdk_version
        versionCode = Apps.version_code
        versionName = Apps.version_name

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Libs.project_core_android))
    implementation(project(Libs.project_domain))
    implementation(project(Libs.project_data_local))
    implementation(project(Libs.project_data_firebase))
    implementation(project(Libs.project_feature_login))
    implementation(project(Libs.project_feature_books))
    implementation(project(Libs.project_feature_settings))

    implementation(Libs.core_ktx)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.activity_compose)
    implementation(Libs.compose_ui)
    implementation(Libs.ui_tooling_preview)
    implementation(Libs.material)

    implementation(Libs.accompanist_navigation_animation)

    implementation(Libs.hilt_android)
    kapt(AnnotationsProcessor.hilt_android_compile)
    implementation(Libs.hilt_navigation_compose)

    implementation(Libs.play_services_auth)

    debugImplementation(Libs.ui_tooling)
    debugImplementation(Libs.ui_test_manifest)
}