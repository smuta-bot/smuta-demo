import org.springframework.boot.gradle.tasks.bundling.BootJar

val postgresqlVersion: String by project

dependencies {
    implementation(project(":core"))
    implementation(project(":database"))

    // PostgreSQL
    implementation("org.postgresql:postgresql:${postgresqlVersion}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType(Jar::class.java) {
    this.enabled = false
}
tasks.withType(BootJar::class.java) {
    this.enabled = true
}