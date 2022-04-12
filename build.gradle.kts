import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("kapt") version PluginVersions.KAPT_VERSION
}

group = "com.xquare.gateway"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.WEBFLUX)
    implementation(Dependencies.SECURITY_JWT)
    implementation(Dependencies.COROUTINE_REACTOR_EXTENSION)
    implementation(Dependencies.COROUTINE_REACTOR)
    implementation(Dependencies.SPRING_CLOUD_GATEWAY)
    implementation(Dependencies.SPRING_VALIDATION)
    implementation(Dependencies.KOTLIN_JACKSON)
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.CIRCUIT_BREAKER)
    implementation(Dependencies.ACTUATOR)
    implementation(Dependencies.ZIPKIN)
    implementation(Dependencies.SPRING_RABBIT)
    implementation(Dependencies.STARTER_SLEUTH)
    kapt(Dependencies.ANNOTATION_PROCESSOR)
    testImplementation(Dependencies.SPRING_BOOT_TEST)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD_VERSION}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
