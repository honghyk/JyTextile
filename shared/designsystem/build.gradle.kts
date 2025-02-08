import com.erp.jytextile.convention.android

plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
    alias(libs.plugins.jytextile.erp.android.library)
}

android {
    namespace = "com.erp.jytextile.shared.designsystem"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
        }
    }
}
