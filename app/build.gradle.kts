
plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    packagingOptions{
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")

    }
    namespace = "com.application.roxid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.application.roxid"
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
    buildFeatures {
        viewBinding = true
    }


}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.android)
    implementation(libs.android.activation)
    implementation(libs.android.mail)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.authentication)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("io.agora.rtc:voice-sdk:4.5.0")
    implementation("io.agora.rtc:chat-sdk:1.3.0")
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("io.agora:authentication:2.1.2")
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

}