dependencies {
    implementation(project(":database"))

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")
}