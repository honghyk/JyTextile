plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)

            implementation(libs.circuit.foundation)
        }
    }
}
