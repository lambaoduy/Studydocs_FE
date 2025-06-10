plugins {
    // Plugin tạo ứng dụng Android
    alias(libs.plugins.android.application)
    // Plugin hỗ trợ Kotlin cho Android
    alias(libs.plugins.kotlin.android)
    // Plugin hỗ trợ Jetpack Compose với Kotlin
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.finalexam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.finalexam"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ========== CORE ANDROID ========== //
    /* Thư viện mở rộng Kotlin cho Android Core */
    implementation(libs.androidx.core.ktx)
    /* Quản lý vòng đời ứng dụng với Kotlin extensions */
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ========== COMPOSE ========== //
    /* BOM (Bill of Materials) để quản lý phiên bản các thư viện Compose */
    val composeBom = platform("androidx.compose:compose-bom:2025.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    /* Core Compose UI */
    implementation("androidx.compose.ui:ui")
    /* Hỗ trợ đồ họa trong Compose */
    implementation("androidx.compose.ui:ui-graphics")
    /* Công cụ preview trong Android Studio */
    implementation("androidx.compose.ui:ui-tooling-preview")
    /* Material Design 3 components */
    implementation(libs.androidx.material3)
    /*code ở ngoài compose thì phải thêm*/
    implementation("com.google.android.material:material:1.11.0")

    // ========== COMPOSE EXTENSIONS ========== //
    /* Material icons cơ bản */
    implementation("androidx.compose.material:material-icons-core")
    /* Bộ icon mở rộng */
    implementation("androidx.compose.material:material-icons-extended")
    /* Hỗ trợ giao diện adaptive (cho nhiều kích thước màn hình) */
    implementation("androidx.compose.material3.adaptive:adaptive")
    /* Tích hợp ViewModel với Compose */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    /* Hệ thống điều hướng trong Compose */
    implementation("androidx.navigation:navigation-compose:2.9.0")
    /* Thư viện load ảnh trong Compose */
    implementation("io.coil-kt:coil-compose:2.5.0")

    // ========== FIREBASE ========== //
    /* BOM để quản lý phiên bản Firebase */
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    /* Analytics */
    implementation("com.google.firebase:firebase-analytics")
    /* Xác thực người dùng */
    implementation("com.google.firebase:firebase-auth")
    /* Cơ sở dữ liệu NoSQL thời gian thực */
    implementation("com.google.firebase:firebase-firestore")
    /* Lưu trữ file trên cloud */
    implementation("com.google.firebase:firebase-storage")
    /* Push notification */
    implementation("com.google.firebase:firebase-messaging")

    // ========== NETWORKING ========== //
    /* HTTP client */
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    /* REST API client */
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    /* Chuyển đổi JSON <-> Object */
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // ========== DATABASE ========== //
    /* ORM database local */
    implementation(libs.androidx.room.runtime.android)

    // ========== PERMISSIONS ========== //
    /* Xử lý runtime permissions dễ dàng hơn */
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    // ========== TESTING ========== //
    /* Unit testing */
    testImplementation(libs.junit)
    /* Android instrumentation testing */
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    /* UI testing cho Compose */
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // ========== DEBUG TOOLS ========== //
    /* Công cụ debug Compose */
    debugImplementation("androidx.compose.ui:ui-tooling")
    /* Tạo manifest cho testing */
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}