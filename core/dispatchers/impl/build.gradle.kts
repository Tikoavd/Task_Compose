plugins {
    id("project.android.library")
}
android {
    namespace = "com.task_compose.dispatchers.impl"
}
dependencies {
    implementation(project(":core:dispatchers:api"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)
}