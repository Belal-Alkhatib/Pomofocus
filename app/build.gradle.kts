plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.deepcoder.pomofocus"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.deepcoder.pomofocus"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packagingOptions {
        exclude ("META-INF/gradle/incremental.annotation.processors/**")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
     val HILT_VERSION = "2.44"
     val DAGGER_VERSION = "2.35.1"
     val HILT_NAVIGATION_VERSION = "1.0.0"
     val ROOM_VERSION = "2.5.1"
     val KOTLIN_COROUTINES_VERSION = "1.6.4"
     val LIFECYCLE_VERSION = "2.6.1"


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.dagger:hilt-android:$HILT_VERSION")
    implementation("androidx.hilt:hilt-navigation-compose:$HILT_NAVIGATION_VERSION")
    kapt("com.google.dagger:hilt-compiler:$HILT_VERSION")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLIN_COROUTINES_VERSION")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION")

    //room
    implementation("androidx.room:room-runtime:$ROOM_VERSION")
    implementation("androidx.room:room-ktx:$ROOM_VERSION")
    kapt("androidx.room:room-compiler:$ROOM_VERSION")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("androidx.compose.material3:material3:1.1.0")


}