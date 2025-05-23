plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.kotlininject.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.circuit.foundation)
            implementation(libs.circuit.overlay)
        }
    }
}
