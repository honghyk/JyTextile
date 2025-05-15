plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.trillion.feature.root"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(libs.material3.adaptive)
            implementation(libs.circuit.foundation)
            implementation(libs.circuit.overlay)
        }
    }
}
