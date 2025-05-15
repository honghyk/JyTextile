plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform.feature)
}

android {
    namespace = "com.erp.trillion.feature.form.roll"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.kotlinUtils)
        }
    }
}
