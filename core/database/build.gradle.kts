plugins {
    alias(libs.plugins.trillion.erp.android.library)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.erp.trillion.core.database"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.base)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlininject.runtime)

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

addKspDependencyForAllTargets(libs.androidx.room.compiler)
