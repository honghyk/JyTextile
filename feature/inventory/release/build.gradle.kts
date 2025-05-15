plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.trillion.feature.inventory.release"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(libs.circuit.overlay)
        }
    }
}
