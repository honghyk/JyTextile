plugins {
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.base)
            implementation(projects.core.kotlinUtils)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlininject.runtime)
            implementation(libs.kermit)
            api(libs.store)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
