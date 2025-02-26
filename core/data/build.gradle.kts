plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.database)
            implementation(projects.core.network)
            implementation(projects.core.base)
            implementation(projects.core.kotlinUtils)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlininject.runtime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
