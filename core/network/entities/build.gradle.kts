plugins {
    id("project.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.task_compose.network.entities"
}

dependencies {
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.datetime)
}
