import org.jetbrains.kotlin.util.removeSuffixIfPresent
import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.android.ksp) apply false
    alias(libs.plugins.android.navigationSafeArgs) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofileProj) apply false
    alias(libs.plugins.android.library) apply false
}

val versions = mapOf(
    "min_sdk" to 23,
    "target_sdk" to 36,
    
)

val versionInfo = mapOf(
    "major" to 1,
    "minor" to 0,
    "patch" to 0,
    "build" to 0
)

val myVersionName = "." + "git rev-parse --short=7 HEAD".runCommand(workingDir = rootDir)
val commitMessage = "git log -1 --pretty=%B".runCommand(workingDir = rootDir).replace("\n", " ")

val versionCode =
    versionInfo["major"]!! * 1000000 + versionInfo["minor"]!! * 10000 + versionInfo["patch"]!! * 100 + versionInfo["build"]!!

val versionName =
    "${versionInfo["major"]}.${versionInfo["minor"]}.${versionInfo["patch"]}"

fun String.runCommand(
        workingDir: File = File(".")
): String = providers.exec {
    setWorkingDir(workingDir)
    commandLine(split(' '))
}.standardOutput.asText.get().removeSuffixIfPresent("\n")

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}

extra["versions"] = versions
extra["versionCode"] = versionCode
extra["versionName"] = versionName
extra["myVersionName"] = myVersionName
extra["commitMessage"] = commitMessage