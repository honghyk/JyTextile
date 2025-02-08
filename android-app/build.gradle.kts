plugins {
    alias(libs.plugins.jytextile.erp.android.application)
    alias(libs.plugins.jytextile.erp.kotlin.android)
    alias(libs.plugins.jytextile.erp.android.compose)
}

android {
    namespace = "com.erp.jytextile.app.android"

    defaultConfig {
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
}
