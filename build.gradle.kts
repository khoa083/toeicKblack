buildscript {
    val agp_version by extra("8.8.2")
    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
    }
}
//sau lỗi thì comment đoạn trên đi
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id ("com.android.application") version ("8.1.0") apply false
//    id ("com.android.library") version ("8.1.0") apply false
    id ("org.jetbrains.kotlin.android") version ("2.0.0") apply false
    id ("com.google.dagger.hilt.android") version ("2.55") apply false
    id ("androidx.navigation.safeargs") version ("2.8.5") apply false
//    alias(libs.plugins.android.kotlin) apply false
//    alias(libs.plugins.android.hilt) apply false
//    alias(libs.plugins.android.navigationSafeArgs) apply false
//    alias(libs.plugins.android.ksp) apply false
}
