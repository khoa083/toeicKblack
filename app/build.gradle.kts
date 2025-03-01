plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.khoa.demotoeictest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.khoa.demotoeictest"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    defaultConfig{
        vectorDrawables.useSupportLibrary = true
    }
}

dependencies {
    
    implementation(libs.bundles.androidxCoreComponents)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.lifecycleAware)
    implementation(libs.bundles.okhttp)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.roomDb)
    ksp(libs.room.compiler)
    implementation(libs.bundles.retrofit2)
    implementation(libs.material3)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.dotsindicator)
    implementation(libs.paging)
    implementation(libs.shimmer)
    implementation(libs.glide)
    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.espresso.core)
}

kapt {
    correctErrorTypes = true
}