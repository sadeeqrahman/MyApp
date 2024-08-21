plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.oma.beyondpayment.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oma.beyondpayment.myapp"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.lifecycle.common.java8)

    // Google Services
    implementation(libs.play.services.mlkit.face.detection)
    implementation(libs.vision.common)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)

    implementation(libs.androidx.camera.view)

    // Square
    implementation(libs.timber)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.androidx.multidex)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.camera.core)


    implementation("org.apache.commons:commons-io:1.3.2")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.oma.beyondpayment.myapp"
            artifactId = "MyApp"
            version = "1.0.1"


            pom {
                name.set("OMAFaceLivelinessSdk")
                description.set("A library for detecting face liveliness.")
                url.set("https://github.com/sadeeqrahman/MyApp")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("sadeeqrahman")
                        name.set("Sadeeq Rahman")
                        email.set("sadeeqrahman1@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/sadeeqrahman/MyApp.git")
                    developerConnection.set("scm:git:ssh://github.com:sadeeqrahman/MyApp.git")
                    url.set("https://github.com/sadeeqrahman/OMAFaceLivelinessSdk")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
    }
}