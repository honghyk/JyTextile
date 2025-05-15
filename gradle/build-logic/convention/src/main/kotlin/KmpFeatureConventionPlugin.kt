import com.erp.trillion.convention.compose
import com.erp.trillion.convention.kotlin
import com.erp.trillion.convention.library
import com.erp.trillion.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class KmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("app.trillion.erp.android.library")
                apply("app.trillion.erp.kotlin.multiplatform")
                apply("app.trillion.erp.kotlin.multiplatform.compose")
                apply("org.jetbrains.kotlin.plugin.parcelize")
            }

            kotlin {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(project(":core:base"))
                        implementation(project(":core:designsystem"))
                        implementation(project(":core:domain"))
                        implementation(project(":core:ui"))
                        implementation(project(":core:navigation"))

                        implementation(compose.dependencies.foundation)
                        implementation(compose.dependencies.material3)

                        implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                        implementation(libs.library("kotlinx-serialization-json"))
                        implementation(libs.library("kotlininject-runtime"))

                        implementation(libs.library("circuit-foundation"))
                    }
                }

                targets.configureEach {
                    if (platformType == KotlinPlatformType.androidJvm) {
                        compilations.configureEach {
                            compileTaskProvider.configure {
                                compilerOptions {
                                    freeCompilerArgs.addAll(
                                        "-P",
                                        "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.erp.trillion.core.base.parcel.Parcelize"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
