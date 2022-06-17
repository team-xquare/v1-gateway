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
    implementation(Dependencies.MICROMETER)
    implementation(Dependencies.HTTP_CLIENT)
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

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint(Dependencies.KTLINT) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

// Checking lint
val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "**/*.kt")
}

// Formatting all source files
val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "**/*.kt")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}

val installGitHook by tasks.creating(Copy::class) {

    description = "Install git hook to root project."

    var suffix = "macos"
    if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)) {
        suffix = "windows"
    }

    val sourceDir = File(rootProject.rootDir, "pre-build/scripts/ktlint/pre-push-on-$suffix")
    val targetDir = File(rootProject.rootDir, ".git/hooks")

    from(sourceDir)
    into(targetDir)
    rename("pre-push-$suffix", "pre-push")

    fileMode = 0b111101101
}

project.tasks
    .getByName("build")
    .dependsOn(":installGitHook")

tasks.getByName<Jar>("jar") {
    enabled = false
}

