import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.erp.jytextile.shared"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.domain)
            api(projects.core.data)
            api(projects.core.database)
            api(projects.feature.root)
            api(projects.feature.inventory)

            implementation(libs.kotlininject.runtime)
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            isStatic = true
            baseName = "JyTextileERP"

            export(projects.feature.root)
        }
    }
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)
