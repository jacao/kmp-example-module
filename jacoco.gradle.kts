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
                // If and switch statements, this metric counts the total number of such branches in a method and determines the number of executed or missed branches.
                counter = "BRANCH"
                minimum = "0.95".toBigDecimal()
            }
        }
        rule {
            limit {
                // A method is considered as executed when at least one bytecode instruction has been executed.
                counter = "METHOD"
                minimum = "0.95".toBigDecimal()
            }
        }
        rule {
            limit {
                // A class is considered as executed when at least one of its methods has been executed.
                counter = "CLASS"
                minimum = "1.0".toBigDecimal()
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