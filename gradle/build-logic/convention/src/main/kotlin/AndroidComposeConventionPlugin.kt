import com.erp.trillion.convention.android
import com.erp.trillion.convention.debugImplementation
import com.erp.trillion.convention.implementation
import com.erp.trillion.convention.libs
import com.erp.trillion.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            android {
                buildFeatures.compose = true
            }
            dependencies {
                val bom = libs.findLibrary("compose-bom").get()
                implementation(platform(bom))
                implementation(libs.findLibrary("androidx-core-ktx"))
                implementation(libs.findLibrary("compose-ui"))
                implementation(libs.findLibrary("compose-material3"))
                implementation(libs.findLibrary("compose-ui-tooling-preview"))
                implementation(libs.findLibrary("androidx-lifecycle-runtime-compose"))
                implementation(libs.findLibrary("androidx-activity-compose"))
                testImplementation(libs.findLibrary("kotlin.test"))
                testImplementation(libs.findLibrary("compose-ui-test-junit4"))
                testImplementation(libs.findLibrary("androidx-espresso-core"))
                debugImplementation(libs.findLibrary("compose-ui-tooling"))
                debugImplementation(libs.findLibrary("compose-ui-test-manifest"))
            }
        }
    }
}
