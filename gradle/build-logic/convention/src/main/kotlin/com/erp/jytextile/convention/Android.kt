package com.erp.jytextile.convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroid() {
    android {
        compileSdkVersion(35)

        defaultConfig {
            minSdk = 28
            targetSdk = 35

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}

fun Project.android(action: BaseExtension.() -> Unit) =
    extensions.configure<BaseExtension>(action)
