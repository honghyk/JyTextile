plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
}

android {
    namespace = "com.erp.jytextile.feature.inventory"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.designsystem)
            implementation(projects.shared.data)

            implementation(compose.foundation)
            implementation(compose.material3)
        }
    }
}
