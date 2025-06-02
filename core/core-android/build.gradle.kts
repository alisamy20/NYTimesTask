plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.core_android"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "QUERY_API_KEY", "\"api-key\"")
        buildConfigField("String", "NYT_API_KEY", "\"QG7V7GJlPb4HVhSVoQodw3mlC2kGPtPZ\"")
        buildConfigField(
            "String", "NYT_BASE_URL", "\"https://api.nytimes.com/svc/mostpopular/v2/\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:common-kotlin"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.converter.gson)
    debugImplementation(libs.okhttp.logging)

}