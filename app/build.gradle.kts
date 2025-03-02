import org.jetbrains.kotlin.util.removeSuffixIfPresent
import java.util.Properties

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
    
//    val releaseType = if (project.hasProperty("releaseType")) project.properties["releaseType"].toString()
//    else readProperties(file("../package.properties")).getProperty("releaseType")
    val myVersionName = "." + "git rev-parse --short=7 HEAD".runCommand(workingDir = rootDir)
    val commitMessage = "git log -1 --pretty=%B".runCommand(workingDir = rootDir)
//    if (releaseType.contains("\"")) {
//        throw IllegalArgumentException("releaseType must not contain \"")
//    }
    
    namespace = "com.khoa.demotoeictest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.khoa.demotoeictest"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
//        if (releaseType != "Release") {
//            versionNameSuffix = myVersionName
//        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        buildConfigField(
            "String",
            "MY_VERSION_NAME",
            "\"$versionName$myVersionName\""
        )
        buildConfigField(
            "String",
            "RELEASE_TYPE",
            "\"aaa\""
        )
        buildConfigField(
            "boolean",
            "DISABLE_STORE_FILTER",
            "false"
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

fun String.runCommand(
        workingDir: File = File(".")
): String = providers.exec {
    setWorkingDir(workingDir)
    commandLine(split(' '))
}.standardOutput.asText.get().removeSuffixIfPresent("\n")
//
//fun readProperties(propertiesFile: File) = Properties().apply {
//    propertiesFile.inputStream().use { fis ->
//        load(fis)
//    }
//}