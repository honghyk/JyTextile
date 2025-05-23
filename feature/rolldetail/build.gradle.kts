plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.rolldetail"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.kotlinUtils)

            implementation(libs.circuit.overlay)
        }
    }
}
