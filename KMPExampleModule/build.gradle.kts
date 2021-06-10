import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("kotlin-android-extensions")
    jacoco
}

version = "0.0.1"
group = "com.example.kmpexamplemodule"

jacoco {
    toolVersion = "0.8.5"
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64
    iosTarget("ios") {
        binaries {
            framework {
                baseName = "KMPExampleModule"
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
    buildToolsVersion("21.1.2")
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
multiplatformSwiftPackage {
    packageName("KMPExampleModule")
    swiftToolsVersion("5.4")
    targetPlatforms {
        iOS { v("13") }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    val targetDir = File(buildDir, "xcode-frameworks")
    group = "build"
    dependsOn(framework.linkTask)
    inputs.property("mode", mode)
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

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