buildscript {
  repositories {
      mavenCentral()
      google()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:4.1.3")

    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
  }
}

// we have to add this otherwise compilation fails
plugins {
  kotlin("multiplatform") version "1.6.10"
}


allprojects {
  group = "net.codinux.kotlin"
  version = "1.0.0-SNAPSHOT"


  repositories {
      mavenCentral()
      google()
  }
}


kotlin {
  // we have to add at least one target
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }

}