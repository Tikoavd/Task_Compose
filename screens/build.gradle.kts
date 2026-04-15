plugins {
    id("project.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.task_compose.screens"
}

dependencies {
    // Serialization
    implementation(libs.kotlin.serialization)

    implementation(project(":core:ui-model"))
}
