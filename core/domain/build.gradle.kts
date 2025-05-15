plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.kotlinUtils)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
