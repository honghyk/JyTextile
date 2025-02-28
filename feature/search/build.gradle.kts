plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.search"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
        }
    }
}
