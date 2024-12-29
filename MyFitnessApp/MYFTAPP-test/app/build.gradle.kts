plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.zybooks.myfitnessapp"
    compileSdk = 34

    android {
        defaultConfig {
            vectorDrawables.useSupportLibrary = true
        }
    }
    defaultConfig {
        applicationId = "com.zybooks.myfitnessapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.fragment:fragment:1.3.6")
    implementation("com.google.android.material:material:1.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
}