import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("kotlin-android-extensions")
    jacoco
}

jacoco {
    toolVersion = "0.8.5"
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

val jacocoTestReport by tasks.creating(JacocoReport::class.java) {
    dependsOn("test")
    reports {
        xml.isEnabled = true
        csv.isEnabled = true
        html.isEnabled = true
        File("${buildDir}/jacocoHtml")
    }
    classDirectories.setFrom(fileTree("${project.buildDir}/tmp/kotlin-classes/debug"))
    sourceDirectories.setFrom(files(project.projectDir))
    executionData.setFrom(
        fileTree(project.projectDir) {
            setIncludes(setOf("build/jacoco/*.exec"))
        }
    )
}

val jacocoTestCoverageVerification by tasks.creating(JacocoCoverageVerification::class.java) {
    dependsOn(jacocoTestReport)
    violationRules {
        rule {
            limit {
                minimum = "0.95".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(fileTree("${project.buildDir}/tmp/kotlin-classes/debug"))
    sourceDirectories.setFrom(files(project.projectDir))
    executionData.setFrom(
        fileTree(project.projectDir) {
            setIncludes(setOf("build/jacoco/*.exec"))
        }
    )
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage."

    dependsOn("test", jacocoTestReport, jacocoTestCoverageVerification)
    tasks["jacocoTestReport"].mustRunAfter("test")
    tasks["jacocoTestCoverageVerification"].mustRunAfter("jacocoTestReport")
}