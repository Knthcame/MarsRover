plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sonarcloud)
    alias(libs.plugins.kover)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.knthcame.marsrover"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.knthcame.marsrover"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose.viewmodel.navigation)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
sonar {
    properties {
        property("sonar.projectKey", "Knthcame_MarsRover")
        property("sonar.organization", "knthcame")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/reportRelease.xml")
        property(
            "sonar.coverage.exclusions",
            "**/androidTest/**," +
                    "**/test/**," +
                    "**/ui/theme/**," +
                    "**/*Screen*," +
                    "**/*NavHost*," +
                    "**/MainActivity.kt," +
                    "**/Koin.kt," +
                    "**/Fake*," +
                    "**/*Canvas.kt,"
        )
    }
}