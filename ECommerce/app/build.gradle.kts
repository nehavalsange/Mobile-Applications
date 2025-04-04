plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.uncc.evaluation05"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.uncc.evaluation05"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.picasso:picasso:2.8")
}
