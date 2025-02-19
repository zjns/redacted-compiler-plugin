import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("io.github.zjns.redacted")
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions {
    useK2.set(project.findProperty("rcp.useK2")?.toString().toBoolean())
    jvmTarget.set(JvmTarget.JVM_11)
    @Suppress("SuspiciousCollectionReassignment")
    freeCompilerArgs.add("-Xstring-concat=${project.findProperty("string_concat")}")
  }
}

dependencies {
  implementation(project(":redacted-compiler-plugin-annotations"))
  testImplementation(libs.junit)
  testImplementation(libs.truth)
}

configurations.configureEach {
  resolutionStrategy.dependencySubstitution {
    substitute(module("io.github.zjns.redacted:redacted-compiler-plugin-annotations"))
        .using(project(":redacted-compiler-plugin-annotations"))
    substitute(module("io.github.zjns.redacted:redacted-compiler-plugin-annotations-jvm"))
        .using(project(":redacted-compiler-plugin-annotations"))
    substitute(module("io.github.zjns.redacted:redacted-compiler-plugin"))
        .using(project(":redacted-compiler-plugin"))
  }
}
