plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Для Room и Moshi
}

android {
    namespace = "com.example.marvelheroesapp"
    compileSdk = 35 // Обновлено на 35 для совместимости

    defaultConfig {
        applicationId = "com.example.marvelheroesapp"
        minSdk = 24
        targetSdk = 35 // Обновлено для согласования с compileSdk
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android и Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // Room (убраны лишние и устаревшие зависимости)
    implementation("androidx.room:room-runtime:2.6.1") // Основная библиотека Room
    implementation("androidx.room:room-ktx:2.6.1")     // KTX для удобной работы
    kapt("androidx.room:room-compiler:2.6.1")          // Для генерации кода Room

    // Unit-тесты
    testImplementation(libs.junit)

    // Android-тесты
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Инструменты отладки
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil для загрузки изображений
    implementation(libs.coil.compose)

    // Accompanist (дополнительные библиотеки для Compose)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Материальные иконки
    implementation(libs.androidx.material.icons.extended)

    // Moshi и Retrofit
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    kapt(libs.moshi.kotlin.codegen) // Для генерации адаптеров Moshi

    // Material Components
    implementation(libs.material)
}
