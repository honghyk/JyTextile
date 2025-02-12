import com.erp.jytextile.convention.compose
import com.erp.jytextile.convention.kotlin
import com.erp.jytextile.convention.library
import com.erp.jytextile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("app.jytextile.erp.android.library")
                apply("app.jytextile.erp.kotlin.multiplatform")
                apply("app.jytextile.erp.kotlin.multiplatform.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            kotlin {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(project(":shared:base"))
                        implementation(project(":shared:designsystem"))
                        implementation(project(":shared:domain"))

                        implementation(compose.dependencies.foundation)
                        implementation(compose.dependencies.material3)

                        implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                        implementation(libs.library("navigation-compose"))
                        implementation(libs.library("kotlinx-serialization-json"))
                        implementation(libs.library("kotlininject-runtime"))
                    }
                }
            }
        }
    }
}
