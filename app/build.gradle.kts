plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.navigation)
}

android {

    namespace = "com.khoa.demotoeictest"
    compileSdk = ((rootProject.extra["versions"] as Map<*, *>)["target_sdk"] as Int?)!!
    
    signingConfigs {
    
    }

    defaultConfig {
        applicationId = "com.khoa.demotoeictest"
        minSdk = ((rootProject.extra["versions"] as Map<*, *>)["min_sdk"] as Int?)!!
        targetSdk = ((rootProject.extra["versions"] as Map<*, *>)["target_sdk"] as Int?)!!
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        buildConfigField(
            "String",
            "MY_VERSION_NAME",
            "\"$versionName${rootProject.extra["myVersionName"] as String}\""
        )
        setProperty("archivesBaseName", "ToeicK-$versionName${versionNameSuffix ?: ""}")
        vectorDrawables {
            useSupportLibrary = true
        }
        
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isPseudoLocalesEnabled = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = rootProject.extra["myVersionName"] as String
        }
        create("staging") {
            applicationIdSuffix = ".staging"
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
    buildFeatures {
        buildConfig = true
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
    debugImplementation(libs.leak.canary)
}

kapt {
    correctErrorTypes = true
}