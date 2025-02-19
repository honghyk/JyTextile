rootProject.name = "TextileERP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":android-app")
include(":desktop-app")
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:domain")
include(":core:base")
include(":feature:root")
include(":shared")
include(":core:ui")
include(":core:kotlin-utils")
include(":core:navigation")
include(":feature:inventory:zone")
include(":feature:inventory:roll")
include(":feature:inventory:release")
include(":feature:form:zone")
include(":feature:form:roll")
include(":feature:form:release")
include(":feature:rolldetail")
