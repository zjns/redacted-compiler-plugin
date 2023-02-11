import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("multiplatform")
  id("io.github.zjns.redacted")
}

kotlin {
  jvm {
    compilations.configureEach {
      compilerOptions.configure {
        jvmTarget.set(JvmTarget.JVM_11)
        freeCompilerArgs.add("-Xstring-concat=${project.findProperty("string_concat")}")
      }
    }
  }
  sourceSets {
    commonMain { dependencies { implementation(project(":redacted-compiler-plugin-annotations")) } }
    val jvmTest by getting {
      dependencies {
        implementation(libs.junit)
        implementation(libs.truth)
      }
    }
  }
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
