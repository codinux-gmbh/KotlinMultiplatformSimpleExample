plugins {
    kotlin("multiplatform") version "1.6.10"
}

group = "net.codinux.kotlin"
version = "1.0.0-SNAPSHOT"


repositories {
    mavenCentral()
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // TODO: try to get rid of / use Kotlin MPP logger
                implementation("org.slf4j:slf4j-api:1.7.28")
                // TODO: use Kotlin Serialization
                implementation("net.dankito.utils:java-utils:1.0.18")
            }
        }
        val jvmTest by getting

        val jsMain by getting
        val jsTest by getting

        val nativeMain by getting
        val nativeTest by getting
    }

}