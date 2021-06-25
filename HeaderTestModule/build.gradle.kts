import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("kotlin-android-extensions")
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
    jacoco

}

version = "0.0.1"
group = "com.unidays.headertestmodule"

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    input = files("src/commonMain/kotlin")
    autoCorrect = false

    reports {
        html.enabled = true
        xml.enabled = true
        txt.enabled = true
        sarif.enabled = true
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    jvmTarget = "1.8"
}

jacoco {
    toolVersion = "0.8.6"
}

apply {
    from("../jacoco.gradle.kts")
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "HeaderTestModule"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
    }
}

multiplatformSwiftPackage {
    packageName("HeaderTestModule")
    swiftToolsVersion("5.4")
    targetPlatforms {
        iOS { v("13") }
    }
}
