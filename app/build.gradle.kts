plugins { id("com.android.application") }

android {
    namespace = "com.amon3411.lumen"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.amon3411.lumen"
        minSdk = 24
        targetSdk = 35
        versionCode = 8
        versionName = "1.4.1"
    }
    sourceSets["main"].assets.srcDirs("../web")
    signingConfigs {
        create("release") {
            val ks = file("../release.jks")
            if (ks.exists()) {
                storeFile = ks
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = "lumen"
                keyPassword = System.getenv("KEYSTORE_PASSWORD")
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
