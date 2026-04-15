plugins {
    id("project.android.library")
}

android {
    namespace = "com.task_compose.network.impl"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:utils"))
    implementation(project(":core:network:api"))
    implementation(project(":core:network:entities"))
    implementation(project(":core:request"))
    implementation(libs.retrofit2)
    implementation(libs.logging)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.convertor)
}
