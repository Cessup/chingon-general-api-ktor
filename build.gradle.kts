
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
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // JWT Auth
    implementation("io.ktor:ktor-server-auth:2.3.4")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.4")

    // Optional: logging/debugging
    implementation("ch.qos.logback:logback-classic:1.5.13")
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

    //Injection Dependencies
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.yaml:snakeyaml:2.2")

    // Unit testing
    testImplementation("io.mockk:mockk:1.13.10")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.testcontainers:testcontainers:1.19.1")
    testImplementation("org.testcontainers:mongodb:1.19.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

tasks.test {
    jvmArgs("-Xshare:off")
}

tasks.test {
    useJUnitPlatform()
}