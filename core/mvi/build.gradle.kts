plugins {
    id("project.android.library")
}

android {
    namespace = "com.task_compose.mvi"
}

dependencies {
    implementation(project(":core:network:api"))
}
