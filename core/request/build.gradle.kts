plugins {
    id("project.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.task_compose.request"
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(project(":core:network:entities"))
}
