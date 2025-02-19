plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.inventory"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.kotlinUtils)

            implementation(libs.circuit.overlay)
            implementation(libs.kotlinx.datetime)
        }
    }
}
