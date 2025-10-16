
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "1.9.0"
}

group = "com.cessup"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)


    // JWT Auth
    implementation("io.ktor:ktor-server-auth:2.3.4")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.4")

    //Mongo Database
    implementation("org.mongodb:mongodb-driver-reactivestreams:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.3")
    //Encrypt
    implementation("at.favre.lib:bcrypt:0.10.2")


    implementation("io.ktor:ktor-server-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-client-gson:2.3.4")
    implementation("io.ktor:ktor-serialization-gson:2.3.4")
    implementation("io.ktor:ktor-server-status-pages:2.3.4")
    implementation("io.ktor:ktor-server-cors:2.3.4")

    implementation("org.yaml:snakeyaml:2.2")

    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:3.5.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.13")

    // JUnit 5
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // Coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // MongoDB Driver
    implementation("org.mongodb:mongodb-driver-sync:4.11.1")



}

tasks.test {
    jvmArgs("-Xshare:off")
}

tasks.test {
    // Enable JUnit 5
    useJUnitPlatform()

    // Optional: show standard output from tests
    testLogging {
        events("passed", "skipped", "failed")
    }
}