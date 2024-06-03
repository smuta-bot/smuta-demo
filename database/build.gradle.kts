val postgresqlVersion: String by project

plugins {
    id("org.liquibase.gradle") version "2.2.0"
}

dependencies {
    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // PostgreSQL
    liquibaseRuntime("org.postgresql:postgresql:${postgresqlVersion}")

    // Migrations
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("info.picocli:picocli:4.6.1")

    // Hibernate Types
    implementation("io.hypersistence:hypersistence-utils-hibernate-62:3.6.0")
}

liquibase {
    activities.register("main") {
        val url = System.getenv("POSTGRES_ADDRESS") ?: "my url"
        val username = System.getenv("POSTGRES_USER") ?: "my username"
        val password = System.getenv("POSTGRES_PASSWORD") ?: "my password"

        this.arguments = mapOf(
            "logLevel" to "debug",
            "changelogFile" to "index.xml",
            "searchPath" to "src/main/resources/migrations",
            "url" to url,
            "username" to username,
            "password" to password,
            "defaultSchemaName" to "public"
        )
    }
    runList = "main"
}

tasks.withType(JavaCompile::class.java) {
    dependsOn(":database:update")
}