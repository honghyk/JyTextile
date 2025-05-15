import com.erp.trillion.convention.android

plugins {
    alias(libs.plugins.trillion.erp.android.library)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform.compose)
}

android {
    namespace = "com.erp.trillion.core.designsystem"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.components.resources)

            implementation(libs.material3.adaptive)
            implementation(libs.material.icons.core)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.ui.tooling.preview)
        }
    }
}
