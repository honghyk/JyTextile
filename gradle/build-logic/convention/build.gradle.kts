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
            id = "app.trillion.erp.kotlin.multiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("kotlinAndroid") {
            id = "app.trillion.erp.kotlin.android"
            implementationClass = "KotlinAndroidConventionPlugin"
        }

        register("androidApplication") {
            id = "app.trillion.erp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "app.trillion.erp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "app.trillion.erp.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("kmpCompose") {
            id = "app.trillion.erp.kotlin.multiplatform.compose"
            implementationClass = "KmpComposeConventionPlugin"
        }

        register("kmpFeature") {
            id = "app.trillion.erp.kotlin.multiplatform.feature"
            implementationClass = "KmpFeatureConventionPlugin"
        }
    }
}
