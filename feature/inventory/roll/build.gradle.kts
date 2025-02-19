plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.inventory.roll"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.circuit.overlay)
        }
    }
}
