plugins {
    id("project.android.library")
    id("project.android.library.compose")
}
android {
    namespace = "com.task_compose.ui"
}

dependencies {
    implementation(libs.kotlin.serialization.datetime)

    implementation(project(":core:mvi"))
    implementation(project(":core:ui-model"))
}
