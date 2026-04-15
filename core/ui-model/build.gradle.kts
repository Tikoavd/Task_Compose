plugins {
    id("project.android.library")
    id("project.android.library.compose")
    kotlin("plugin.serialization")
}
android {
    namespace = "com.task_compose.ui_model"
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.datetime)

    implementation(project(":core:network:entities"))
    implementation(project(":core:utils"))
    implementation(project(":core:request"))
}
