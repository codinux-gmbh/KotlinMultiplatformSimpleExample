// we have to add this otherwise compilation fails
plugins {
    kotlin("multiplatform") version "1.6.10"
}


subprojects {
  group = "net.codinux.kotlin"
  version = "1.0.0-SNAPSHOT"


  repositories {
    mavenCentral()
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