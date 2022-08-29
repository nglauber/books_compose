import Versions.accompanist_navigation_animation_version
import Versions.accompanist_swiperefresh_version
import Versions.activity_compose_version
import Versions.androidx_exif_version
import Versions.androidx_test_ext_kotlin_runner_version
import Versions.androidx_text_core_version
import Versions.app_compat_version
import Versions.coil_version
import Versions.compose_ui_version
import Versions.core_ktx_version
import Versions.core_testing_version
import Versions.coroutines_version
import Versions.espresso_core_version
import Versions.firebase_bom_version
import Versions.google_services_version
import Versions.hilt_nav_compose_version
import Versions.hilt_version
import Versions.junit_version
import Versions.kotlin_version
import Versions.lifecycle_runtime_version
import Versions.lifecycle_viewmodel_version
import Versions.material_compose
import Versions.mockk_version
import Versions.playservices_version
import Versions.reveal_swipe_version
import Versions.room_version
import Versions.test_ext_version
import Versions.test_runner_version
import org.gradle.api.JavaVersion

object Plugins {
    const val android_app = "com.android.application"
    const val android_lib = "com.android.library"
    const val google_services = "com.google.gms.google-services"
    const val hilt_android = "dagger.hilt.android.plugin"
    const val java_lib = "java-library"
    const val kotlin_android = "org.jetbrains.kotlin.android"
    const val kotlin_jvm = "org.jetbrains.kotlin.jvm"
    const val kotlin_kapt = "kapt"
}

object Apps {
    const val application_id = "com.nglauber.architecture_sample"
    const val compile_sdk_version = 32
    const val min_sdk_version = 24
    const val target_sdk_version = 32

    const val version_code = 1
    const val version_name = "1.0.0"

    const val jvm_target_version = "1.8"
    val java_compatibility_version = JavaVersion.VERSION_1_8

    const val test_instrumentation_runner = "androidx.test.runner.AndroidJUnitRunner"
}

object Versions {
    const val android_plugin_id = "7.2.2"
    const val accompanist_navigation_animation_version = "0.24.12-rc"
    const val accompanist_swiperefresh_version = "0.24.12-rc"
    const val activity_compose_version = "1.6.0-alpha05"
    const val androidx_exif_version = "1.3.3"
    const val androidx_text_core_version = "1.4.0"
    const val androidx_test_ext_kotlin_runner_version = "1.1.3"
    const val app_compat_version = "1.5.0"
    const val coil_version = "2.0.0-rc01"
    const val compose_ui_version = "1.2.0"
    const val core_ktx_version = "1.7.0"
    const val core_testing_version = "2.1.0"
    const val coroutines_version = "1.6.0"
    const val espresso_core_version = "3.4.0"
    const val firebase_bom_version = "29.1.0"
    const val google_services_version = "4.3.13"
    const val hilt_version = "2.42"
    const val hilt_nav_compose_version = "1.0.0"
    const val junit_version = "4.13.2"
    const val kotlin_version = "1.7.0"
    const val kotlin_compiler_ext_version = "1.2.0"
    const val lifecycle_runtime_version = "2.4.1"
    const val lifecycle_viewmodel_version = "2.4.1"
    const val material_compose = "1.2.0-alpha05"
    const val material_android_version = "1.5.0"
    const val mockk_version = "1.12.2"
    const val playservices_version = "20.1.0"
    const val reveal_swipe_version = "1.0.0"
    const val room_version = "2.4.3"
    const val test_ext_version = "1.1.3"
    const val test_runner_version = "1.4.0"
}

object BuildDependencies {
    const val GoogleServices = "com.google.gms:google-services:$google_services_version"
    const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
}

object Libs {
    // Projects
    const val project_core = ":core"
    const val project_core_android = ":core_android"
    const val project_domain = ":domain"
    const val project_data_local = ":data_local"
    const val project_data_firebase = ":data_firebase"
    const val project_feature_login = ":features:login"
    const val project_feature_books = ":features:books"
    const val project_feature_settings = ":features:settings"

