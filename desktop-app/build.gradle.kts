import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform)
    alias(libs.plugins.jytextile.erp.kotlin.multiplatform.compose)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        desktopMain.dependencies {
            implementation(projects.shared.designsystem)
            implementation(projects.feature.inventory)

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(compose.foundation)
            implementation(compose.material3)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.erp.jytextile.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.erp.jytextile"
            packageVersion = "1.0.0"
        }
    }
}
