plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation)
}

android {
    namespace = "com.kblack.base"
    compileSdk = ((rootProject.extra["versions"] as Map<*, *>)["target_sdk"] as Int?)!!

    defaultConfig {
        minSdk = ((rootProject.extra["versions"] as Map<*, *>)["min_sdk"] as Int?)!!

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.navigation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.bundles.retrofit2)
    implementation(libs.bundles.okhttp)
    implementation(platform(libs.okhttp.bom))
}