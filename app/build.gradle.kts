plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}

android {
    namespace = "dev.syoritohatsuki.fstatsmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.syoritohatsuki.fstatsmobile"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    //    implementation("co.yml", "ycharts", "2.0.0")
    implementation("com.github.AnyChart:AnyChart-Android:1.1.5")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    val ktorVersion = "2.3.1"
    implementation("io.ktor", "ktor-client-core", ktorVersion)
    implementation("io.ktor", "ktor-client-android", ktorVersion)
    implementation("io.ktor", "ktor-client-content-negotiation", ktorVersion)
    implementation("io.ktor", "ktor-serialization-kotlinx-json", ktorVersion)


    val koinVersion = "3.4.1"
    implementation("io.insert-koin", "koin-android", koinVersion)
    implementation("io.insert-koin", "koin-ktor", koinVersion)

    implementation("androidx.navigation", "navigation-compose", "2.7.6")

    implementation("androidx.core", "core-ktx", "1.10.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle", "lifecycle-runtime-ktx", "2.6.1")
    implementation("androidx.activity", "activity-compose", "1.7.2")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui", "ui")
    implementation("androidx.compose.ui", "ui-graphics")
    implementation("androidx.compose.ui", "ui-tooling-preview")
    implementation("androidx.compose.material3", "material3")
    implementation("androidx.core", "core-ktx", "1.10.1")
    implementation("androidx.core", "core-ktx", "1.10.1")

    debugImplementation("androidx.compose.ui", "ui-tooling")
    debugImplementation("androidx.compose.ui", "ui-test-manifest")
}
