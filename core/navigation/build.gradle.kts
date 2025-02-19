plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)

            implementation(libs.circuit.foundation)
        }
    }
}
