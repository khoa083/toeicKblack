plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.kblack.baselineprofile"
    compileSdk = ((rootProject.extra["versions"] as Map<*, *>)["target_sdk"] as Int?)!!
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    
    kotlinOptions {
        jvmTarget = "23"
    }
    
    defaultConfig {
        minSdk = ((rootProject.extra["versions"] as Map<*, *>)["min_sdk"] as Int?)!!
        targetSdk = ((rootProject.extra["versions"] as Map<*, *>)["target_sdk"] as Int?)!!
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    targetProjectPath = ":app"
    buildTypes {
        create("staging") {
        }
        create("benchmarkStaging") {
        }
        create("nonMinifiedStaging") {
        }
    }
    
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
}

androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)?.applicationId }
        )
    }
}