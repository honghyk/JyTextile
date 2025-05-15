import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.trillion.erp.android.library)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildConfig)
}

android {
    namespace = "com.erp.trillion.core.network"
}

buildConfig {
    packageName("com.erp.trillion.core.network")

    val apiKey = gradleLocalProperties(rootDir, providers).getProperty("supabase_api_key") ?: ""

    buildConfigField<String>(
        "SUPABASE_API_KEY",
        apiKey,
    )
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.base)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlininject.runtime)
            implementation(libs.kotlinx.serialization.json)
            api(libs.postgrest.kt)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
