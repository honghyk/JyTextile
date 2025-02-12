plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.root"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.inventory)
        }
    }
}
