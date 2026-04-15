package com.project.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

internal fun Project.configureFeature(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {

        val test = libs.findBundle("test").get()
        val androidTest = libs.findBundle("androidTest").get()
        val androidX = libs.findBundle("androidx").get()
        val appcompat = libs.findLibrary("appcompat").get()
        val androidCoreKtx = libs.findLibrary("android.core.ktx").get()
        val kspCompiler = libs.findLibrary("koin.ksp.compiler").get()
        val lifecycle = libs.findLibrary("androidx.lifecycle.runtimeCompose").get()

        dependencies {
            add("implementation", project(":core:ui"))
            add("implementation", project(":core:dispatchers:api"))
            add("implementation", project(":core:mvi"))
            add("implementation", project(":core:utils"))
            add("implementation", project(":core:network:entities"))
            add("implementation", project(":core:network:api"))
            add("implementation", project(":core:request"))
            add("implementation", project(":core:ui-model"))
            add("implementation", project(":screens"))

            // AndroidX
            add("implementation", androidX)
            add("implementation", appcompat)
            add("implementation", androidCoreKtx)

            add("ksp", kspCompiler)

            // Tests
            add("androidTestImplementation", androidTest)
            add("testImplementation", test)

            add("implementation", lifecycle)
        }
    }
}
