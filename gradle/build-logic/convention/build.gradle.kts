plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.composeCompiler.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "app.jytextile.erp.kotlin.multiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("kotlinAndroid") {
            id = "app.jytextile.erp.kotlin.android"
            implementationClass = "KotlinAndroidConventionPlugin"
        }

        register("androidApplication") {
            id = "app.jytextile.erp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "app.jytextile.erp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "app.jytextile.erp.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("kmpCompose") {
            id = "app.jytextile.erp.kotlin.multiplatform.compose"
            implementationClass = "KmpComposeConventionPlugin"
        }

        register("kmpFeature") {
            id = "app.jytextile.erp.kotlin.multiplatform.feature"
            implementationClass = "KmpFeatureConventionPlugin"
        }
    }
}
