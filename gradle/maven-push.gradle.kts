// import org.gradle.api.publish.maven.MavenPublication
// import org.gradle.kotlin.dsl.`maven-publish`
// import org.gradle.kotlin.dsl.signing
// import java.util.*
//
// plugins {
//     `maven-publish`
//     signing
// }
//
// val javadocJar by tasks.registering(Jar::class) {
//     archiveClassifier.set("javadoc")
// }
//
// publishing {
//
//     repositories {
//         maven {
//             name = MAVEN_PROJECT_NAME
//             setUrl(System.getenv("MAVEN_URL"))
//             credentials {
//                 username = System.getenv("OSSRH_USERNAME")
//                 password = System.getenv("OSSRH_PASSWORD")
//             }
//         }
//     }
//
//     publications.withType<MavenPublication> {
//
//         artifact(javadocJar.get())
//
//         pom {
//             name.set(POM_NAME)
//             description.set(POM_DESCRIPTION)
//             url.set(GITHUB_URL)
//
//             licenses {
//                 license {
//                     name.set("MIT")
//                     url.set("https://opensource.org/licenses/MIT")
//                 }
//             }
//             developers {
//                 developer {
//                     id.set(POM_DEVELOPER_ID)
//                     name.set(POM_DEVELOPER_NAME)
//                     email.set(POM_DEVELOPER_EMAIL)
//                 }
//             }
//             scm {
//                 url.set(GITHUB_URL)
//             }
//
//         }
//     }
// }
//
// // Signing artifacts. Signing.* extra properties values will be used
//
// signing {
//     sign(publishing.publications)
// }