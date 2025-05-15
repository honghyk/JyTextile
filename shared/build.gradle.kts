import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.trillion.erp.android.library)
    alias(libs.plugins.trillion.erp.kotlin.multiplatform)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.erp.trillion.shared"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.domain)
            api(projects.core.data)
            api(projects.core.database)
            api(projects.core.network)
            api(projects.feature.root)
            api(projects.feature.inventory.zone)
            api(projects.feature.inventory.roll)
            api(projects.feature.inventory.release)
            api(projects.feature.form.zone)
            api(projects.feature.form.roll)
            api(projects.feature.form.release)
            api(projects.feature.rolldetail)
            api(projects.feature.search)

            implementation(libs.kotlininject.runtime)
            implementation(libs.circuit.foundation)
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            isStatic = true
            baseName = "Trillion"

            export(projects.feature.root)
        }
    }
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)
