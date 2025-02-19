import com.erp.jytextile.convention.android

plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
}

android {
    namespace = "com.erp.jytextile.core.ui"
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
