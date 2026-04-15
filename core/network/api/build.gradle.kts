plugins {
    id("project.android.library")
}

android {
    namespace = "com.task_compose.network.api"
}

dependencies {
    implementation(project(":core:network:entities"))
    implementation(project(":core:request"))

    implementation(libs.retrofit2)
}
