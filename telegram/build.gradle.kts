import org.springframework.boot.gradle.tasks.bundling.BootJar

val postgresqlVersion: String by project

java.sourceCompatibility = JavaVersion.VERSION_17

dependencies {
    implementation(project(":core"))
    implementation(project(":database"))

    // Telegram
    implementation("org.telegram:telegrambots:6.7.0")
    implementation("org.telegram:telegrambotsextensions:6.7.0")

    // PostgreSQL
    implementation("org.postgresql:postgresql:${postgresqlVersion}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType(Jar::class.java) {
    this.enabled = false
}
tasks.withType(BootJar::class.java) {
    this.enabled = true
}