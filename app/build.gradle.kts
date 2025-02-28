plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    //TODO: Migrate from kapt to KSP
//    id("com.google.devtools.ksp")
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

    val lifecycle_version = "2.8.7"
    val nav_version = "2.8.8"

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
//    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

    //dotsindicator
    implementation("com.tbuonomo:dotsindicator:5.1.0")
//   Pagging
    implementation("androidx.paging:paging-runtime-ktx:3.3.6")

//    implementation("androidx.core:core-ktx:2.2.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:$lifecycle_version")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    // optional - ReactiveStreams support for LiveData
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")

//    implementation 'com.airbnb.android:lottie:3.4.0'
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //RoomDb
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.1")
    
//    implementation(libs.bundles.androidxCoreComponents)
//    implementation(libs.bundles.navigation)
//    implementation(libs.bundles.lifecycleAware)
//    implementation(libs.bundles.okhttp)
//    implementation(platform(libs.okhttp.bom))
//    implementation(libs.bundles.hilt)
//    ksp(libs.hilt.compiler)
//    implementation(libs.bundles.roomDb)
//    implementation(libs.bundles.retrofit2)
//    implementation(libs.material3)
//    implementation(libs.kotlinx.coroutines)
//    implementation(libs.dotsindicator)
//    implementation(libs.paging)
//    implementation(libs.shimmer)
//    implementation(libs.glide)
//    ksp(libs.room.compiler)
//    implementation(libs.junit)
//    implementation(libs.androidx.junit)
//    implementation(libs.espresso.core)

}

kapt {
    correctErrorTypes = true
}