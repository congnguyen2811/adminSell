plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.manager.appbanhang"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.manager.appbanhang"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

}

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.20") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
    }

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-cast-framework:21.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-messaging:23.2.1")
    implementation("com.google.firebase:firebase-firestore:24.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    //badge
    implementation ("com.nex3z:notification-badge:1.0.4")
    // evenbus
    implementation("org.greenrobot:eventbus:3.3.1")
    //paper
    implementation("io.github.pilgr:paperdb:2.7.2")
    // gson
    implementation("com.google.code.gson:gson:2.10.1")
    //lottifile
    implementation("com.airbnb.android:lottie:6.1.0")
    // image picker
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.work:work-gcm:2.8.1")
    implementation("androidx.work:work-runtime:2.8.1")
    // mvp chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")



}