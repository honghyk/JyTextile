plugins {
    alias(libs.plugins.trillion.erp.android.application)
    alias(libs.plugins.trillion.erp.kotlin.android)
    alias(libs.plugins.trillion.erp.android.compose)
}

android {
    namespace = "com.erp.trillion.app.android"

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
