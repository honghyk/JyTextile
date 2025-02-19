plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.jytextile.feature.form.roll"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.kotlinUtils)
        }
    }
}
