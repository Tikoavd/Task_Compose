plugins {
    id("project.android.application")
    id("project.android.application.compose")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.task_compose.task"

    defaultConfig {
        applicationId = "com.task_compose.task"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations += setOf("en", "ru")
    }

    buildTypes {
        val debug by getting {
            versionNameSuffix = ".debug"
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    secrets {
        ignoreList.add("keyToIgnore")
        ignoreList.add("sdk.*")
    }
}

dependencies {

    implementation(project(":core:network:api"))
    implementation(project(":core:network:impl"))
    implementation(project(":core:network:entities"))
    implementation(project(":core:dispatchers:impl"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":navigation"))
    implementation(project(":screens"))
    implementation(project(":feature:home"))

    implementation(libs.bundles.androidx)
    implementation(libs.android.core.splash)

    // TEST
    testImplementation(libs.bundles.test)
    debugImplementation(libs.compose.debug.test.manifest)
}