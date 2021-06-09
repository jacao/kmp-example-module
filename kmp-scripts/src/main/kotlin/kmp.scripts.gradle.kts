import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing

val mavenProjectName = project.property("MAVEN_PROJECT_NAME")

val pomName = project.property("POM_NAME")
val pomDescription = project.property("POM_DESCRIPTION")
val pomDeveloperId = project.property("POM_DEVELOPER_ID")
val pomDeveloperName = project.property("POM_DEVELOPER_NAME")
val pomDeveloperEmail = project.property("POM_DEVELOPER_EMAIL")

val githubUrl = project.property("GITHUB_URL")

plugins {
    `maven-publish`
    signing
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {

    repositories {
        maven {
            name = mavenProjectName as String
            setUrl(System.getenv("MAVEN_URL"))
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }

    publications.withType<MavenPublication> {

        artifact(javadocJar.get())

        pom {
            name.set(pomName as String)
            description.set(pomDescription as String)
            url.set(githubUrl as String)

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set(pomDeveloperId as String)
                    name.set(pomDeveloperName as String)
                    email.set(pomDeveloperEmail as String)
                }
            }
            scm {
                url.set(githubUrl as String)
            }

        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used

signing {
    isRequired = false
    sign(publishing.publications)
}