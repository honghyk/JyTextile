plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.erp.jytextile.shared.database"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.domain)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }

        androidInstrumentedTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.test.runner)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    kspAndroid(libs.androidx.room.compiler)
    kspDesktop(libs.androidx.room.compiler)
    kspIosArm64(libs.androidx.room.compiler)
    kspIosSimulatorArm64(libs.androidx.room.compiler)
}