    // Libs
    const val accompanist_navigation_animation =
        "com.google.accompanist:accompanist-navigation-animation:$accompanist_navigation_animation_version"
    const val accompanist_swipe_refresh =
        "com.google.accompanist:accompanist-swiperefresh:$accompanist_swiperefresh_version"
    const val activity_compose =
        "androidx.activity:activity-compose:$activity_compose_version"
    const val app_compat =
        "androidx.appcompat:appcompat:$app_compat_version"
    const val coil_compose =
        "io.coil-kt:coil-compose:$coil_version"
    const val compose_ui =
        "androidx.compose.ui:ui:$compose_ui_version"
    const val compose_material =
        "androidx.compose.material:material:$material_compose"
    const val core_ktx =
        "androidx.core:core-ktx:$core_ktx_version"
    const val exif_interface =
        "androidx.exifinterface:exifinterface:$androidx_exif_version"
    const val firebase_bom =
        "com.google.firebase:firebase-bom:$firebase_bom_version"
    const val firebase_core =
        "com.google.firebase:firebase-core"
    const val firebase_firestore =
        "com.google.firebase:firebase-firestore"
    const val firebase_storage =
        "com.google.firebase:firebase-storage"
    const val firebase_auth =
        "com.google.firebase:firebase-auth"
    const val firebase_auth_ktx =
        "com.google.firebase:firebase-auth-ktx"
    const val hilt_android =
        "com.google.dagger:hilt-android:$hilt_version"
    const val hilt_android_compiler =
        "com.google.dagger:hilt-android-compiler:$hilt_version"
    const val hilt_compiler =
        "com.google.dagger:hilt-compiler:$hilt_version"
    const val hilt_core =
        "com.google.dagger:hilt-core:$hilt_version"
    const val hilt_navigation_compose =
        "androidx.hilt:hilt-navigation-compose:$hilt_nav_compose_version"
    const val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_runtime_version"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_viewmodel_version"
    const val material =
        "androidx.compose.material:material:$material_compose"
    const val play_services_auth =
        "com.google.android.gms:play-services-auth:$playservices_version"
    const val reveal_swipe =
        "de.charlex.compose:revealswipe:$reveal_swipe_version"
    const val room_compiler =
        "androidx.room:room-compiler:$room_version"
    const val room_ktx =
        "androidx.room:room-ktx:$room_version"
    const val room_runtime =
        "androidx.room:room-runtime:$room_version"
    const val ui_tooling_preview =
        "androidx.compose.ui:ui-tooling-preview:$compose_ui_version"

    // Debug
    const val ui_tooling =
        "androidx.compose.ui:ui-tooling:$compose_ui_version"
    const val ui_test_manifest =
        "androidx.compose.ui:ui-test-manifest:$compose_ui_version"
}

object TestLibs {
    const val arch_core =
        "androidx.arch.core:core-testing:$core_testing_version"
    const val arch_core_ktx =
        "androidx.test:core-ktx:$androidx_text_core_version"
    const val compose_ui_test_junit =
        "androidx.compose.ui:ui-test-junit4:$compose_ui_version"
    const val espresso_core =
        "androidx.test.espresso:espresso-core:$espresso_core_version"
    const val ext_junit =
        "androidx.test.ext:junit:$test_ext_version"
    const val ext_junit_ktx =
        "androidx.test.ext:junit-ktx:$androidx_test_ext_kotlin_runner_version"
    const val junit =
        "junit:junit:$junit_version"
    const val kotlin_coroutines_test =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version"
    const val kotlin_reflect =
        "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
    const val mockk =
        "io.mockk:mockk:$mockk_version"
    const val mockk_agent_jvm =
        "io.mockk:mockk-agent-jvm:$mockk_version"
    const val room =
        "androidx.room:room-testing:$room_version"
    const val test_runner =
        "androidx.test:runner:$test_runner_version"
}

object AnnotationsProcessor {
    const val hilt_android_compile = "com.google.dagger:hilt-android-compiler:$hilt_version"
}