package com.erp.trillion.convention

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

fun Project.configureJava() {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}
