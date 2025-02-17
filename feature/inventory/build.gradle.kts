plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.inventory"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.circuit.overlay)
        }
    }
}
