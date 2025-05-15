import com.erp.trillion.convention.android

plugins {
    alias(libs.plugins.trillion.erp.android.library)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform.compose)
}

android {
    namespace = "com.erp.trillion.core.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.kotlinUtils)

            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.kotlinx.datetime)
        }
    }
}
