import com.erp.jytextile.convention.android

plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
}

android {
    namespace = "com.erp.jytextile.core.designsystem"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.components.resources)

            implementation(libs.material3.adaptive)
            implementation(libs.data.table.material3)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.ui.tooling.preview)
        }
    }
}
