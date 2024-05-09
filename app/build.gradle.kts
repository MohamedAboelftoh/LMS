
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.lms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lms"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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

    viewBinding {
       enable=true
    }


}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation ("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    //calender View
    implementation ("com.github.prolificinteractive:material-calendarview:2.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //glide library to load images from api
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // OkHttp client if api not security
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    // OkHttp logging interceptor
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    // pdfViewer
    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")
    //steper
    implementation ("com.github.TalebRafiepour:AndroidSteper:0.9")
    implementation ("com.github.shuhart:stepview:1.5.1")

}