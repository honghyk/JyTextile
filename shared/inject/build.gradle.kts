plugins {
    alias(libs.plugins.jytextile.erp.android.library)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.erp.jytextile.shared.inject"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.base)
            api(projects.shared.domain)
            api(projects.shared.data)
            api(projects.shared.database)
            api(projects.feature.root)
            api(projects.feature.inventory)

            implementation(libs.kotlininject.runtime)
        }
    }
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)
