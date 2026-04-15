package com.project.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("kotlin.compiler.version").get().toString()
        }

//        kotlinOptions {
//            freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
//        }

        dependencies {
            val bom = libs.findLibrary("compose.bom").get()
            val toolingPreview = libs.findLibrary("compose.tooling.preview").get()
            val tooling = libs.findLibrary("compose.tooling").get()
            val materialIcon = libs.findLibrary("compose.material.icon").get()
            val compose = libs.findBundle("compose").get()
            val composeDebugTestManifest = libs.findLibrary("compose.debug.test.manifest").get()
            val coil = libs.findLibrary("coil.compose").get()

            add("debugImplementation", tooling)
            add("implementation", compose)
            add("implementation", coil)
            add("implementation", materialIcon)
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("debugImplementation", toolingPreview)
            add("debugImplementation", composeDebugTestManifest)

        }
    }
}
